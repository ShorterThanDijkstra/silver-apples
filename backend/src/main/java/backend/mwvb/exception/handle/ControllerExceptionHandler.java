package backend.mwvb.exception.handle;

import backend.mwvb.exception.*;
import backend.mwvb.util.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    @ExceptionHandler(value = IllegalRequestException.class)
    public Response<String> illegalRequestException(IllegalRequestException ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserRegisterException.class)
    public Response<String> userRegisterException(UserRegisterException ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserQueryException.class)
    public Response<String> userQueryException(UserQueryException ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserLoginException.class)
    public Response<String> userLoginException(UserLoginException ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserAuthenticationException.class)
    public Response<String> userAuthenticationException(UserAuthenticationException ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserInfoValidateException.class)
    public Response<String> userInfoValidateException(UserInfoValidateException ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserChangePasswordException.class)
    public Response<String> userChangePasswordException(UserChangePasswordException ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserWordPracticeException.class)
    public Response<String> userWordPracticeException(UserWordPracticeException ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public Response<String> internalAuthenticationServiceException(AuthenticationException ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        String message = ex.getMessage();
        message = message.substring(message.lastIndexOf(":") + 1);
        return Response.fail(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(value = Exception.class)
    public Response<String> handler(Exception ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
