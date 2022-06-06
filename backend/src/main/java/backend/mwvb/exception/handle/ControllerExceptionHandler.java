package backend.mwvb.exception.handle;

import backend.mwvb.exception.IllegalRequestException;
import backend.mwvb.exception.UserLoginException;
import backend.mwvb.exception.UserQueryException;
import backend.mwvb.exception.UserRegisterException;
import backend.mwvb.util.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Response<String> handler(Exception ex) {
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        if (ex instanceof IllegalRequestException) {
            return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
        if (ex instanceof UserRegisterException) {
            return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
        if (ex instanceof UserQueryException) {
            return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
        if (ex instanceof UserLoginException) {
            return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
        if (ex instanceof InternalAuthenticationServiceException) {
            String message = ex.getMessage();
            message = message.substring(message.lastIndexOf(":") + 1);
            return Response.fail(HttpStatus.BAD_REQUEST.value(), message);
        }
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
