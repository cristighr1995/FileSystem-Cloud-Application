package cloud.CommandsConfiguration;

import cloud.CloudServiceSystem.CloudService;
import cloud.Commands.CloudCommands.Listcloud;
import cloud.Commands.CloudCommands.Sync;
import cloud.Commands.CloudCommands.Upload;
import cloud.Commands.Command;
import cloud.Commands.IndependentCommands.Deluser;
import cloud.Commands.IndependentCommands.Echo;
import cloud.Commands.IndependentCommands.Login;
import cloud.Commands.IndependentCommands.Logout;
import cloud.Commands.IndependentCommands.Newuser;
import cloud.Commands.IndependentCommands.Userinfo;
import cloud.Commands.ReadCommands.Cat;
import cloud.Commands.ReadCommands.Cd;
import cloud.Commands.ReadCommands.Ls;
import cloud.Commands.ReadCommands.Pwd;
import cloud.Commands.WriteCommands.Mkdir;
import cloud.Commands.WriteCommands.Rm;
import cloud.Commands.WriteCommands.Touch;
import cloud.Configurations.SystemConfiguration;
import cloud.Exceptions.InvalidCommandException;
import cloud.Exceptions.InvalidCommandFormatException;
import cloud.Observator.Subject;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import cloud.Utils.AlphabeticComparator;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class CommandsFactory {
    private final TreeFileSystem mTreeFileSystem;
    private final UserManagement mUserManagement;
    private final SystemConfiguration mSystemConfiguration;
    private final CloudService mCloudService;
    private String command;
    private final TreeSet<String> validCommands;
    private final Subject mSubject = CommandConfiguration.getSubject();
    
    public CommandsFactory(TreeFileSystem tfs, UserManagement um, String com, SystemConfiguration sc, CloudService cs) {
        mTreeFileSystem = tfs;
        mUserManagement = um;
        command = com;
        mSystemConfiguration = sc;
        mCloudService = cs;
        
        validCommands = new TreeSet<>(new AlphabeticComparator());
        this.fillValidCommands();
    }
    
    // add valid commands
    private void fillValidCommands() {
        validCommands.add("ls");
        validCommands.add("cd");
        validCommands.add("cat");
        validCommands.add("pwd");
        validCommands.add("mkdir");
        validCommands.add("rm");
        validCommands.add("touch");
        validCommands.add("echo");
        validCommands.add("login");
        validCommands.add("logout");
        validCommands.add("newuser");
        validCommands.add("deluser");
        validCommands.add("userinfo");
        validCommands.add("upload");
        validCommands.add("sync");
        validCommands.add("listcloud");
    }
    
    private boolean validateCommand(String commandType) {
        String pattern;
        
        if(commandType.equals("pwd") || commandType.equals("logout") || commandType.equals("listcloud")) {
            pattern = "^(\\s)*\\w+(\\s)*$";
            return Pattern.matches(pattern, command);
        }
        if(commandType.equals("userinfo")) {
            pattern = "^(\\s)*\\w+(\\s-POO)?(\\s)*$";
            return Pattern.matches(pattern, command);
        }
        if(commandType.equals("deluser")) {
            pattern = "^(\\s)*\\w+\\s\\w+(\\s)*$";
            return Pattern.matches(pattern, command);
        }
        if(commandType.equals("cd") || commandType.equals("upload") || commandType.equals("sync") || commandType.equals("cat")) {
            pattern = "^(\\s)*\\w+(\\s(-r|-ar))?\\s(/?((\\.){2})?/?[a-zA-Z0-9]?/?){1,}(\\.\\w+)?(\\s)*$";
            return Pattern.matches(pattern, command);
        }   
        if(commandType.equals("echo")) {
            pattern = "^(\\s)*\\w+\\s[a-zA-Z0-9 ]+(\\s-POO)?(\\s)*$";
            return Pattern.matches(pattern, command);
        }
        if(commandType.equals("ls")) {
            pattern = "^(\\s)*\\w+(\\s((-r|-a|-ar)))?(\\s(/?((\\.){2}/?)?/?[a-zA-Z0-9]?/?){1,}(\\.\\w+)?)?(\\s-POO)?(\\s)*$";
            return Pattern.matches(pattern, command);
        }
        if(commandType.equals("rm")) {
            pattern = "^(\\s)*\\w+(\\s((-r|-ar)))?\\s(/?((\\.){2}/?)?/?[a-zA-Z0-9]?/?){1,}(\\.\\w+)?(\\s)*$";
            return Pattern.matches(pattern, command);
        }
        if(commandType.equals("mkdir") || commandType.equals("touch")) {
            pattern = "^(\\s)*\\w+\\s[0-9]+\\s(/?((\\.){2}/?)?/?[a-zA-Z0-9]?/?){1,}(\\.\\w+)?(\\s)*$";
            return Pattern.matches(pattern, command);
        }
        if(commandType.equals("login")) {
            pattern = "^(\\s)*\\w+\\s\\w+\\s\\w+(\\s)*$";
            return Pattern.matches(pattern, command);
        }
        if(commandType.equals("newuser")) {
            pattern = "^(\\s)*\\w+\\s\\w+\\s\\w+\\s\\w+\\s\\w+(\\s)*$";
            return Pattern.matches(pattern, command);
        }
        
        return false;
    }
    
    public Command getCommand(String commandType) throws Exception {
        if(commandType == null)
            return null;
        
        // if tried other command than those valid
        if(!validCommands.contains(commandType)) {
            CommandConfiguration.setReturnString("Invalid : Comanda invalida!");
            mSubject.setStateException(new InvalidCommandException());
            throw new InvalidCommandException();
        }
        
        // if the command doesn't match its corresponding format
        if(!validateCommand(commandType)){
            CommandConfiguration.setReturnString("Invalid : Format invalid al comenzii...");
            mSubject.setStateException(new InvalidCommandFormatException());
            throw new InvalidCommandFormatException();
        }
        
        // decide which command was typed
        if(commandType.equalsIgnoreCase("ls"))
            return new Ls(mTreeFileSystem, mUserManagement, command);
        if(commandType.equalsIgnoreCase("cd"))
            return new Cd(mTreeFileSystem, mUserManagement, command);
        if(commandType.equalsIgnoreCase("cat"))
            return new Cat(mTreeFileSystem, mUserManagement, command);
        if(commandType.equalsIgnoreCase("pwd"))
            return new Pwd(mTreeFileSystem, mUserManagement);
        
        if(commandType.equalsIgnoreCase("mkdir"))
            return new Mkdir(mTreeFileSystem, mUserManagement, command, mSystemConfiguration);
        if(commandType.equalsIgnoreCase("touch"))
            return new Touch(mTreeFileSystem, mUserManagement, command, mSystemConfiguration);
        if(commandType.equalsIgnoreCase("rm"))
            return new Rm(mTreeFileSystem, mUserManagement, command, mSystemConfiguration);
        
        if(commandType.equalsIgnoreCase("echo"))
            return new Echo(command);
        if(commandType.equalsIgnoreCase("newuser"))
            return new Newuser(mTreeFileSystem, mUserManagement, mSystemConfiguration, command);
        if(commandType.equalsIgnoreCase("login"))
            return new Login(mUserManagement, mSystemConfiguration, command);
        if(commandType.equalsIgnoreCase("logout"))
            return new Logout(mUserManagement);
        if(commandType.equalsIgnoreCase("userinfo"))
            return new Userinfo(mUserManagement);
        if(commandType.equalsIgnoreCase("deluser"))
            return new Deluser(mUserManagement, command, mSystemConfiguration);
        if(commandType.equalsIgnoreCase("upload"))
            return new Upload(mTreeFileSystem, mUserManagement, command, mCloudService);
        if(commandType.equalsIgnoreCase("sync"))
            return new Sync(mTreeFileSystem, mUserManagement, command, mCloudService, mSystemConfiguration);
        if(commandType.equalsIgnoreCase("listcloud"))
            return new Listcloud(mCloudService);
        
        return null;
    }
    
    public String getStringCommand() {
        return command;
    }
    
    public void newCommand(String com) {
        command = com;
    }
}
