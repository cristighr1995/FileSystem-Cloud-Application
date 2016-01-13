package cloud.Utils;

import cloud.Exceptions.ForgetToPassPermissionNumberException;
import java.util.StringTokenizer;

public class Parameter {
    private final String command;
    
    public Parameter(String command) {
        this.command = command;
    }
    
    public int getParamsNumber() throws Exception {
        int count = 0;
        StringTokenizer st = new StringTokenizer(this.command);
        
        while(st.hasMoreTokens()) {
            count++;
            st.nextToken();
        }
        
        if(count == 3)
            return count;
        else
            throw new ForgetToPassPermissionNumberException();
    }
    
    public int getPermNr() {
        StringTokenizer st = new StringTokenizer(this.command);
        st.nextElement();
        
        return Integer.parseInt((String) st.nextElement());
    }
    
    public String getFDName() {
        StringTokenizer st = new StringTokenizer(this.command);
        st.nextElement();
        st.nextElement();
        
        return (String) st.nextElement();
    }
    
    public String getFileType() {
        if(!command.contains(".")) {
            return "binar";
        }
        
        StringTokenizer mStringTokenizer = new StringTokenizer(command, ".");
        mStringTokenizer.nextElement();
        
        return mStringTokenizer.nextToken();
    }
    
    public String getFileName() {
        if(!command.contains(".")) {
            return command;
        }
        
        StringTokenizer mStringTokenizer = new StringTokenizer(command, ".");
        return mStringTokenizer.nextToken();
    }
}
