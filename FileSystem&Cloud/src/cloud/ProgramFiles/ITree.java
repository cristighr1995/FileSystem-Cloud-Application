package cloud.ProgramFiles;

import cloud.Folders.Director;

public interface ITree {
    
    void addNode(Director director);
    void addNode(Director director, Director parent);

    void display(Director root);

    void delNode(Director director, Director parent);
    
    Director findDirector(Director director);
}
