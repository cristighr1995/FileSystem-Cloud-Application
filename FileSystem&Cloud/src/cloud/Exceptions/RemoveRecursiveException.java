package cloud.Exceptions;

public class RemoveRecursiveException extends Exception {
    
    public RemoveRecursiveException() {
        super("Nu puteti sterge recursiv un fisier sau director gol! Apelati comanda rm fara parametrul -r...");
    }
    public RemoveRecursiveException(String something) {
        super("Nu puteti sterge un director! Apelati comanda rm cu parametrul -r...");
    }
}
