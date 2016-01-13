package cloud.Users;

public interface IUserManagement<User> {
    
    User findUser(String username);
    
    boolean deluser(String username);
    
    User userinfo();
    
    int getUsersNumber();
}
