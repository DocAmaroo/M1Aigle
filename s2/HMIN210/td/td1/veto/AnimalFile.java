package veto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AnimalFile extends UnicastRemoteObject implements FollowUpFile {

    protected String checkup;

    public AnimalFile(String checkup) throws RemoteException {
        this.checkup = checkup;
    }

    public String getLastCheckUp() throws RemoteException {
        System.out.println("Appel de la méthode getLastCheckUp() dans AnimalFile");
        return this.checkup;
    }

    public void setLastCheckUp(String checkup) throws RemoteException {
        this.checkup = checkup;
    }
}
