package backend.mwvb.util;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response<T> {
    private final Integer code;
    private final T data;

    private Response(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(HttpStatus.OK.value(), data);
    }

    public static Response<String> fail(Integer code, String msg) {
        return new Response<>(code, msg);
    }
    public static Response<String> fail( String msg) {
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }
    public static Response<String> fail() {
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown error");
    }
}
