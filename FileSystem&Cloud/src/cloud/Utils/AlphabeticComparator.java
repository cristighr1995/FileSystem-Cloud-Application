package cloud.Utils;

import java.util.Comparator;

public class AlphabeticComparator implements Comparator {

    @Override
    public int compare(Object t1, Object t2) {
        String s1 = (String) t1;
        String s2 = (String) t2;
        
        return s1.compareTo(s2);
    }
    
}
