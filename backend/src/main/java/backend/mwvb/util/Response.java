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

}
