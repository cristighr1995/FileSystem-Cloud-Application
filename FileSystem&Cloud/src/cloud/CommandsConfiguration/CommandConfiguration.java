package cloud.CommandsConfiguration;

import cloud.Exceptions.*;
import cloud.Files.File;
import cloud.Folders.Director;
import cloud.Observator.ExceptionObserver;
import cloud.Observator.Subject;
import cloud.Observator.UsersObserver;
import cloud.Permissions.Permission;
import cloud.ProgramFiles.Node;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import cloud.Users.UserManagement.User;
import cloud.Utils.Parameter;
import java.text.SimpleDateFormat;
import java.util.*;

// All methods of the commands are here
public class CommandConfiguration implements ICommandReadable, ICommandWriteable, ICommandUserManageable {

    private final UserManagement mUserManagement;
    private final TreeFileSystem mTreeFile;
    private Director start;
    private static String returnStr;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Calendar mCalendar;
    private static final Subject mSubject = new Subject();
    private static final ExceptionObserver eo = new ExceptionObserver(mSubject);
    private static final UsersObserver uo = new UsersObserver(mSubject);

    public CommandConfiguration(UserManagement um, TreeFileSystem tfs) {
        this.mUserManagement = um;
        this.mTreeFile = tfs;
    }

    public static String getReturnString() {
        return returnStr;
    }

    public static void setReturnString(String setStr) {
        returnStr = setStr;
    }

    public static Subject getSubject() {
        return mSubject;
    }

    // ls just with another string after the command
    // like ls director or ls -ar
    // need to check if it's a path or a parameter
    public void ls(String string) throws Exception {
        StringBuilder sb = new StringBuilder();
        // if contains '-' the string needs to be a parameter
        if (string.contains("-")) {
            // check what parameter
            if (string.compareTo("-r") == 0 || string.compareTo("-ar") == 0) {
                UserManagement.User currentUser = mUserManagement.id();
                Director currentDirector = mTreeFile.getCurrentDirector();

                Iterator<Node> depthIterator = mTreeFile.iterator(currentDirector);

                // iterate through tree and print the directories/files if the current user have permissions
                while (depthIterator.hasNext()) {
                    Node node = depthIterator.next();
                    Director director = node.getCurrentDirector();

                    if (director.getUserPermissions(currentUser).getReadPermission()) {
                        if (string.compareTo("-r") == 0) {
                            sb.append(director.getPath()).append("\n");
                        } else {
                            if (director instanceof File) {
                                sb.append("[F] ");
                            } else {
                                sb.append("[D] ");
                            }
                            
                            sb.append(director.getName()).append(" ").append(director.getOwner().getUsername()).append(" [").append(director.getTimeCreated()).append("] ").append(director.getDimension()).append("kB ").append(director.getPermNr()).append("\n");
                        }
                    }
                }
                returnStr = sb.toString().substring(0, sb.toString().length() - 1);
            } else {
                if (string.compareTo("-a") == 0) {
                    UserManagement.User currentUser = mUserManagement.id();
                    Director currentDirector = mTreeFile.getCurrentDirector();

                    if (currentDirector.getUserPermissions(currentUser).getReadPermission()) {
                        mTreeFile.getNodes().get(currentDirector).getChildren().stream().forEach((dir) -> {
                            if (dir instanceof File) {
                                sb.append("[F] ");
                            } else {
                                sb.append("[D] ");
                            }

                            sb.append(dir.getName()).append(" ").append(dir.getOwner().getUsername()).append(" [").append(dir.getTimeCreated()).append("] ").append(dir.getDimension()).append("kB ").append(dir.getPermNr()).append("\n");
                        });

                        returnStr = sb.toString().substring(0, sb.toString().length() - 1);
                    }
                } else if (string.compareTo("-POO") == 0) {
                    this.ls();
                } else {
                    mTreeFile.changeCurrentDirector(start);
                    returnStr = "Invalid : Parametru Invalid!";
                    mSubject.setStateException(new InvalidParameterException());
                    throw new InvalidParameterException();
                }
            }
        } else {
            try {
                Director currentDirector = mTreeFile.getCurrentDirector();
                if (string.compareTo("-POO") == 0) {
                    this.ls();
                } else {
                    this.cd(string);
                    this.ls();
                }
                mTreeFile.changeCurrentDirector(currentDirector);
            } catch (InvalidPathExceptionParameter | DeniedPermissionException | InvalidChangeDirectoryToFileException e) {
                e.printStackTrace();
                returnStr = "Invalid : " + e.getMessage();
            }
        }
    }

    // list directories/files from a path with a paramater
    public void ls(String param, String path) throws Exception {
        try {
            Director currentDirector = mTreeFile.getCurrentDirector();
            start = currentDirector;
            if (path.compareTo("-POO") == 0) {
                this.ls(param);
            } else {
                this.cd(path);
                this.ls(param);
            }
            mTreeFile.changeCurrentDirector(currentDirector);
        } catch (InvalidParameterException | InvalidPathExceptionParameter | DeniedPermissionException | InvalidChangeDirectoryToFileException e) {
            e.printStackTrace();
            returnStr = "Invalid : " + e.getMessage();
        }
    }

    // list directories/files from current director
    public void ls() throws Exception {
        UserManagement.User currentUser = mUserManagement.id();
        Director currentDirector = mTreeFile.getCurrentDirector();
        StringBuilder sb = new StringBuilder();

        if (currentDirector.getUserPermissions(currentUser).getReadPermission()) {

            mTreeFile.getNodes().get(currentDirector).getChildren().stream().forEach((dir) -> {
                sb.append(dir.getName()).append(" ");
            });
            returnStr = sb.toString();
        } else {
            returnStr = "Invalid : Nu aveti permisiuni necesare";
            mSubject.setStateException(new DeniedPermissionException());
            throw new DeniedPermissionException();
        }
    }

    // change path 
    public void cd(String path) throws Exception {
        StringTokenizer mStringTokenizer = new StringTokenizer(path, "/");
        ArrayList<String> moves = new ArrayList<>();
        
        UserManagement.User currentUser = mUserManagement.id();
        Director first = mTreeFile.getCurrentDirector();
        returnStr = "";

        // parse the path after '/'
        if (path.startsWith("/")) {
            // add each director to moves for better observing them
            while (mStringTokenizer.hasMoreTokens()) {
                moves.add(mStringTokenizer.nextToken());
            }
            // if starts with / need to change director to the root
            mTreeFile.changeCurrentDirector(mTreeFile.getRootDirector());

            Director currentDirector = mTreeFile.getCurrentDirector();
            Node currentNode = mTreeFile.getNodes().get(currentDirector);

            // reconstruct the way to the wanted path
            for (String move : moves) {
                Director destination = mTreeFile.findDirectorByName(move);
                
                // the path isn't valid
                if (destination == null) {
                    mTreeFile.changeCurrentDirector(first);
                    returnStr = "Invalid : Calea trimisa nu este valida!";
                    mSubject.setStateException(new InvalidPathExceptionParameter());
                    throw new InvalidPathExceptionParameter();
                }
                // can't change path with a file
                if (destination instanceof File) {
                    mTreeFile.changeCurrentDirector(first);
                    returnStr = "Invalid : Nu poti sa schimbi calea cu un fisier!";
                    mSubject.setStateException(new InvalidChangeDirectoryToFileException());
                    throw new InvalidChangeDirectoryToFileException();
                }

                ArrayList<Director> children = currentNode.getChildren();
                boolean found = false;

                for (Director child : children) {
                    if (child.equals(destination)) {
                        found = true;
                    }
                }

                if (!found) {
                    mTreeFile.changeCurrentDirector(first);
                    returnStr = "Invalid : Calea trimisa nu este valida!";
                    mSubject.setStateException(new InvalidPathExceptionParameter());
                    throw new InvalidPathExceptionParameter();
                }

                if (destination.getUserPermissions(currentUser).getReadPermission()) {
                    currentDirector = destination;
                    currentNode = mTreeFile.getNodes().get(currentDirector);
                    mTreeFile.changeCurrentDirector(currentDirector);
                } else {
                    mTreeFile.changeCurrentDirector(first);
                    returnStr = "Invalid : Nu aveti permisiuni necesare";
                    mSubject.setStateException(new DeniedPermissionException());
                    throw new DeniedPermissionException();
                }
            }
            returnStr = "Success";
            return;
        }
        
        // if here, it means the path doesn't start with '/'
        // so add the rest of path to moves
        while (mStringTokenizer.hasMoreTokens()) {
            moves.add(mStringTokenizer.nextToken());
        }

        Director currentDirector = mTreeFile.getCurrentDirector();
        Node currentNode = mTreeFile.getNodes().get(currentDirector);

        for (String move : moves) {
            if (move.compareTo("..") == 0) {
                currentDirector = currentNode.getParentDirector();
                currentNode = mTreeFile.getNodes().get(currentDirector);
                mTreeFile.changeCurrentDirector(currentDirector);
            } else {
                Director destination = mTreeFile.findDirectorByName(move);

                // can't change the destination to a file
                if (destination instanceof File) {
                    mTreeFile.changeCurrentDirector(first);
                    returnStr = "Invalid : Nu poti sa schimbi calea cu un fisier!";
                    mSubject.setStateException(new InvalidChangeDirectoryToFileException());
                    throw new InvalidChangeDirectoryToFileException();
                }

                // path invalid
                if (destination == null) {
                    mTreeFile.changeCurrentDirector(first);
                    returnStr = "Invalid : Calea trimisa nu este valida!";
                    mSubject.setStateException(new InvalidPathExceptionParameter());
                    throw new InvalidPathExceptionParameter();
                } else {
                    ArrayList<Director> children = currentNode.getChildren();
                    boolean found = false;

                    for (Director child : children) {
                        if (child.equals(destination)) {
                            found = true;
                        }
                    }

                    if (!found) {
                        mTreeFile.changeCurrentDirector(first);
                        returnStr = "Invalid : Calea trimisa nu este valida!";
                        mSubject.setStateException(new InvalidPathExceptionParameter());
                        throw new InvalidPathExceptionParameter();
                    }

                    if (destination.getUserPermissions(currentUser).getReadPermission()) {
                        currentDirector = destination;
                        currentNode = mTreeFile.getNodes().get(currentDirector);
                        mTreeFile.changeCurrentDirector(currentDirector);
                    } else {
                        returnStr = "Invalid : Nu aveti permisiuni necesare";
                        mSubject.setStateException(new DeniedPermissionException());
                        throw new DeniedPermissionException();
                    }
                }
            }
        }

        returnStr = "Success";
    }

    // show content of a file, from a path give as argument
    public void cat(String path) throws Exception {
        StringTokenizer mStringTokenizer = new StringTokenizer(path, "/");
        // current director from where i called this command
        Director first = mTreeFile.getCurrentDirector();
        UserManagement.User currentUser = mUserManagement.id();
        // store the path tokenized
        // the path needs to be reconstruct after by components
        ArrayList<String> components = new ArrayList<>();

        if (path.startsWith("/")) {
            components.add("/");
        }

        while (mStringTokenizer.hasMoreTokens()) {
            components.add(mStringTokenizer.nextToken());
        }

        String fileNameWithExtension = components.get(components.size() - 1);
        StringTokenizer mPoint = new StringTokenizer(fileNameWithExtension, ".");

        String fileName = mPoint.nextToken();
        String extension = "";
        if (mPoint.hasMoreTokens()) {
            extension = mPoint.nextToken();
        }

        StringBuilder sb = new StringBuilder(path.length());

        for (int i = 0; i < components.size() - 1; i++) {
            if (components.get(i).compareTo("/") == 0) {
                sb.append("/");
            } else {
                sb.append(components.get(i)).append("/");
            }
        }

        try {
            Director file = mTreeFile.findDirectorByName(fileName);
            // path invalid
            if (file == null) {
                returnStr = "Invalid : Calea trimisa nu este valida!";
                mSubject.setStateException(new InvalidPathExceptionParameter());
                throw new InvalidPathExceptionParameter();
            }
            // path invalid
            if (!(file instanceof File)) {
                returnStr = "Invalid : Calea trimisa nu este valida!";
                mSubject.setStateException(new InvalidPathExceptionParameter());
                throw new InvalidPathExceptionParameter();
            }

            if (sb.length() != 0) {
                this.cd(sb.toString());
            }

            // path invalid
            if (extension.compareTo("") != 0) {
                if (((File) file).getType().compareTo(extension) != 0) {
                    returnStr = "Invalid : Calea trimisa nu este valida!";
                    mSubject.setStateException(new InvalidPathExceptionParameter());
                    throw new InvalidPathExceptionParameter();
                }
            }

            if (file.getUserPermissions(currentUser).getReadPermission()) {
                returnStr = Arrays.toString(((File) file).getContent());
                mTreeFile.changeCurrentDirector(first);
            } else {
                mTreeFile.changeCurrentDirector(first);
                returnStr = "Invalid : Nu aveti permisiuni necesare";
                mSubject.setStateException(new DeniedPermissionException());
                throw new DeniedPermissionException();
            }
        } catch (InvalidPathExceptionParameter | DeniedPermissionException | InvalidChangeDirectoryToFileException e) {
            e.printStackTrace();
            returnStr = "Invalid : " + e.getMessage();
        }
    }

    // print working director
    public void pwd() throws Exception {
        Director currentDirector = mTreeFile.getCurrentDirector();

        String path = currentDirector.getPath();

        if (path.length() > 255) {
            returnStr = "Invalid : Calea depaseste 255 caractere";
            mSubject.setStateException(new FilePathTooLongException());
            throw new FilePathTooLongException();
        } else {
            returnStr = path;
        }
    }

    // make new director with a giver permission number
    public void mkdir(String name, int permNr) throws Exception {
        UserManagement.User currentUser = mUserManagement.id();
        UserManagement.User root = mUserManagement.findUser("root");
        UserManagement.User guest = mUserManagement.findUser("guest");
        Director currentDirector = mTreeFile.getCurrentDirector();
        int permNrParent = currentDirector.getPermNr();
        returnStr = "";

        if (currentDirector.getUserPermissions(currentUser).getWritePermission()) {
            ArrayList<Permission> perms = new ArrayList<>();
            perms.add(new Permission(true, true, root));
            perms.add(new Permission(false, false, guest));
            perms.add(new Permission(true, true, currentUser));
            boolean canRead, canWrite;

            if (permNrParent == 0) {
                permNr = 0;
            }
            if (permNrParent == 1) {
                if (permNr != 1 || permNr != 0) {
                    permNr = 1;
                }
            }
            if (permNrParent == 2) {
                if (permNr != 2 || permNr != 0) {
                    permNr = 2;
                }
            }

            switch (permNr) {
                case 0:
                    canRead = canWrite = false;
                    break;
                case 1:
                    canRead = false;
                    canWrite = true;
                    break;
                case 2:
                    canRead = true;
                    canWrite = false;
                    break;
                case 3:
                    canRead = canWrite = true;
                    break;
                default:
                    canRead = canWrite = true;
                    break;
            }

            // add permissions for other users to the new director
            Iterator<UserManagement.User> it = mUserManagement.iterator();
            while (it.hasNext()) {
                UserManagement.User itUser = it.next();
                if (!itUser.equals(root) && !itUser.equals(guest) && !itUser.equals(currentUser)) {
                    perms.add(new Permission(canRead, canWrite, itUser));
                }
            }
            Director dir = new Director(name, currentUser, perms, permNr);
            currentDirector.updateDimension(currentDirector.getDimension() + dir.getDimension());

            mTreeFile.addNode(dir, currentDirector);
        } else {
            returnStr = "Invalid : Nu aveti permisiuni necesare";
            mSubject.setStateException(new DeniedPermissionException());
            throw new DeniedPermissionException();
        }

        returnStr = "Success";
    }

    // make director with a given path and a permission number
    public void mkdir(String path, String dirName, int permNr) throws Exception {
        try {
            Director currentDirector = mTreeFile.getCurrentDirector();
            if (path.compareTo("") != 0 || path != null) {
                this.cd(path);
            }
            this.mkdir(dirName, permNr);
            mTreeFile.changeCurrentDirector(currentDirector);
        } catch (InvalidParameterException | InvalidPathExceptionParameter | DeniedPermissionException | InvalidChangeDirectoryToFileException e) {
            e.printStackTrace();
            returnStr = "Invalid : " + e.getMessage();
        }

        returnStr = "Success";
    }

    // create a new file with a permission number
    public void touch(String name, int permNr) throws Exception {
        UserManagement.User currentUser = mUserManagement.id();
        UserManagement.User root = mUserManagement.findUser("root");
        UserManagement.User guest = mUserManagement.findUser("guest");
        Director currentDirector = mTreeFile.getCurrentDirector();
        Node currentNode = mTreeFile.getNodes().get(currentDirector);
        returnStr = "";

        int permNrParent = currentDirector.getPermNr();

        if (currentDirector.getUserPermissions(currentUser).getWritePermission()) {
            ArrayList<Permission> perms = new ArrayList<>();
            perms.add(new Permission(true, true, root));
            perms.add(new Permission(false, false, guest));
            perms.add(new Permission(true, true, currentUser));
            boolean canRead, canWrite;

            if (permNrParent == 0) {
                permNr = 0;
            }
            if (permNrParent == 1) {
                if (permNr != 1 || permNr != 0) {
                    permNr = 1;
                }
            }
            if (permNrParent == 2) {
                if (permNr != 2 || permNr != 0) {
                    permNr = 2;
                }
            }

            switch (permNr) {
                case 0:
                    canRead = canWrite = false;
                    break;
                case 1:
                    canRead = false;
                    canWrite = true;
                    break;
                case 2:
                    canRead = true;
                    canWrite = false;
                    break;
                case 3:
                    canRead = canWrite = true;
                    break;
                default:
                    canRead = canWrite = true;
                    break;
            }

            // add permissions for other users to the new file
            Iterator<UserManagement.User> it = mUserManagement.iterator();
            while (it.hasNext()) {
                UserManagement.User itUser = it.next();
                if (!itUser.equals(root) && !itUser.equals(guest) && !itUser.equals(currentUser)) {
                    perms.add(new Permission(canRead, canWrite, itUser));
                }
            }

            String type = new Parameter(name).getFileType();
            String fileName = new Parameter(name).getFileName();

            File file = new File(fileName, type, currentUser, perms, permNr);
            currentDirector.updateDimension(currentDirector.getDimension() + file.getDimension());

            mTreeFile.addNode(file, currentDirector);

            currentDirector = currentNode.getParentDirector();
            currentNode = mTreeFile.getNodes().get(currentDirector);

            // update the dimension in all nodes from tree binded to that node
            while (currentDirector != null) {
                ArrayList<Director> childrenParent = currentNode.getChildren();
                double dim = 0;

                dim = childrenParent.stream().map((child) -> child.getDimension()).reduce(dim, (accumulator, _item) -> accumulator + _item);
                currentDirector.updateDimension(dim);

                currentDirector = currentNode.getParentDirector();
                currentNode = mTreeFile.getNodes().get(currentDirector);
            }
        } else {
            returnStr = "Invalid : Nu aveti permisiuni necesare";
            mSubject.setStateException(new DeniedPermissionException());
            throw new DeniedPermissionException();
        }

        returnStr = "Success";
    }

    // create a new file from a path and a permission number
    public void touch(String path, String fileName, int permNr) throws Exception {
        try {
            Director currentDirector = mTreeFile.getCurrentDirector();
            if (path.compareTo("") != 0 || path != null) {
                this.cd(path);
            }
            this.touch(fileName, permNr);
            mTreeFile.changeCurrentDirector(currentDirector);
        } catch (InvalidParameterException | InvalidPathExceptionParameter | DeniedPermissionException | InvalidChangeDirectoryToFileException e) {
            e.printStackTrace();
            returnStr = "Invalid : " + e.getMessage();
        }

        returnStr = "Success";
    }

    // remove a file 
    public void rm(String fileName, String param) throws Exception {
        UserManagement.User currentUser = mUserManagement.id();
        returnStr = "";

        Director currentDirector = mTreeFile.getCurrentDirector();
        Director currentFileDirector = null;
        ArrayList<Director> children = mTreeFile.getNodes().get(currentDirector).getChildren();

        for (Director child : children) {
            if (child.getName().compareTo(fileName) == 0) {
                currentFileDirector = child;
                break;
            }
        }

        if (currentFileDirector == null) {
            returnStr = "Invalid : Fisierul/Directorul introdus nu exista!";
            mSubject.setStateException(new InvalidFileNameException());
            throw new InvalidFileNameException();
        }

        if (param.compareTo("-r") == 0 && (currentFileDirector instanceof File || mTreeFile.getNodes().get(currentFileDirector).getChildren().isEmpty())) {
            returnStr = "Invalid : Nu puteti sterge recursiv un fisier sau director gol! Apelati comanda rm fara parametrul -r...";
            mSubject.setStateException(new RemoveRecursiveException());
            throw new RemoveRecursiveException();
        }
        if (param.compareTo("") == 0 && !mTreeFile.getNodes().get(currentFileDirector).getChildren().isEmpty()) {
            returnStr = "Invalid : Nu puteti sterge un director! Apelati comanda rm cu parametrul -r...";
            mSubject.setStateException(new RemoveRecursiveException());
            throw new RemoveRecursiveException("");
        }

        if (currentFileDirector.getUserPermissions(currentUser).getWritePermission()) {
            Director parent = mTreeFile.getNodes().get(currentFileDirector).getParentDirector();
            mTreeFile.delNode(currentFileDirector, parent);
            parent.updateDimension(parent.getDimension() - currentFileDirector.getDimension());
        } else {
            returnStr = "Invalid : Nu aveti permisiuni necesare";
            mSubject.setStateException(new DeniedPermissionException());
            throw new DeniedPermissionException();
        }

        returnStr = "Success";
    }

    // remove from a path
    public void rm(String path, String dirName, String param) throws Exception {
        try {
            Director currentDirector = mTreeFile.getCurrentDirector();
            if (path.compareTo("") != 0) {
                this.cd(path);
            }
            this.rm(dirName, param);
            mTreeFile.changeCurrentDirector(currentDirector);
        } catch (InvalidParameterException | InvalidPathExceptionParameter | DeniedPermissionException | InvalidChangeDirectoryToFileException e) {
            e.printStackTrace();
            returnStr = "Invalid : " + e.getMessage();
        }

        returnStr = "Success";
    }

    // print information about current user 
    public void userinfo() {
        UserManagement.User currentUser = mUserManagement.id();
        returnStr = currentUser.toString();
    }

    public void logout() throws Exception {
        User currentUser = mUserManagement.id();
        String username = currentUser.getUsername();
        User guest = mUserManagement.findUser("guest");

        if (currentUser.equals(guest)) {
            returnStr = "Invalid : Nu puteti sa deconectati userul guest (Vizitator)!";
            throw new GuestLogoutException();
        }

        mUserManagement.logout();
        returnStr = "Success : V-ati deconectat cu succes! Pentru relogare folositi comanda login...";

        mCalendar = Calendar.getInstance();
        mSubject.setStateUser("S-a delogat user-ul: " + username + " la data: " + mSimpleDateFormat.format(mCalendar.getTime()));
    }

    public void login(String username, String password) throws Exception {
        User currentUser = mUserManagement.id();
        User guest = mUserManagement.findUser("guest");

        if (currentUser.equals(guest)) {
            if (mUserManagement.findUser(username) == null) {
                returnStr = "Invalid : Username-ul introdus nu exista! Va rugam reincercati...";
            }
            if (mUserManagement.findUser(username).getPassword().compareTo(password) != 0) {
                returnStr = "Invalid : Parola introdusa este gresita! Va rugam reincercati...";
            }

            returnStr = "Success : Bine ati venit, " + mUserManagement.findUser(username).getFirstName() + " " + mUserManagement.findUser(username).getLastName();
            mUserManagement.login(mUserManagement.findUser(username));

            mCalendar = Calendar.getInstance();
            mSubject.setStateUser("S-a logat user-ul: " + username + " la data: " + mSimpleDateFormat.format(mCalendar.getTime()));
        } else {
            returnStr = "Invalid : Nu puteti sa va logati cu alt user inainte de a va deconecta de cel curent!";
            mSubject.setStateException(new LoginInsideOtherUserException());
            throw new LoginInsideOtherUserException();
        }
    }

    public void newuser(String username, String password, String lastName, String firstName) throws Exception {
        User currentUser = mUserManagement.id();
        User root = mUserManagement.findUser("root");

        if (currentUser.equals(root)) {
            UserManagement.User newUser = mUserManagement.new User(username, password, lastName, firstName);
            // add new user to UserManagement
            mUserManagement.newuser(newUser);

            Iterator<Node> depthIterator = mTreeFile.iterator(mTreeFile.getRootDirector());

            // iterate through tree for configuring the new user's permissions
            while (depthIterator.hasNext()) {
                Node node = depthIterator.next();
                boolean canRead, canWrite;

                int permNr = node.getCurrentDirector().getPermNr();

                switch (permNr) {
                    case 0:
                        canRead = canWrite = false;
                        break;
                    case 1:
                        canRead = false;
                        canWrite = true;
                        break;
                    case 2:
                        canRead = true;
                        canWrite = false;
                        break;
                    case 3:
                        canRead = canWrite = true;
                        break;
                    default:
                        canRead = canWrite = true;
                        break;
                }

                node.getCurrentDirector().addPermission(new Permission(canRead, canWrite, newUser));
            }

            returnStr = "Success : Felicitari! Contul a fost creat cu succes...";
        } else {
            returnStr = "Invalid : Nu puteti adauga/sterge useri decat cu contul de administrator (root)!";
            mSubject.setStateException(new RootOnlyPermissionException());
            throw new RootOnlyPermissionException();
        }
    }

    public void deluser(String username) throws Exception {
        User currentUser = mUserManagement.id();
        User root = mUserManagement.findUser("root");

        if (!currentUser.equals(root)) {
            returnStr = "Invalid : Nu puteti adauga/sterge useri decat cu contul de administrator (root)!";
            mSubject.setStateException(new RootOnlyPermissionException());
            throw new RootOnlyPermissionException();
        } else {
            User userToDelete = mUserManagement.findUser(username);

            if (userToDelete == null) {
                returnStr = "Invalid : Userul cu acest nume nu exista! Va rugam reincercati...";
                mSubject.setStateException(new UserNotFoundException());
                throw new UserNotFoundException();
            } else {
                mUserManagement.deluser(username);
            }
        }

        returnStr = "Success";
    }

    // Hello World!
    public void echo(String param) {
        returnStr = param;
    }
}
