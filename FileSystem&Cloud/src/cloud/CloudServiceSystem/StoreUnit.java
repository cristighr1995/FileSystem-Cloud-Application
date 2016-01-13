package cloud.CloudServiceSystem;

import java.util.*;

public class StoreUnit extends StoreStation {
    private final HashSet<TreeSet<MachineId>> tfs;
    private final double totalDimension;
    private double occupiedDimension;
    private final int id;

    public StoreUnit(int id) {
        this.tfs = new HashSet<>();
        this.totalDimension = 100;
        this.occupiedDimension = 0;
        this.id = id;
    }
    
    public void store(TreeSet<MachineId> nodes) {
        if(!nodes.isEmpty() || nodes != null)
            this.tfs.add(nodes);
    }
    
    public TreeSet<MachineId> getBase(String dName) {
        Iterator itTFS = tfs.iterator();
        while(itTFS.hasNext()) {
            TreeSet<MachineId> inside = (TreeSet<MachineId>) itTFS.next();
            Iterator itInside = inside.iterator();
            MachineId first = (MachineId) itInside.next();
            
            if(first.getCurrentDirector().getName().compareTo(dName) == 0)
                return inside;
        }
        return null;
    }
    
    public MachineId find(String fdName) {
        Iterator itTFS = tfs.iterator();
        while(itTFS.hasNext()) {
            TreeSet<MachineId> inside = (TreeSet<MachineId>) itTFS.next();
            Iterator itInside = inside.iterator();
            while(itInside.hasNext()) {
                MachineId mMachineId = (MachineId) itInside.next();
                if(mMachineId.getCurrentDirector().getName().compareTo(fdName) == 0)
                    return mMachineId;
            }
        }
        return null;
    }
    
    public String listStoreUnitContent() {
        StringBuilder sb = new StringBuilder();
        Iterator itHS = tfs.iterator();
        while (itHS.hasNext()) {
            TreeSet<MachineId> ts = (TreeSet<MachineId>) itHS.next();
            Iterator itTS = ts.iterator();
            while (itTS.hasNext()) {
                MachineId mMachineId = (MachineId) itTS.next();
                sb.append(mMachineId.getCurrentDirector().getName()).append("--").append(mMachineId.getCurrentDirector().getPath()).append("\n");
            }
        }
        sb.append("-------\n" + "Dimensiunea statiei ").append(this.id).append(" este : ").append(occupiedDimension).append("\n-------\n");
        
        return sb.toString();
    }
    
    public double getTotalDimension() {
        return this.totalDimension;
    }

    public double getOccupiedDimension() {
        return this.occupiedDimension;
    }
    
    public void updateOccupiedDimension(double dim) {
        this.occupiedDimension = dim;
    }
}
