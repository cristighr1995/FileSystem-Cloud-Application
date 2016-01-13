package cloud.Permissions;

public interface IPermission<T> {
    
    boolean getReadPermission();
    boolean getWritePermission();
    public T getUser();
    
    void changeReadPermission(boolean read);
    void changeWritePermission(boolean write);
    
    boolean equals(Object o);
}
