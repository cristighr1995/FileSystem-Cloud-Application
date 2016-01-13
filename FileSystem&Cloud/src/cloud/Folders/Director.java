package cloud.Folders;

import cloud.Permissions.Permission;
import cloud.Users.UserManagement;
import java.text.*;
import java.util.*;

@SuppressWarnings("EqualsAndHashcode")
public class Director implements IDirector {
    protected String name;
    protected double dimension;
    protected UserManagement.User owner;
    protected ArrayList<Permission> perms;
    protected String timeCreated;
    protected Calendar mCalendar;
    protected SimpleDateFormat mSimpleDateFormat;
    protected int identifier = 0;
    protected String path;
    protected int permNr;
    
    // Constructors
    public Director(String name, UserManagement.User user, ArrayList<Permission> permissions, int permNr) {
        this.name = name;
        this.dimension = 0;
        mCalendar = Calendar.getInstance();
        mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.timeCreated = mSimpleDateFormat.format(mCalendar.getTime());
        this.owner = user;
        this.perms = permissions;
        this.permNr = permNr;
    }
    
    public Director(String n, double dim, UserManagement.User o, ArrayList<Permission> perms, String tc, int id, String path, int permNr) {
        this.name = n;
        this.dimension = dim;
        this.owner = o;
        this.perms = perms;
        this.timeCreated = tc;
        this.identifier = id;
        this.path = path;
        this.permNr = permNr;
    }
    
    public String toString() {
        return name + "--" + dimension + "--" + owner.getUsername() + "--" + timeCreated + "--" + 
               identifier + "--" + path + "--" + permNr;
    }

    public String getName() {
        return this.name;
    }   
    public double getDimension() {
        return this.dimension;
    }
    public UserManagement.User getOwner() {
        return this.owner;
    }   
    public ArrayList<Permission> getPermissions() {
        return this.perms;
    }
    public Permission getUserPermissions(UserManagement.User user) {
        for(Permission p : perms) {
            if(((UserManagement.User)(p.getUser())).equals(user))
                return p;
        }
        return null;
    }
    public String getTimeCreated() {
        return this.timeCreated;
    }    
    public int getId() {
        return this.identifier;
    }
    public String getPath() {
        return this.path;
    }
    public int getPermNr() {
        return this.permNr;
    }
    
    public void changeName(String name) {
        this.name = name;
    }
    public void updateDimension(double dimension) {
        this.dimension = dimension;
    }
    public void changeOwner(UserManagement.User user) {
        this.owner = user;
    }
    public void addPermission(Permission permission) {
        perms.add(permission);
    }
    public void setId(int id) {
        this.identifier = id;
    }
    public void changePath(String path) {
        this.path = path;
    }
    public void resetDimension() {
        this.dimension = 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Director))
            return false;
        
        Director dir = (Director) o;
        return this.name.compareTo(dir.getName()) == 0 && this.dimension == dir.getDimension() && this.owner.equals(dir.getOwner()) &&
                this.perms.equals(dir.getPermissions()) && this.timeCreated.compareTo(dir.getTimeCreated()) == 0 && this.identifier == dir.getId();
    }
}
