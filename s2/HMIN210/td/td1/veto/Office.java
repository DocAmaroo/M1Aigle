package veto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Office extends UnicastRemoteObject implements IOffice {

    private ArrayList<Animal> animals;

    public Office(ArrayList<Animal> animals) throws RemoteException {
        this.animals = animals;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) throws RemoteException {
        this.animals = animals;
    }

    public Animal getPatient(String name) throws RemoteException {
        for (Animal animal : this.animals) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        return null;
    }

    public void addPatient(Animal animal) throws RemoteException {
        animals.add(animal);
    }

    public void display() throws RemoteException {
        for (Animal animal : this.animals) {
            System.out.println(animal.display());
        }
    }
}
