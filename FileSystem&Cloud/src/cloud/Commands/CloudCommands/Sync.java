package cloud.Commands.CloudCommands;

import cloud.CloudServiceSystem.CloudService;
import cloud.Commands.Command;
import cloud.Configurations.SystemConfiguration;
import cloud.Exceptions.CloudSpaceFullException;
import cloud.Exceptions.InexistentFileCloudException;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sync implements Command {
    private final UserManagement mUserManagement;
    private final TreeFileSystem mTreeFileSystem;
    private final SystemConfiguration mSystemConfiguration;
    private final String command;
    private final CloudService mCloudService;
    private String dName;
    
    public Sync(TreeFileSystem tfs, UserManagement um, String com, CloudService cs, SystemConfiguration sc) {
        mUserManagement = um;
        mTreeFileSystem = tfs;
        command = com;
        mCloudService = cs;
        mSystemConfiguration = sc;
        this.parseCommand();
    }
    
    private void parseCommand() {
        StringTokenizer mStringTokenizer = new StringTokenizer(command);
        mStringTokenizer.nextElement();
        
        dName = mStringTokenizer.nextToken();
    }

    @Override
    public void execute() {
        try {
            mCloudService.sync(dName);
            mSystemConfiguration.updateDirectorInFile(mTreeFileSystem);
            mSystemConfiguration.updateFileInFile(mTreeFileSystem);
        } catch (InexistentFileCloudException | CloudSpaceFullException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
}
