package cloud.Commands.IndependentCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Users.UserManagement;

public class Userinfo implements Command {
    private final UserManagement mUserManagement;
    
    public Userinfo(UserManagement um) {
        mUserManagement = um;
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, null);
        
        cmd.userinfo();
    }
}
