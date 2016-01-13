package cloud.Configurations;

import cloud.Files.File;
import cloud.Folders.Director;
import cloud.Permissions.Permission;
import cloud.ProgramFiles.Node;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import java.io.*;
import java.util.*;

// Class used for reconfiguring the system after shutdown or restart
public class SystemConfiguration {
    private final UserManagement mUserManagement;
    private final TreeFileSystem mTreeFileSystem;
    private final static String inputUserPath = "src\\cloud\\Configurations\\users.txt";
    private final static String inputDirectorPath = "src\\cloud\\Configurations\\directors.txt";
    private final static String inputFilePath = "src\\cloud\\Configurations\\files.txt";
    
    public SystemConfiguration() {
        mUserManagement = UserManagement.getInstance();
        mTreeFileSystem = new TreeFileSystem();
        
        Permission<UserManagement.User> pGuest = new Permission<>(true, false, mUserManagement.getGuestId());
        Permission<UserManagement.User> pRoot = new Permission<>(true, true, mUserManagement.getRootId());
        
        ArrayList<Permission> perms = new ArrayList<>();
        perms.add(pGuest);
        perms.add(pRoot);
        
        this.readUsersFromFile();
        
        UserManagement.User root = mUserManagement.findUser("root");
        UserManagement.User guest = mUserManagement.findUser("guest");
        
        // Foreach user reconfigure its permissions
        Iterator<UserManagement.User> it = mUserManagement.iterator();
        while(it.hasNext()) {
            UserManagement.User itUser = it.next();
            if(!itUser.equals(root) && !itUser.equals(guest)) {
                perms.add(new Permission(true, true, itUser));
            }
        }
            
        Director dRoot = new Director("root", mUserManagement.getRootId(), perms, 3);
        this.mTreeFileSystem.addNode(dRoot);
        
        this.readDirectorsFromFile();
        this.readFilesFromFile();
        
        ArrayList<Director> rootChildren = mTreeFileSystem.getNodes().get(dRoot).getChildren();
        
        rootChildren.stream().forEach((dir) -> {
            dRoot.updateDimension(dRoot.getDimension() + dir.getDimension());
        });
    }
    
    public UserManagement getExistingUsers() {
        return this.mUserManagement;
    }
    
    public TreeFileSystem getSystemConfiguration() {
        return this.mTreeFileSystem;
    }
    
    private void readUsersFromFile() {
        FileReader stream;
        try {
            stream = new FileReader(inputUserPath);
            try (BufferedReader br = new BufferedReader(stream)) {
                String line = br.readLine();
                while(line != null) {
                    StringTokenizer mStringTokenizer = new StringTokenizer(line, "--");
                    
                    String username, password, lastName, firstName, timeCreated, timeLastLogin;
                    username = mStringTokenizer.nextToken();
                    password = mStringTokenizer.nextToken();
                    lastName = mStringTokenizer.nextToken();
                    firstName = mStringTokenizer.nextToken();
                    timeCreated = mStringTokenizer.nextToken();
                    timeLastLogin = mStringTokenizer.nextToken();
                    
                    boolean result = mUserManagement.newuser(mUserManagement.new User(username, password, lastName, firstName, timeCreated, timeLastLogin));
                    line = br.readLine();
                }
            }
            stream.close();
        }
        catch(Exception e) {
        }
    }
    
    public void updateUsersInFile(UserManagement um) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(inputUserPath, false)))) {
            Iterator<UserManagement.User> it = mUserManagement.iterator();
            while(it.hasNext()) {
                UserManagement.User itUser = it.next();
                pw.println(itUser);
            }
        }
        catch (IOException e) {
        }
    }
    
    private void readDirectorsFromFile() {
        FileReader stream;
        try {
            stream = new FileReader(inputDirectorPath);
            try (BufferedReader br = new BufferedReader(stream)) {
                String line = br.readLine();
                while(line != null) {
                    StringTokenizer mStringTokenizer = new StringTokenizer(line, "--");
                    
                    String name, parentName, owner, timeCreated, path;
                    UserManagement.User userOwn;
                    double dimension;
                    int identifier, permNr;
                    boolean canRead, canWrite;
                    String user;
                    ArrayList<Permission> perms = new ArrayList<>();
                    
                    name = mStringTokenizer.nextToken();
                    parentName = mStringTokenizer.nextToken();
                    dimension = Double.parseDouble(mStringTokenizer.nextToken());
                    owner = mStringTokenizer.nextToken();
                    userOwn = mUserManagement.findUser(owner);
                    timeCreated = mStringTokenizer.nextToken();
                    identifier = Integer.parseInt(mStringTokenizer.nextToken());
                    path = mStringTokenizer.nextToken();
                    permNr = Integer.parseInt(mStringTokenizer.nextToken());
                    
                    String line2 = br.readLine();
                    StringTokenizer mStringBrackets = new StringTokenizer(line2, "[]");
                    line2 = mStringBrackets.nextToken();
                    StringTokenizer mStringTokenizerL2LinePermissions = new StringTokenizer(line2, ", ");

                    while(mStringTokenizerL2LinePermissions.hasMoreTokens()) {
                        String p = mStringTokenizerL2LinePermissions.nextToken();
                        StringTokenizer mStringTokenizerL2SinglePermission = new StringTokenizer(p, "--");
                        user = mStringTokenizerL2SinglePermission.nextToken();
                        canRead = Boolean.parseBoolean(mStringTokenizerL2SinglePermission.nextToken());
                        canWrite = Boolean.parseBoolean(mStringTokenizerL2SinglePermission.nextToken());
                        perms.add(new Permission(canRead, canWrite, mUserManagement.findUser(user)));
                    }
                    
                    Director newDir = new Director(name, dimension, userOwn, perms, timeCreated, identifier, path, permNr);
                    Director parentDir = mTreeFileSystem.findDirectorByName(parentName);
                    
                    mTreeFileSystem.addNode(newDir, parentDir);
                    
                    line = br.readLine();
                }
            }
        }
        catch(IOException | NumberFormatException e) {
        }
    }
    
    private void readFilesFromFile() {
        FileReader stream;
        try {
            stream = new FileReader(inputFilePath);
            try (BufferedReader br = new BufferedReader(stream)) {
                String line = br.readLine();
                while(line != null) {
                    StringTokenizer mStringTokenizer = new StringTokenizer(line, "--");
                    
                    String name, parentName, owner, timeCreated, path, type;
                    UserManagement.User userOwn;
                    double dimension;
                    int identifier, permNr;
                    boolean canRead, canWrite;
                    String user;
                    ArrayList<Permission> perms = new ArrayList<>();
                    byte[] content;
                    
                    name = mStringTokenizer.nextToken();
                    parentName = mStringTokenizer.nextToken();
                    dimension = Double.parseDouble(mStringTokenizer.nextToken());
                    owner = mStringTokenizer.nextToken();
                    userOwn = mUserManagement.findUser(owner);
                    timeCreated = mStringTokenizer.nextToken();
                    identifier = Integer.parseInt(mStringTokenizer.nextToken());
                    path = mStringTokenizer.nextToken();
                    permNr = Integer.parseInt(mStringTokenizer.nextToken());
                    type = mStringTokenizer.nextToken();
                    content = mStringTokenizer.nextToken().getBytes();
                    
                    String line2 = br.readLine();
                    StringTokenizer mStringBrackets = new StringTokenizer(line2, "[]");
                    line2 = mStringBrackets.nextToken();
                    StringTokenizer mStringTokenizerL2LinePermissions = new StringTokenizer(line2, ", ");
                    
                    while(mStringTokenizerL2LinePermissions.hasMoreTokens()) {
                        String p = mStringTokenizerL2LinePermissions.nextToken();
                        StringTokenizer mStringTokenizerL2SinglePermission = new StringTokenizer(p, "--");
                        user = mStringTokenizerL2SinglePermission.nextToken();
                        canRead = Boolean.parseBoolean(mStringTokenizerL2SinglePermission.nextToken());
                        canWrite = Boolean.parseBoolean(mStringTokenizerL2SinglePermission.nextToken());
                        perms.add(new Permission(canRead, canWrite, mUserManagement.findUser(user)));
                    }
                    
                    File newFile = new File(name, dimension, userOwn, perms, timeCreated, identifier, path, permNr, type, content);
                    Director parentDir = mTreeFileSystem.findDirectorByName(parentName);
                    
                    mTreeFileSystem.addNode(newFile, parentDir);
                    
                    line = br.readLine();
                }
            }
        }
        catch(IOException | NumberFormatException e) {
        }
    }
    
    public void updateDirectorInFile(TreeFileSystem tfs) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(inputDirectorPath, false)))) {
            Iterator<Node> depthIterator = tfs.iterator(tfs.getRootDirector());

            while (depthIterator.hasNext()) {
                Node node = depthIterator.next();
                
                Director currentDir = node.getCurrentDirector();
                
                if(!currentDir.equals(mTreeFileSystem.findDirectorByName("root"))) {
                
                    String newDirLine = currentDir.toString();
                    Director parentDir = tfs.getNodes().get(currentDir).getParentDirector();
                
                    if(currentDir instanceof Director && !(currentDir instanceof File)) {
                        StringBuilder sb = new StringBuilder(newDirLine.length() + parentDir.getName().length() + 2);
                        sb.append(currentDir.getName()).append("--");
                        sb.append(parentDir.getName()).append("--");
                        sb.append(newDirLine.substring(currentDir.getName().length() + 2));
            
                        pw.println(sb.toString());
                        pw.println(currentDir.getPermissions());
                    }
                }
            }
        }
        catch(IOException e){
        }
    }
    
    public void updateFileInFile(TreeFileSystem tfs) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(inputFilePath, false)))) {
            Iterator<Node> depthIterator = tfs.iterator(tfs.getRootDirector());

            while (depthIterator.hasNext()) {
                Node node = depthIterator.next();
                
                Director currentDir = node.getCurrentDirector();
                
                String newDirLine = currentDir.toString();
                Director parentDir = tfs.getNodes().get(currentDir).getParentDirector();
                if(parentDir == null)
                    parentDir = currentDir;
                
                if(currentDir instanceof File) {
                    StringBuilder sb = new StringBuilder(newDirLine.length() + parentDir.getName().length() + 2);
                    sb.append(currentDir.getName()).append("--");
                    sb.append(parentDir.getName()).append("--");
                    sb.append(newDirLine.substring(currentDir.getName().length() + 2));
            
                    pw.println(sb.toString());
                    pw.println(currentDir.getPermissions());
                }
            }
        }
        catch(IOException e){
        }
    }
}
