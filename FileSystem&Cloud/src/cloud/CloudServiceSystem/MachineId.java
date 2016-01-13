package cloud.CloudServiceSystem;

import cloud.Folders.Director;
import cloud.ProgramFiles.Node;

public class MachineId extends Node {
    
    private int id;
    
    public MachineId(Director director) {
        super(director);
    }

    public MachineId(Director director, Director parent) {
        super(director, parent);
    }

    public int getMId() {
        return id;
    }
    
    public void setMId(int id) {
        this.id = id;
    }
}
