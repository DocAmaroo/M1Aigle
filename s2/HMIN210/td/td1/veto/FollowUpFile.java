package veto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FollowUpFile extends Remote {
    String getLastCheckUp() throws RemoteException;
    void setLastCheckUp(String checkup) throws RemoteException;
}
