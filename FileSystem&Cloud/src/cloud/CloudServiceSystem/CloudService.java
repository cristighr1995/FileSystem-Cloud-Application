package cloud.CloudServiceSystem;

import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Exceptions.CloudSpaceFullException;
import cloud.Exceptions.InexistentFileCloudException;
import cloud.Exceptions.InvalidFileNameException;
import cloud.Files.File;
import cloud.Folders.Director;
import cloud.Observator.Subject;
import cloud.ProgramFiles.Node;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Utils.AscendingTimeComparator;
import java.util.*;

public class CloudService {
    private final TreeFileSystem mTreeFileSystem;
    private final ArrayList<StoreUnit> mStoreUnits;
    private final Subject mSubject = CommandConfiguration.getSubject();
    
    public CloudService(TreeFileSystem tfs) {
        this.mTreeFileSystem = tfs;
        this.mStoreUnits = new ArrayList<>();
        StoreUnit su1 = new StoreUnit(1);
        StoreUnit su2 = new StoreUnit(2);
        StoreUnit su3 = new StoreUnit(3);
        this.mStoreUnits.add(su1);
        this.mStoreUnits.add(su2);
        this.mStoreUnits.add(su3);
    }
    
    private Director findParentInTree(TreeSet<MachineId> ts, Director dir) {
        Iterator itTS = ts.iterator();
        while(itTS.hasNext()) {
            MachineId mMachineId = (MachineId) itTS.next();
            Director mDirector = mMachineId.getCurrentDirector();
            
            if(dir.equals(mDirector)) {
                return mMachineId.getParentDirector();
            }
            
            for(int i = 0; i < mMachineId.getChildren().size(); i++) {
                if(mMachineId.getChildren().get(i).getName().compareTo(dir.getName()) == 0)
                    return mDirector;
            }
        }
        return null;
    }
    
    public void upload(Director dir) throws Exception {
        Node node = mTreeFileSystem.getNodes().get(dir);
        
        // file/director doesn't exist
        if(node == null) {
            CommandConfiguration.setReturnString("Invalid : Fisierul/Directorul introdus nu exista!");
            mSubject.setStateException(new InvalidFileNameException());
            throw new InvalidFileNameException();
        }
        
        Director parentInTree = node.getParentDirector();
        // clone the parent to the cloud
        Director mParent = new Director(parentInTree.getName(), parentInTree.getDimension(), parentInTree.getOwner(), 
                                                parentInTree.getPermissions(), parentInTree.getTimeCreated(),
                                                parentInTree.getId(), parentInTree.getPath(), parentInTree.getPermNr());
        
        // save in miniTrees a part (or all) directories/files from uploaded director
        TreeSet<MachineId> miniTreeSU_1 = new TreeSet<>(new AscendingTimeComparator());
        TreeSet<MachineId> miniTreeSU_2 = new TreeSet<>(new AscendingTimeComparator());
        TreeSet<MachineId> miniTreeSU_3 = new TreeSet<>(new AscendingTimeComparator());
        
        double occupiedSU_1 = this.mStoreUnits.get(0).getOccupiedDimension();
        double totalSU_1 = this.mStoreUnits.get(0).getTotalDimension();
        double occupiedSU_2 = this.mStoreUnits.get(1).getOccupiedDimension();
        double totalSU_2 = this.mStoreUnits.get(1).getTotalDimension();
        double occupiedSU_3 = this.mStoreUnits.get(2).getOccupiedDimension();
        double totalSU_3 = this.mStoreUnits.get(2).getTotalDimension();
        
        double occupiedTotalSU_123 = occupiedSU_1 + occupiedSU_2 + occupiedSU_3;
        double totalTotalSU_123 = totalSU_1 + totalSU_2 + totalSU_3;
        
        // if the cloud hasn't got that space to upload the director
        if(dir.getDimension() + occupiedTotalSU_123 >= totalTotalSU_123) {
            CommandConfiguration.setReturnString("Invalid : Spatiul din cloud este plin!");
            mSubject.setStateException(new CloudSpaceFullException());
            throw new CloudSpaceFullException();
        }
        
        // clone the uploaded director
        Director mDirector = new Director(dir.getName(), 0, dir.getOwner(), 
                                                dir.getPermissions(), dir.getTimeCreated(),
                                                dir.getId(), dir.getPath(), dir.getPermNr());
        
        MachineId mMachineId = new MachineId(mDirector);
        mMachineId.changeParent(mParent);
        
        // add its children (a reference to them)
        node.getChildren().stream().forEach((child) -> {
            if(child instanceof Director && !(child instanceof File)) {
                Director newChild = new Director(child.getName(), child.getDimension(), child.getOwner(),
                        child.getPermissions(), child.getTimeCreated(), child.getId(), child.getPath(), child.getPermNr());
                mMachineId.addChild(newChild);
            }
            else {
                File iFile = (File) child;
                File newChild = new File(iFile.getName(), iFile.getDimension(), iFile.getOwner(),
                        iFile.getPermissions(), iFile.getTimeCreated(), iFile.getId(), iFile.getPath(), iFile.getPermNr(),
                        iFile.getType(), iFile.getContent());
                mMachineId.addChild(newChild);
            }
        });
        
        mMachineId.setMId(0);
        // add the head director (the director wanted to be uploaded)
        miniTreeSU_1.add(mMachineId);
        
        // iterate from it to all of its children
        // and clone them each one by one if the cloud has that space
        Iterator<Node> depthIterator = mTreeFileSystem.iterator(dir);
        while (depthIterator.hasNext()) {
            Node itNode = depthIterator.next();
            Director itDir = itNode.getCurrentDirector();
            
            if(itDir instanceof Director && !(itDir instanceof File)) {
                Director newDirector = new Director(itDir.getName(), 0, itDir.getOwner(), 
                                                itDir.getPermissions(), itDir.getTimeCreated(),
                                                itDir.getId(), itDir.getPath(), dir.getPermNr());
                MachineId newMachineId = new MachineId(newDirector, this.findParentInTree(miniTreeSU_1, newDirector));
                
                // reference to the children 
                itNode.getChildren().stream().forEach((child) -> {
                    if(child instanceof Director && !(child instanceof File)) {
                        Director newChild = new Director(child.getName(), child.getDimension(), child.getOwner(),
                                child.getPermissions(), child.getTimeCreated(), child.getId(), child.getPath(), child.getPermNr());
                        newMachineId.addChild(newChild);
                    }
                    else {
                        File iFile = (File) child;
                        File newChild = new File(iFile.getName(), iFile.getDimension(), iFile.getOwner(),
                                iFile.getPermissions(), iFile.getTimeCreated(), iFile.getId(), iFile.getPath(), iFile.getPermNr(),
                                iFile.getType(), iFile.getContent());
                        newMachineId.addChild(newChild);
                    }
                });
                
                // add all the directories to the first station because they have dimension 0!
                newMachineId.setMId(0);
                newMachineId.changeParent(this.findParentInTree(miniTreeSU_1, newDirector));
                miniTreeSU_1.add(newMachineId);
                continue;
            }
            
            // it's time to add the files (file's dimension matters only)
            // if there is space to the first station 
            if(itDir.getDimension() + occupiedSU_1 <= totalSU_1) {
                File itFile = (File) itDir;
                File newFile = new File(itFile.getName(), itFile.getDimension(), itFile.getOwner(),
                                        itFile.getPermissions(), itFile.getTimeCreated(), itFile.getId(),
                                        itFile.getPath(), itFile.getPermNr(), itFile.getType(), itFile.getContent());
                
                MachineId newMachineId = new MachineId(newFile, this.findParentInTree(miniTreeSU_1, newFile));
                newMachineId.changeParent(this.findParentInTree(miniTreeSU_1, newFile));
                
                newMachineId.setMId(0);
                miniTreeSU_1.add(newMachineId);
                occupiedSU_1 += itDir.getDimension();
                continue;
            }
            
            // if there is space to the second station
            if(itDir.getDimension() + occupiedSU_2 <= totalSU_2) {
                File itFile = (File) itDir;
                File newFile = new File(itFile.getName(), itFile.getDimension(), itFile.getOwner(),
                                        itFile.getPermissions(), itFile.getTimeCreated(), itFile.getId(),
                                        itFile.getPath(), itFile.getPermNr(), itFile.getType(), itFile.getContent());
                
                MachineId newMachineId = new MachineId(newFile, this.findParentInTree(miniTreeSU_1, newFile));
                newMachineId.changeParent(this.findParentInTree(miniTreeSU_1, newFile));
                
                newMachineId.setMId(1);
                miniTreeSU_2.add(newMachineId);
                occupiedSU_2 += itDir.getDimension();
                continue;
            }
            
            // if there is space to the third station
            if(itDir.getDimension() + occupiedSU_3 <= totalSU_3) {
                File itFile = (File) itDir;
                File newFile = new File(itFile.getName(), itFile.getDimension(), itFile.getOwner(),
                                        itFile.getPermissions(), itFile.getTimeCreated(), itFile.getId(),
                                        itFile.getPath(), itFile.getPermNr(), itFile.getType(), itFile.getContent());
                
                MachineId newMachineId = new MachineId(newFile, this.findParentInTree(miniTreeSU_1, newFile));
                newMachineId.changeParent(this.findParentInTree(miniTreeSU_1, newFile));
                
                newMachineId.setMId(2);
                miniTreeSU_3.add(newMachineId);
                occupiedSU_3 += itDir.getDimension();
            }
        }
        
        // store the files/directories into cloud
        this.mStoreUnits.get(0).store(miniTreeSU_1);
        this.mStoreUnits.get(1).store(miniTreeSU_2);
        this.mStoreUnits.get(2).store(miniTreeSU_3);
        
        this.mStoreUnits.get(0).updateOccupiedDimension(occupiedSU_1);
        this.mStoreUnits.get(1).updateOccupiedDimension(occupiedSU_2);
        this.mStoreUnits.get(2).updateOccupiedDimension(occupiedSU_3);
        CommandConfiguration.setReturnString("Success");
    }
    
    public void upload(String dName) throws Exception {
        Director dir = mTreeFileSystem.findDirectorByName(dName);
        this.upload(dir);
    }
    
    public void sync(String fdName) throws Exception {  
        TreeSet<MachineId> finalTree = new TreeSet<>(new AscendingTimeComparator());
        // get directories from the first station
        // all directories are there!
        TreeSet<MachineId> base = this.mStoreUnits.get(0).getBase(fdName);
        
        if(base == null) {
            CommandConfiguration.setReturnString("Invalid : Fisierul nu exista in cloud!");
            mSubject.setStateException(new InexistentFileCloudException());
            throw new InexistentFileCloudException();
        }
        
        // remove the existing director from the system 
        Node nodeToDelete = this.mTreeFileSystem.getNodes().get(this.mTreeFileSystem.findDirectorByName(fdName));
        if(nodeToDelete != null) {
            nodeToDelete.getParentDirector().updateDimension(nodeToDelete.getParentDirector().getDimension() - nodeToDelete.getCurrentDirector().getDimension());
            this.mTreeFileSystem.delNode(nodeToDelete.getCurrentDirector(), nodeToDelete.getParentDirector());
        }

        // iterate the base to reach their children files
        // and reconstruct the initial director
        Iterator itBase = base.iterator();
        
        while(itBase.hasNext()) {
            MachineId bMId = (MachineId) itBase.next();
            finalTree.add(bMId);
            ArrayList<Director> children = bMId.getChildren();
            
            children.stream().map((child) -> {
                MachineId mChild = this.mStoreUnits.get(0).find(child.getName());
                if(mChild == null) {
                    mChild = this.mStoreUnits.get(1).find(child.getName());
                    
                    if(mChild == null)
                        mChild = this.mStoreUnits.get(2).find(child.getName());
                }
                return mChild;                
            }).forEach((mChild) -> {
                finalTree.add(mChild);
            });
        }
        
        // add the director from cloud into the system
        Iterator itFinalTree = finalTree.iterator();
        while(itFinalTree.hasNext()) {
            MachineId currentNode = (MachineId) itFinalTree.next();
            ArrayList<Director> children = currentNode.getChildren();
            
            double reverseDimension = 0;
            
            reverseDimension = children.stream().map((child) -> child.getDimension()).reduce(reverseDimension, (accumulator, _item) -> accumulator + _item);
            
            if(currentNode.getCurrentDirector().getDimension() == 0)
                currentNode.getCurrentDirector().updateDimension(reverseDimension);
            
            Director parent = mTreeFileSystem.findDirectorByName(currentNode.getParentDirector().getName());
            this.mTreeFileSystem.addNode(currentNode.getCurrentDirector(), parent);
        }
        
        Iterator itBaseFirstNode = base.iterator();
        MachineId firstMachineId = (MachineId) itBaseFirstNode.next();
        
        // initial dimensions need to be remade, because now the directories have dimension 0
        Iterator<Node> depthIterator = mTreeFileSystem.iterator(firstMachineId.getCurrentDirector());
        while (depthIterator.hasNext()) {
            Node itNode = depthIterator.next();
            Director itDir = itNode.getCurrentDirector();
            
            double reverseDimension = 0;
            
            ArrayList<Director> children = itNode.getChildren();
            
            reverseDimension = children.stream().map((child) -> child.getDimension()).reduce(reverseDimension, (accumulator, _item) -> accumulator + _item);
            
            if(itDir instanceof Director && !(itDir instanceof File))
                itDir.updateDimension(reverseDimension);
        }
        
        Director currentDirector = mTreeFileSystem.findDirectorByName(firstMachineId.getParentDirector().getName()); 
        Node currentNode = mTreeFileSystem.getNodes().get(currentDirector);

        while (currentDirector != null) {
            ArrayList<Director> childrenParent = currentNode.getChildren();
            double dim = 0;

            dim = childrenParent.stream().map((child) -> child.getDimension()).reduce(dim, (accumulator, _item) -> accumulator + _item);
            currentDirector.updateDimension(dim);

            currentDirector = currentNode.getParentDirector();
            currentNode = mTreeFileSystem.getNodes().get(currentDirector);
        }
        
        CommandConfiguration.setReturnString("Success");
    }
    
    // list the cloud with its stations
    public void lscl() {
        StringBuilder sb = new StringBuilder();
        String s;
        s = mStoreUnits.get(0).listStoreUnitContent();
        sb.append(s);
        s = mStoreUnits.get(1).listStoreUnitContent();
        sb.append(s);
        s = mStoreUnits.get(2).listStoreUnitContent();
        sb.append(s);
        
        CommandConfiguration.setReturnString(sb.toString().substring(0, sb.toString().length() - 2));
    }
}
