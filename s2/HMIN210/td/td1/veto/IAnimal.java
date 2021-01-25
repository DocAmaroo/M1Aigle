package veto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAnimal extends Remote {
    String getName() throws RemoteException;
//    String getSpecies() throws RemoteException;
    Species getSpecies() throws RemoteException;
    String getBreed() throws RemoteException;
    String getTrainer() throws RemoteException;
    FollowUpFile getAnimalFile() throws RemoteException;
    String display() throws RemoteException;
}