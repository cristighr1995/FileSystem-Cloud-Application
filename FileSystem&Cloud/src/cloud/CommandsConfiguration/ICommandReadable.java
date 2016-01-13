package cloud.CommandsConfiguration;

public interface ICommandReadable {
    
    void ls(String command) throws Exception;
    
    void cd(String path) throws Exception;
    
    void cat(String fileName) throws Exception;
    
    void pwd() throws Exception ;
}
