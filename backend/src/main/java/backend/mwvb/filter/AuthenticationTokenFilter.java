package backend.mwvb.filter;

import backend.mwvb.entity.AuthUser;
import backend.mwvb.exception.UserAuthenticationException;
import backend.mwvb.service.AuthUserCacheService;
import backend.mwvb.util.CommonJWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

import static backend.mwvb.config.SecurityConfig.TOKEN_HEADER_NAME;

@Component
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    private final AuthUserCacheService authUserCacheService;
    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(TOKEN_HEADER_NAME);

        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        authenUser(token);
        filterChain.doFilter(request, response);
    }

    private void authenUser(String token) {
        try {
            Claims claims = CommonJWTUtils.parse(token, jwtKey);
            String userId = claims.getSubject();
            AuthUser cachedAuthUser = authUserCacheService.getCachedAuthUser(userId);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(cachedAuthUser, null, cachedAuthUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception ex) {
            throw new UserAuthenticationException("Bad Token");
        }
    }
}
