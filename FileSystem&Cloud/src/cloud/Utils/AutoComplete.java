package cloud.Utils;

import cloud.ProgramFiles.Node;
import cloud.ProgramFiles.TreeFileSystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class AutoComplete<E> extends ArrayList<E> {
    private final TreeFileSystem mTreeFile;
    private ArrayList<String> listCommands;
    private int index;
    
    public AutoComplete(TreeFileSystem tfs) {
        mTreeFile = tfs;
        addListCommands();
        index = 0;
    }
    
    private void addListCommands() {
        listCommands = new ArrayList<>();
        listCommands.add("ls");
        listCommands.add("cd");
        listCommands.add("cat");
        listCommands.add("pwd");
        listCommands.add("mkdir");
        listCommands.add("rm");
        listCommands.add("touch");
        listCommands.add("echo");
        listCommands.add("login");
        listCommands.add("logout");
        listCommands.add("newuser");
        listCommands.add("deluser");
        listCommands.add("userinfo");
        listCommands.add("upload");
        listCommands.add("sync");
        listCommands.add("listcloud");
    }
    
    public void updatePosibilities(String commandText) {
        super.clear();
        this.parseCommandText(commandText);
        index = super.size();
    }
    
    private void parseCommandText(String commandText) {
        String stringToComplete = "";
        int indexOfParam = 0;
        StringTokenizer st = new StringTokenizer(commandText);
        
        while(st.hasMoreTokens()) {
            stringToComplete = st.nextToken();
            indexOfParam++;
        }
        
        if(!stringToComplete.isEmpty()) {
            if(indexOfParam == 1) {
                for(String command : listCommands) {
                    if(command.startsWith(stringToComplete)) {
                        super.add((E) command);
                    }
                }
            }
            
            if(indexOfParam == 2) {
                if(stringToComplete.startsWith("-")) {
                    super.add((E) "-r");
                    super.add((E) "-a");
                    super.add((E) "-ar");
                }
                else {
                    if (stringToComplete.startsWith("/")) {
                        Iterator<Node> depthIterator = mTreeFile.iterator(mTreeFile.getRootDirector());
                        while (depthIterator.hasNext()) {
                            Node node = depthIterator.next();
                            if(node.getCurrentDirector().getPath().startsWith(stringToComplete))
                                super.add((E) node.getCurrentDirector().getPath());
                        }
                    }
                    else {
                        Iterator<Node> depthIterator = mTreeFile.iterator(mTreeFile.getRootDirector());
                        while (depthIterator.hasNext()) {
                            Node node = depthIterator.next();
                            if(node.getCurrentDirector().getPath().contains(stringToComplete))
                                super.add((E) node.getCurrentDirector().getPath());
                        }
                    }
                }
            }
            
            if(indexOfParam == 3) {
                if (stringToComplete.startsWith("/")) {
                        Iterator<Node> depthIterator = mTreeFile.iterator(mTreeFile.getRootDirector());
                        while (depthIterator.hasNext()) {
                            Node node = depthIterator.next();
                            if(node.getCurrentDirector().getPath().startsWith(stringToComplete))
                                super.add((E) node.getCurrentDirector().getPath());
                        }
                    }
                    else {
                        Iterator<Node> depthIterator = mTreeFile.iterator(mTreeFile.getRootDirector());
                        while (depthIterator.hasNext()) {
                            Node node = depthIterator.next();
                            if(node.getCurrentDirector().getPath().contains(stringToComplete))
                                super.add((E) node.getCurrentDirector().getPath());
                        }
                    }
            }
        }
    }
    
    public int getIndex() {
        return index;
    }
    
    public String next() {            
        if(index == super.size()) {
            index = 0;            
            return (String) super.get(index);
        }
        return (String) super.get(index++);
    }
}
