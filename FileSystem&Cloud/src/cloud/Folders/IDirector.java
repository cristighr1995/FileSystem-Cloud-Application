package cloud.Folders;

import cloud.Permissions.Permission;
import cloud.Users.UserManagement;

public interface IDirector {
    String toString();

    String getName();
    double getDimension();
    UserManagement.User getOwner(); 
    String getTimeCreated();
    int getId();
    
    void changeName(String name);
    void updateDimension(double dimension);
    void changeOwner(UserManagement.User user);
    void setId(int id);
    
    boolean equals(Object o);
}
