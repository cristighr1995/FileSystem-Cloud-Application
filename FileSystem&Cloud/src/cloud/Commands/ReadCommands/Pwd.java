package cloud.Commands.ReadCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Exceptions.FilePathTooLongException;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pwd extends ReadCommand implements Command {
    private final TreeFileSystem mTreeFileSystem;
    private final UserManagement mUserManagement;

    public Pwd(TreeFileSystem tfs, UserManagement um) {
        mTreeFileSystem = tfs;
        mUserManagement = um;
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, mTreeFileSystem);
        try {
            cmd.pwd();
        } catch (FilePathTooLongException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
    
}
