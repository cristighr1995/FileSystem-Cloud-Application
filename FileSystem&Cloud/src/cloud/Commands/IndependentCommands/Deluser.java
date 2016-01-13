package cloud.Commands.IndependentCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Configurations.SystemConfiguration;
import cloud.Exceptions.RootOnlyPermissionException;
import cloud.Exceptions.UserNotFoundException;
import cloud.Users.UserManagement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Deluser implements Command {
    private final UserManagement mUserManagement;
    private final SystemConfiguration mSystemConfiguration;
    private final String command;
    private String username;
    
    public Deluser(UserManagement um, String com, SystemConfiguration sc) {
        mUserManagement = um;
        mSystemConfiguration = sc;
        command = com;
        this.parseCommand();
    }
    
    private void parseCommand() {
        StringTokenizer mStringTokenizer = new StringTokenizer(command);
        mStringTokenizer.nextElement();
        
        username = mStringTokenizer.nextToken();
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, null);
        try {
            cmd.deluser(username);
            mSystemConfiguration.updateUsersInFile(mUserManagement);
        } catch (UserNotFoundException | RootOnlyPermissionException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
    
}
