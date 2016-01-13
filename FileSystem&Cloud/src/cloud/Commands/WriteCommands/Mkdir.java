package cloud.Commands.WriteCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Configurations.SystemConfiguration;
import cloud.Exceptions.DeniedPermissionException;
import cloud.Exceptions.ForgetToPassPermissionNumberException;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import cloud.Utils.Parameter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mkdir extends WriteCommand implements Command {
    private final TreeFileSystem mTreeFileSystem;
    private final UserManagement mUserManagement;
    private final SystemConfiguration mSystemConfiguration;
    private final String command;
    private String directorName;
    private String path;
    private int paramNr;
    
    public Mkdir(TreeFileSystem tfs, UserManagement um, String com, SystemConfiguration sc)  {
        mTreeFileSystem = tfs;
        mUserManagement = um;
        command = com;
        mSystemConfiguration = sc;
        this.parseCommand();
    }
    
    private void parseCommand() {
        try {
            ArrayList<String> components = new ArrayList<>();
            Parameter mParameter = new Parameter(command);
            paramNr = mParameter.getPermNr();
            String absPath = mParameter.getFDName();
            StringTokenizer mStringTokenizer = new StringTokenizer(absPath, "/");
            
            while(mStringTokenizer.hasMoreTokens())
                components.add(mStringTokenizer.nextToken());
            
            StringBuilder sb = new StringBuilder(absPath.length());
            
            if(absPath.startsWith("/"))
                sb.append("/");
             
            for(int i = 0; i < components.size() - 1; i++)
                sb.append(components.get(i)).append("/");
            
            path = sb.toString();
            directorName = components.get(components.size() - 1);
            
        } catch (ForgetToPassPermissionNumberException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(Mkdir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, mTreeFileSystem);
        
        try {
            cmd.mkdir(path, directorName, paramNr);
            mSystemConfiguration.updateDirectorInFile(mTreeFileSystem);
        } catch (ForgetToPassPermissionNumberException | DeniedPermissionException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
    
}
