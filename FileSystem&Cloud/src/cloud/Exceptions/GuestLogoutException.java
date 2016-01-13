package cloud.Exceptions;

public class GuestLogoutException extends Exception {
    
    public GuestLogoutException() {
        super("Nu puteti sa deconectati userul guest (Vizitator)!");
    }
}
