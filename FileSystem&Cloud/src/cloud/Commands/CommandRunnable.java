package cloud.Commands;

import cloud.CloudServiceSystem.CloudService;
import cloud.CommandsConfiguration.CommandsFactory;
import cloud.Configurations.SystemConfiguration;
import cloud.Exceptions.InvalidCommandException;
import cloud.Exceptions.InvalidCommandFormatException;
import cloud.Exceptions.InvalidFileNameException;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

// Class similar to Runnable
// It works until the system is shutdown
public class CommandRunnable {
    Scanner sc = new Scanner(System.in);
    private final CommandsFactory mCommandsFactory;
    private final TreeFileSystem mTreeFileSystem;
    private final UserManagement mUserManagement;
    private final SystemConfiguration mSystemConfiguration;
    private final CloudService mCloudService;
    private String commandString;
    private String cmdStrReturn;
    
    public CommandRunnable(TreeFileSystem tfs, UserManagement um, String com, SystemConfiguration sc, CloudService cs) {
        mCommandsFactory = new CommandsFactory(tfs, um, com, sc, cs);
        mTreeFileSystem = tfs;
        mUserManagement = um;
        commandString = com;
        mSystemConfiguration = sc;
        mCloudService = cs;
    }

    public String getCmdStrReturn() {
        return cmdStrReturn;
    }
    
    public void setCmdStrReturn(String str) {
        cmdStrReturn = str;
    }
    
    public void run(String cmdString) {
        commandString = cmdString;
        mCommandsFactory.newCommand(commandString);
        
        if (commandString.compareTo("exit") != 0) {
            if (!commandString.trim().isEmpty()) {
                Command mCommand;
                StringTokenizer mStringTokenizer = new StringTokenizer(commandString);

                String commandType = mStringTokenizer.nextToken();
                
                try {
                    mCommand = mCommandsFactory.getCommand(commandType);
                    mCommand.execute();
                }
                catch(InvalidFileNameException | InvalidCommandException | InvalidCommandFormatException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    Logger.getLogger(CommandRunnable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else {
            // shutdown the system
            System.exit(0);
        }
    }
    
}
