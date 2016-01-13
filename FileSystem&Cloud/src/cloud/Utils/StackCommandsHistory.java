package cloud.Utils;

import java.util.ArrayList;

public class StackCommandsHistory<E> extends ArrayList<E> {
    private int index;
    
    public StackCommandsHistory() {
        index = 0;
    }
    
    public void setIndex(int ind) {
        index = ind;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void resetIndex() {
        if(super.isEmpty())
            index = 0;
        else
            index = super.size();
    }
    
    public String next() {
        if(index == 0) {
            if(super.isEmpty())
                return "";
            return (String) super.get(0);
        }
        index--;
        return (String) super.get(index);
    }
    
    public String back() {
        if(super.isEmpty())
            return "";
        
        if(index == super.size() - 1) {
            return (String) super.get(index);
        }
        index++;
        return (String) super.get(index);
    }
    
   public boolean add(E e) {
       index++;
       return super.add(e);
   }
}
