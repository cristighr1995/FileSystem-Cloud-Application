package cloud.ProgramFiles;

import cloud.Files.File;
import cloud.Folders.Director;
import java.util.*;

public class TreeFileSystem implements ITree {
    private final static int ROOT = 0;
    // Tree nodes
    private final HashMap<Director, Node> nodes;
    private int currentId = 0;
    private final TreeSet<Integer> idsToRemove;
    private Director currentDirector;

    // Constructors
    public TreeFileSystem() {
        this.nodes = new HashMap<>();
        this.idsToRemove = new TreeSet<>();
    }

    public HashMap<Director, Node> getNodes() {
        return nodes;
    }

    // Add the root in HashMap
    public void addNode(Director director) {
        this.currentDirector = director;
        this.addNode(director, null);
    }
    public void addNode(Director director, Director parent) {
        // Create and add a new node 
        director.setId(currentId++);
        Node node;
        
        if(parent != null) {
            // When added a new node, the path is formed by the path of its parent and the name of the current file/directory
            if(director instanceof Director)
                director.changePath(parent.getPath() + director.getName() + "/");
            if(director instanceof File)
                director.changePath(parent.getPath() + director.getName());
            node = new Node(director, parent);
        }
        else {
            director.changePath("/");
            node = new Node(director);
        }
        
        nodes.put(director, node);

        // Verify if the tree is null or the parent isn't a File
        // If isn't null, my directory is inside another Director
        // Then update his parent
        if (parent != null && !(parent instanceof File)) {
            nodes.get(parent).addChild(director);
        }
    }

    public void display(Director root) {
        this.display(root, ROOT);
    }
    private void display(Director director, int TABS) {
        ArrayList<Director> children = nodes.get(director).getChildren();

        if (TABS == ROOT) 
            System.out.println(nodes.get(director).getCurrentDirector().getId() +
                    nodes.get(director).getCurrentDirector().getName() + "  " +
                    nodes.get(director).getCurrentDirector().getPath());
        else {
            // Print all children
            String tabs = String.format("%0" + TABS + "d", 0).replace("0", "    "); // 4 spaces
            System.out.println(tabs + nodes.get(director).getCurrentDirector().getId() +
                    nodes.get(director).getCurrentDirector().getName() + "  " +
                    nodes.get(director).getCurrentDirector().getPath());
        }
        
        // Grow indentation tabs
        TABS++;
        
        for (Director dir : children) 
            this.display(dir, TABS);
    }
    
    private void findIdsToRemove(Director director, Director parent) {
        ArrayList<Director> childrenDirector = nodes.get(director).getChildren();
        
        // idsToRemove contains all ids of directories or files that need to be deleted
        idsToRemove.add(director.getId());
        
        // Recursive call
        childrenDirector.stream().forEach((dir) -> {
            this.findIdsToRemove(dir, director);
        });
    }
    public void delNode(Director director, Director parent) {
        // Find all ids of files/directories that need to delete
        this.findIdsToRemove(director, parent);

        // Delete those nodes
        idsToRemove.stream().forEach((i) -> {
            Iterator nodesIterator = nodes.entrySet().iterator();
            while (nodesIterator.hasNext()) {
                Map.Entry<Director, Node> pair = (Map.Entry<Director, Node>)nodesIterator.next();
                Director dir = pair.getKey();
                
                if(dir.getId() == i)
                    nodesIterator.remove(); 
            }
        });
        
        // Clear node from his parent
        ArrayList<Director> childrenParent = nodes.get(parent).getChildren();
        childrenParent.remove(director);
        
        idsToRemove.clear();
    }
    
    public Director findDirector(Director director) {
        Iterator nodesIterator = nodes.entrySet().iterator();
        
        while (nodesIterator.hasNext()) {
            Map.Entry<Director, Node> pair = (Map.Entry<Director, Node>)nodesIterator.next();
            Director itDirector = pair.getKey();
            
            if(itDirector.equals(director))
                return itDirector;
        }
        
        return null;
    }
    
    public Director findDirectorByPath(String path) {
        Iterator nodesIterator = nodes.entrySet().iterator();
        
        while (nodesIterator.hasNext()) {
            Map.Entry<Director, Node> pair = (Map.Entry<Director, Node>)nodesIterator.next();
            Director itDirector = pair.getKey();
            
            if(itDirector.getPath().compareTo(path) == 0 || itDirector.getPath().contains(path))
                return itDirector;
        }
        
        return null;
    }
    
    public Director findDirectorByAbsolutePath(String path) {
        Iterator nodesIterator = nodes.entrySet().iterator();
        
        while (nodesIterator.hasNext()) {
            Map.Entry<Director, Node> pair = (Map.Entry<Director, Node>)nodesIterator.next();
            Director itDirector = pair.getKey();
            
            if(itDirector.getPath().compareTo(path) == 0)
                return itDirector;
        }
        
        return null;
    }
    
    public Director findDirectorByName(String name) {
        Iterator nodesIterator = nodes.entrySet().iterator();
        
        while (nodesIterator.hasNext()) {
            Map.Entry<Director, Node> pair = (Map.Entry<Director, Node>)nodesIterator.next();
            Director itDirector = pair.getKey();
            
            if(itDirector.getName().compareTo(name) == 0)
                return itDirector;
        }
        
        return null;
    }
    
    public Director getRootDirector() {
        Iterator nodesIterator = nodes.entrySet().iterator();
        
        while (nodesIterator.hasNext()) {
            Map.Entry<Director, Node> pair = (Map.Entry<Director, Node>)nodesIterator.next();
            Director itDirector = pair.getKey();
            
            if(itDirector.getPath().compareTo("/") == 0)
                return itDirector;
        }
        return null;
    }
    
    public Director getCurrentDirector() {
        return this.currentDirector;
    }
    public void changeCurrentDirector(Director dir) {
        this.currentDirector = dir;
    }

    public Iterator<Node> iterator(Director dir) {
        return this.iterator(dir, TraversalStrategy.DEPTH_FIRST);
    }

    public Iterator<Node> iterator(Director dir, TraversalStrategy traversalStrategy) {
        return new DepthTreeIterator(nodes, dir);
    }
    
}
