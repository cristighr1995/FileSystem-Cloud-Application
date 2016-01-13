package cloud.Commands.CloudCommands;

import cloud.CloudServiceSystem.CloudService;
import cloud.Commands.Command;
import cloud.Exceptions.CloudSpaceFullException;
import cloud.Exceptions.InvalidFileNameException;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import java.util.StringTokenizer;

public class Upload implements Command {
    private final UserManagement mUserManagement;
    private final TreeFileSystem mTreeFileSystem;
    private final String command;
    private final CloudService mCloudService;
    private String dName;

    public Upload(TreeFileSystem tfs, UserManagement um, String com, CloudService cs) {
        mUserManagement = um;
        mTreeFileSystem = tfs;
        command = com;
        mCloudService = cs;
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
            mCloudService.upload(dName);
        } catch (InvalidFileNameException | CloudSpaceFullException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        } 
    }
    
}
