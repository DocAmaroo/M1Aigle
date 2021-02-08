package base;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IOffice extends Remote {
    ArrayList<IAnimal> getAnimals() throws RemoteException;
    void setAnimals(ArrayList<IAnimal> animals) throws RemoteException;
    IAnimal getPatient(String name) throws RemoteException;
    void addPatient(IAnimal animal) throws RemoteException;
    void removePatient(String name) throws RemoteException;
    void registerClient(IClient client) throws RemoteException;
    void disconnectClient(IClient client) throws RemoteException;
    void alertAllClients(int n) throws RemoteException;
    boolean contains(String name) throws RemoteException;

    void display() throws RemoteException;
}
