package cloud.Permissions;

import cloud.Users.UserManagement;

public class Permission<T> implements IPermission<T> {
    private boolean canRead;
    private boolean canWrite;
    private final T user;
    
    public Permission(boolean read, boolean write, T user) {
        this.canRead = read;
        this.canWrite = write;
        this.user = user;
    }
    
    public boolean getReadPermission() {
        return this.canRead;
    }
    public boolean getWritePermission() {
        return this.canWrite;
    }
    public T getUser() {
        return this.user;
    }
    
    public void changeReadPermission(boolean read) {
        this.canRead = read;
    }
    public void changeWritePermission(boolean write) {
        this.canWrite = write;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Permission))
            return false;
        
        Permission perm = (Permission) o;
        return this.canRead == perm.getReadPermission() && this.canWrite == perm.getWritePermission() &&
                this.user.equals(perm.getUser());
    }
    
    public String toString() {
        UserManagement.User thisUser = (UserManagement.User) user;
        return thisUser.getUsername() + "--" + canRead + "--" + canWrite;
    }
}
