package backend.mwvb.exception;

public class UserLoginException extends RuntimeException{
    public UserLoginException(String msg){
        super(msg);
    }
}
