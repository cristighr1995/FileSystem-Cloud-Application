package cloud.ProgramFiles;

import cloud.Folders.Director;
import java.util.ArrayList;

public interface INode {
    Director getCurrentDirector();

    ArrayList<Director> getChildren();

    void addChild(Director director);
}
