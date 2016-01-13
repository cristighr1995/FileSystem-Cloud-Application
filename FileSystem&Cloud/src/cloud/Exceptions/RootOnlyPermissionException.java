package cloud.Exceptions;

public class RootOnlyPermissionException extends Exception {
    
    public RootOnlyPermissionException() {
        super("Nu puteti adauga/sterge useri decat cu contul de administrator (root)!");
    }
}
