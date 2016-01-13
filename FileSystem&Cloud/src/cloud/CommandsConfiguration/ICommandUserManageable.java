package cloud.CommandsConfiguration;

public interface ICommandUserManageable {
    void userinfo() ;
    
    void logout() throws Exception ;
    
    void login(String username, String password) throws Exception ;
    
    void newuser(String username, String password, String lastName, String firstName) throws Exception;
}
