package cloud.Observator;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UsersObserver extends Observer {
    private static final Logger mLog = Logger.getLogger(UsersObserver.class.getName());

    public UsersObserver(Subject s) {
        subject = s;
        subject.attach(this);
        
        // Write directly into log file
        FileHandler fh;
        try {
            fh = new FileHandler("src\\cloud\\Logger\\loggerUsersTimeTracking.log");
            mLog.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        mLog.log(Level.SEVERE, subject.getStateUser());
    }
}
