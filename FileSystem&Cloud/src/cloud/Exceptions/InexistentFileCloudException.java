package cloud.Exceptions;

public class InexistentFileCloudException extends Exception {
    
    public InexistentFileCloudException() {
        super("Fisierul nu exista in cloud !");
    }
}
