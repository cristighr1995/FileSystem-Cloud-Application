package cloud.Commands.ReadCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Exceptions.DeniedPermissionException;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Ls extends ReadCommand implements Command {
    private final TreeFileSystem mTreeFileSystem;
    private final UserManagement mUserManagement;
    private final String command;
    private ArrayList<String> components;
    
    public Ls(TreeFileSystem tfs, UserManagement um, String com) {
        mTreeFileSystem = tfs;
        mUserManagement = um;
        command = com;
        this.parseCommand();
    }
    
    private void parseCommand() {
        StringTokenizer mStringTokenizer = new StringTokenizer(command);
        components = new ArrayList<>();
        mStringTokenizer.nextElement();

        while (mStringTokenizer.hasMoreTokens()) {
            components.add(mStringTokenizer.nextToken());
        }
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, mTreeFileSystem);
        try {
            if (components.isEmpty()) {
                cmd.ls();
            }
            if (components.size() == 1) {
                cmd.ls(components.get(0));
            }
            if (components.size() == 2 || components.size() == 3) {
                cmd.ls(components.get(0), components.get(1));
            }
        } catch (DeniedPermissionException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
}
