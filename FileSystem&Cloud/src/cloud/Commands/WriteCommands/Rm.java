package cloud.Commands.WriteCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Configurations.SystemConfiguration;
import cloud.Exceptions.DeniedPermissionException;
import cloud.Exceptions.ForgetToPassPermissionNumberException;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Rm extends WriteCommand implements Command {
    private final TreeFileSystem mTreeFileSystem;
    private final UserManagement mUserManagement;
    private final SystemConfiguration mSystemConfiguration;
    private final String command;
    private String fdName;
    private String path;
    private String param;
    
    public Rm(TreeFileSystem tfs, UserManagement um, String com, SystemConfiguration sc) {
        mTreeFileSystem = tfs;
        mUserManagement = um;
        command = com;
        mSystemConfiguration = sc;
        this.parseCommand();
    }
    
    private void parseCommand() {
        StringTokenizer mStringTokenizer = new StringTokenizer(command);
        mStringTokenizer.nextElement();
        ArrayList<String> components = new ArrayList<>();
        ArrayList<String> moves = new ArrayList<>();
        
        while (mStringTokenizer.hasMoreTokens()) {
            components.add(mStringTokenizer.nextToken());
        }

        String absPath;
        
        param = components.get(0);
        if(!param.startsWith("-")) {
            param = "";
            absPath = components.get(0);
        }
        else
            absPath = components.get(1);
        
        StringTokenizer mStringTokenizer2 = new StringTokenizer(absPath, "/");

        while (mStringTokenizer2.hasMoreTokens()) {
            moves.add(mStringTokenizer2.nextToken());
        }

        StringBuilder sb = new StringBuilder(absPath.length());

        if (absPath.startsWith("/")) {
            sb.append("/");
        }

        for (int i = 0; i < moves.size() - 1; i++) {
            sb.append(moves.get(i)).append("/");
        }

        path = sb.toString();
        fdName = moves.get(moves.size() - 1);
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, mTreeFileSystem);
        try {
            cmd.rm(path, fdName, param);
            mSystemConfiguration.updateFileInFile(mTreeFileSystem);
            mSystemConfiguration.updateDirectorInFile(mTreeFileSystem);
        } catch (ForgetToPassPermissionNumberException | DeniedPermissionException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
    
}
