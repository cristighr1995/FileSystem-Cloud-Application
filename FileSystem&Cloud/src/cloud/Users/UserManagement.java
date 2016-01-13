package cloud.Users;

import java.text.*;
import java.util.*;

public class UserManagement<User> extends TreeSet<UserManagement.User> implements IUserManagement<UserManagement.User>, IUserAdministration<UserManagement.User>{
    public class User implements Comparable {
        private String username;
        private String password;
        private String lastName;  
        private String firstName; 
        private String timeCreated;
        private String timeLastLogin;

        // Constructor
        public User(String username, String password, String lastName, String firstName) {
            this.username = username;
            this.password = password;
            this.lastName = lastName;
            this.firstName = firstName;
            this.timeLastLogin = "never";
        }
        
        public User(String username, String password, String lastName, String firstName, String timeCreated, String timeLastLogin) {
            this(username, password, lastName, firstName);
            this.timeCreated = timeCreated;
            this.timeLastLogin = timeLastLogin;
        }
        
        // Get
        public String getUsername() {
            return this.username;
        }
        public String getPassword() {
            return this.password;
        }
        public String getLastName() {
            return this.lastName;
        }
        public String getFirstName() {
            return this.firstName;
        }
        public String getTimeCreated() {
            return this.timeCreated;
        }
        public String getTimeLastLogin() {
            return this.timeLastLogin;
        }
        
        // Set
        public void setUsername(String newUsername) {
            this.username = newUsername;
        }
        public void setPassword(String newPassword) {
            this.password = newPassword;
        }
        public void setLastName(String newLastName) {
            this.lastName = newLastName;
        }
        public void setFirstName(String newFirstName) {
            this.firstName = newFirstName;
        }
        public void setTimeCreated(String newTimeCreated) {
            this.timeCreated = newTimeCreated;
        }
        public void setTimeLastLogin(String newTimeLastLogin) {
            this.timeLastLogin = newTimeLastLogin;
        }

        @Override
        public int compareTo(Object u) {            
            User user = (User) u;
            return this.getUsername().compareTo(user.getUsername());
        }
        
        @Override
        public String toString() {
            return username + "--" + password + "--" + lastName + "--" + firstName +
                    "--" + timeCreated + "--" + timeLastLogin;
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof UserManagement.User))
                return false;
            
            User user = (User) o;
            return this.firstName.compareTo(user.getFirstName()) == 0 && this.lastName.compareTo(user.getLastName()) == 0 &&
                    this.password.compareTo(user.getPassword()) == 0 && this.timeCreated.compareTo(user.getTimeCreated()) == 0 &&
                    this.timeLastLogin.compareTo(user.getTimeLastLogin()) == 0 && this.username.compareTo(user.getUsername()) == 0;
        }
    }
    
    // Singleton Pattern
    private static UserManagement instance = new UserManagement(new UserComparator());
    
    private User currentUser;
    private Calendar mCalendar;
    private final SimpleDateFormat mSimpleDateFormat;
    
    private UserManagement(Comparator cmp) {
        super(cmp);
        mCalendar = Calendar.getInstance();
        mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
        // Predefined users of the system
        User root = new User("root", "root", "root", "root");
        User guest = new User("guest", "guest", "guest", "guest");
        this.newuser(root);
        this.newuser(guest);
    }
    
    public User findUser(String username) {
        for(User u : this) 
            if(u.getUsername().compareTo(username) == 0)
                return u;
        return null;
    }
    
    public boolean deluser(String username) {
        if(findUser(username) == null)
            return true;
        return super.remove(findUser(username));
    }
    
    public User userinfo() {
        return this.currentUser;
    }
    
    public void logout() {
        this.currentUser = findUser("guest");
    }
    
    public int getUsersNumber() {
        return super.size();
    }
    
    public boolean newuser(User newUser) {
        mCalendar = Calendar.getInstance();
        newUser.timeCreated = mSimpleDateFormat.format(mCalendar.getTime());
        return super.add(newUser);
    }

    public void login(User user) {
        mCalendar = Calendar.getInstance();
        user.timeLastLogin = mSimpleDateFormat.format(mCalendar.getTime());
        this.currentUser = user;
    }
    
    // getCurrentUser
    public User id() {
        return this.currentUser;
    }
    
    public User getGuestId() {
        return this.findUser("guest");
    }
    
    public User getRootId() {
        return this.findUser("root");
    }
    
    public static UserManagement getInstance() {
        return instance;
    }
}
