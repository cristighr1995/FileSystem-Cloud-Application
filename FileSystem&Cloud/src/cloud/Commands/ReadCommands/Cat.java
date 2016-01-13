package cloud.Commands.ReadCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Exceptions.DeniedPermissionException;
import cloud.Exceptions.InvalidFileNameException;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cat extends ReadCommand implements Command {
    private final TreeFileSystem mTreeFileSystem;
    private final UserManagement mUserManagement;
    private final String command;
    private String path;
    
    public Cat(TreeFileSystem tfs, UserManagement um, String com) {
        mTreeFileSystem = tfs;
        mUserManagement = um;
        command = com;
        this.parseCommand();
    }
    
    private void parseCommand() {
        StringTokenizer mStringTokenizer = new StringTokenizer(command);
        mStringTokenizer.nextElement();
        
        path = mStringTokenizer.nextToken();
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, mTreeFileSystem);
        try {
            cmd.cat(path);
        } catch (InvalidFileNameException | DeniedPermissionException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
    
}
