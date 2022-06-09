package backend.mwvb.exception;

public class UserAuthenticationException extends RuntimeException{
    public UserAuthenticationException(String msg){
        super(msg);
    }
}