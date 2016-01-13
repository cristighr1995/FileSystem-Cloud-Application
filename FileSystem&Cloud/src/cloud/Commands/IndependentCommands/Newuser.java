package cloud.Commands.IndependentCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Configurations.SystemConfiguration;
import cloud.Exceptions.RootOnlyPermissionException;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Newuser implements Command {
    private final TreeFileSystem mTreeFileSystem;
    private final UserManagement mUserManagement;
    private final SystemConfiguration mSystemConfiguration;
    private final String command;
    private String username, password, lastName, firstName;
    
    public Newuser(TreeFileSystem tfs, UserManagement um, SystemConfiguration sc, String com) {
        mTreeFileSystem = tfs;
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
        lastName = mStringTokenizer.nextToken();
        firstName = mStringTokenizer.nextToken();
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, mTreeFileSystem);
        try {
            cmd.newuser(username, password, lastName, firstName);
            mSystemConfiguration.updateUsersInFile(mUserManagement);
        } catch (RootOnlyPermissionException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
    
}
