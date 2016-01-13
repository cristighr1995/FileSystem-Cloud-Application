package cloud.Commands.IndependentCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Configurations.SystemConfiguration;
import cloud.Exceptions.LoginInsideOtherUserException;
import cloud.Users.UserManagement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login implements Command {
    private final UserManagement mUserManagement;
    private final SystemConfiguration mSystemConfiguration;
    private final String command;
    private String username, password;
    
    public Login(UserManagement um, SystemConfiguration sc, String com) {
        mUserManagement = um;
        mSystemConfiguration = sc;
        command = com;
        this.parseCommand();
    }
    
    private void parseCommand() {
        StringTokenizer mStringTokenizer = new StringTokenizer(command);
        mStringTokenizer.nextElement();
        
        username = mStringTokenizer.nextToken();
        password = mStringTokenizer.nextToken();
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, null);
        
        try {
            cmd.login(username, password);
            mSystemConfiguration.updateUsersInFile(mUserManagement);
        } catch (LoginInsideOtherUserException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
    
}
