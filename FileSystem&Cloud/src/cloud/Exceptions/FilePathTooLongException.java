package cloud.Exceptions;

public class FilePathTooLongException extends Exception {
    
    public FilePathTooLongException() {
        super("Calea depaseste 255 caractere");
    }
}
