package cloud.Exceptions;

public class UserNotFoundException extends Exception {
    
    public UserNotFoundException() {
        super("Userul cu acest nume nu exista! Va rugam reincercati...");
    }
}
