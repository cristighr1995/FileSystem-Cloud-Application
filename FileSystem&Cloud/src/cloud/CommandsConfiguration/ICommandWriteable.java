package cloud.CommandsConfiguration;

public interface ICommandWriteable {
    void mkdir(String name, int permNr) throws Exception ;
    
    void touch(String name, int permNr) throws Exception ;
    
    void rm(String fileName, String param) throws Exception ;
}
