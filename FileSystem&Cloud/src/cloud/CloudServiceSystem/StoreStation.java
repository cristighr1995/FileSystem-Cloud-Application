package cloud.CloudServiceSystem;

import java.util.TreeSet;

public abstract class StoreStation {
    
    public MachineId machineId;
    
    public abstract void store(TreeSet<MachineId> nodes);    
    
    public abstract TreeSet<MachineId> getBase(String dName);
    
    public abstract MachineId find(String fdName);
}
