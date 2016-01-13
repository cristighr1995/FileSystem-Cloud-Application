package cloud.Exceptions;

public class DeniedPermissionException extends Exception {
    
    public DeniedPermissionException() {
        super("Nu aveti permisiuni necesare");
    }
}
