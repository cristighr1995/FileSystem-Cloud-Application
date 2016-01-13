package cloud.Utils;

import cloud.CloudServiceSystem.MachineId;
import cloud.Folders.Director;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class AscendingTimeComparator implements Comparator {

    @Override
    public int compare(Object t1, Object t2) {
        MachineId m1 = (MachineId) t1;
        MachineId m2 = (MachineId) t2;
        Director d1 = m1.getCurrentDirector();
        Director d2 = m2.getCurrentDirector();
        String tc1 = d1.getTimeCreated();
        String tc2 = d2.getTimeCreated();
        
        DateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date1 = mSimpleDateFormat.parse(tc1);
            Date date2 = mSimpleDateFormat.parse(tc2);
            
            if(date1.after(date2))
                return 1;
            if(date2.after(date1))
                return -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}
