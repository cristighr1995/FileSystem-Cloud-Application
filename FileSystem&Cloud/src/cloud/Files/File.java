package cloud.Files;

import cloud.Folders.Director;
import cloud.Permissions.Permission;
import cloud.Utils.RandomGenerator;
import cloud.Users.UserManagement;
import java.util.ArrayList;

public class File extends Director implements IFile {
    
    private String type;
    private byte[] content;
    
    // Constructors
    public File(String name, String type, UserManagement.User user, ArrayList<Permission> perms, int permNr) {
        super(name, user, perms, permNr);
        this.type = type;
        int length = RandomGenerator.randomInt(10, 50);
        content = new byte[length];
        content = RandomGenerator.randomBytes(content);
        this.dimension = content.length;
    }
    
    public File(String n, double dim, UserManagement.User o, ArrayList<Permission> perms, String tc, int id, String path, int permNr, String type, byte[] content) {
        super(n, dim, o, perms, tc, id, path, permNr);
        this.type = type;
        this.content = content;
    }
    
    @SuppressWarnings("ImplicitArrayToString")
    public String toString() {
        return super.toString() + "--" + type + "--" + content;
    }
    
    public String getType() {
        return this.type;
    }
    public byte[] getContent() {
        return this.content;
    }
    
    public void changeType(String type) {
        this.type = type;
    }
    public void changeContent(byte[] content) {
        this.content = content;
    }
}
