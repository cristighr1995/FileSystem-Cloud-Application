package cloud.ProgramFiles;

import cloud.Folders.Director;
import java.util.ArrayList;

public class Node implements INode {
    private Director director;
    private ArrayList<Director> children;
    private Director parent;

    // Constructor
    public Node(Director director) {
        this(director, null);
    }
    public Node(Director director, Director parent) {
        this.director = director;
        children = new ArrayList<>();
        this.parent = parent;
    }

    public Director getCurrentDirector() {
        return director;
    }
    public ArrayList<Director> getChildren() {
        return children;
    }
    public Director getParentDirector() {
        return this.parent;
    }

    public void addChild(Director director) {
        children.add(director);
    }
    public void removeChild(Director director) {
        children.remove(director);
    }
    public void changeParent(Director director) {
        this.parent = director;
    }
    
    public boolean equals(Object o) {
        if(!(o instanceof Node))
            return false;
        
        Node node = (Node) o;
        return this.director.equals(node.director) && this.parent.equals(node.parent) && this.children.equals(node.children);
    }
}
