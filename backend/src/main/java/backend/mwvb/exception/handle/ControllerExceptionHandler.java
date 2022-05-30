package backend.mwvb.exception.handle;

import backend.mwvb.exception.IllegalRequestException;
import backend.mwvb.exception.IllegalUserInfoException;
import backend.mwvb.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Response<String> handler(Exception ex) {
        if (ex instanceof IllegalRequestException) {
            return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
        if (ex instanceof IllegalUserInfoException) {
            return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getClass().getName() +":"+ ex.getMessage());
    }
}
