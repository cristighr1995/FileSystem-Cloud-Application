package cloud.Exceptions;

public class CloudSpaceFullException extends Exception {
    
    public CloudSpaceFullException() {
        super("Spatiul din cloud este plin !");
    }
}
