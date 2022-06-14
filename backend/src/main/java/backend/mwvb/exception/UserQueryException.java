package backend.mwvb.exception;

public class UserQueryException extends RuntimeException{
    public UserQueryException(String msg){
        super(msg);
    }
}
