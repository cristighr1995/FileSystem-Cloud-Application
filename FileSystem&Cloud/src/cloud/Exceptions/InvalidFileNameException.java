package cloud.Exceptions;

public class InvalidFileNameException extends Exception {
    
    public InvalidFileNameException() {
        super("Fisierul/Directorul introdus nu exista!");
    }
}
