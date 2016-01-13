package cloud.Exceptions;

public class LoginInsideOtherUserException extends Exception {
    
    public LoginInsideOtherUserException() {
        super("Nu puteti sa va logati cu alt user inainte de a va deconecta de cel curent!");
    }
}
