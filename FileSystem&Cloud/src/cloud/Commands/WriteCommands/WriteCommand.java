package cloud.Commands.WriteCommands;

import cloud.Folders.Director;
import cloud.Users.UserManagement;

public class WriteCommand {
    
    public boolean getUserPermission(UserManagement.User currentUser, Director currentDirector) {
        return currentDirector.getUserPermissions(currentUser).getWritePermission();
    }
}
