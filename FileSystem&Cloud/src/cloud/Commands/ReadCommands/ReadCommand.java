package cloud.Commands.ReadCommands;

import cloud.Folders.Director;
import cloud.Users.UserManagement;

public abstract class ReadCommand {
    
    public boolean getUserPermission(UserManagement.User currentUser, Director currentDirector) {
        return currentDirector.getUserPermissions(currentUser).getReadPermission();
    }
}
