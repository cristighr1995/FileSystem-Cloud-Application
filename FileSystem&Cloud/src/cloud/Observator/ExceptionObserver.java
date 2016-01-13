package cloud.Observator;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ExceptionObserver extends Observer {
    private static final Logger mLog = Logger.getLogger(ExceptionObserver.class.getName());

    public ExceptionObserver(Subject s) {
        subject = s;
        subject.attach(this);
        
        // Write directly into log file
        FileHandler fh;
        try {
            fh = new FileHandler("src\\cloud\\Logger\\loggerExceptions.log");
            mLog.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        mLog.log(Level.SEVERE, null, subject.getStateException());
    }
}
