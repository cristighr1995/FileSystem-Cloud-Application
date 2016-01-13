package cloud.Exceptions;

public class InvalidChangeDirectoryToFileException extends Exception {
    
    public InvalidChangeDirectoryToFileException() {
        super("Nu poti sa schimbi calea cu un fisier!");
    }
}
