package cloud.ProgramFiles;

import cloud.Folders.Director;
import java.util.*;

public class DepthTreeIterator implements Iterator<Node>{
    private final LinkedList<Node> list;

    public DepthTreeIterator(HashMap<Director, Node> tree, Director dir) {
        list = new LinkedList<>();

        if (tree.containsKey(dir)) {
            this.buildList(tree, dir);
        }
    }

    private void buildList(HashMap<Director, Node> tree, Director dir) {
        list.add(tree.get(dir));
        
        ArrayList<Director> children = tree.get(dir).getChildren();
        
        children.stream().forEach((child) -> {
            this.buildList(tree, child);
        });
    }

    public boolean hasNext() {
        return !list.isEmpty();
    }

    public Node next() {
        return list.poll();
    }
}
