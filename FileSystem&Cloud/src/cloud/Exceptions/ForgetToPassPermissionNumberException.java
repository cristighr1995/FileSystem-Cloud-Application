package cloud.Exceptions;

public class ForgetToPassPermissionNumberException extends NumberFormatException {
    
    public ForgetToPassPermissionNumberException() {
        super("Ati uitat de parametrul permNr (numar de permisiune) pentru fisier/director!");
    }
}
