package cloud.Users;

import java.util.*;

public class UserComparator implements Comparator {

    @Override
    public int compare(Object one, Object two) {
        UserManagement.User user1 = (UserManagement.User) one;
        UserManagement.User user2 = (UserManagement.User) two;
        
        return user1.getUsername().compareTo(user2.getUsername());
    }
}
