package veto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IOffice extends Remote {
    ArrayList<Animal> getAnimals() throws RemoteException;
    void setAnimals(ArrayList<Animal> animals) throws RemoteException;
    Animal getPatient(String name) throws RemoteException;
    void addPatient(Animal animal) throws RemoteException;
}
