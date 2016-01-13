package cloud.Files;

public interface IFile {
    String toString();
    
    String getType();
    byte[] getContent();
    
    void changeType(String type);
    void changeContent(byte[] content);
}
