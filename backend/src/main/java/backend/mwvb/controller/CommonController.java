package backend.mwvb.controller;

import backend.mwvb.util.Response;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CommonController implements ErrorController {
    @RequestMapping("/error")
    public Response<String> handleError(HttpServletRequest request) {
        Object code = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object msg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (code != null && msg != null) {
            return Response.fail(Integer.valueOf(code.toString()), msg.toString());
        }
        return Response.fail();
    }

}
