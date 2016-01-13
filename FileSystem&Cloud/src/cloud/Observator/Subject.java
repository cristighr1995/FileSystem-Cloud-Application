package cloud.Observator;

import java.util.ArrayList;

public class Subject {
    private final ArrayList<Observer> observers = new ArrayList<>();
    private Exception stateException;
    private String stateUser;

    public Exception getStateException() {
        return stateException;
    }

    public void setStateException(Exception state) {
        this.stateException = state;
        notifyExceptionObservers();
    }
    
    public void setStateUser(String stateUser) {
        this.stateUser = stateUser;
        notifyUserObservers();
    }
    
    public String getStateUser() { 
        return stateUser;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyExceptionObservers() {
        observers.stream().filter((observer) -> (observer instanceof ExceptionObserver)).forEach((observer) -> {
            observer.update();
        });
    }
    
    public void notifyUserObservers() {
        observers.stream().filter((observer) -> (observer instanceof UsersObserver)).forEach((observer) -> {
            observer.update();
        });
    }

}
