package cloud.Users;

public interface IUserAdministration<User> {
    
    User id();
    
    User getGuestId();
    
    User getRootId();
}
