package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Office extends UnicastRemoteObject implements IOffice {

    private ArrayList<IAnimal> animals = new ArrayList<>();
    private ArrayList<IClient> clients = new ArrayList<>();

    public Office() throws RemoteException { }


    public ArrayList<IAnimal> getAnimals() {
        return animals;
    }


    public void setAnimals(ArrayList<IAnimal> animals) throws RemoteException {
        this.animals = animals;
    }


    public IAnimal getPatient(String name) throws RemoteException {
        for (IAnimal animal : this.animals) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        System.out.println("[-]Aucun patient n'est affilié à ce prénom!");
        return null;
    }


    public void addPatient(IAnimal animal) throws RemoteException {
        this.animals.add(animal);
        checkCeiling();
    }


    public void removePatient(String name) throws RemoteException {
        for (int i = 0; i < this.animals.size(); i++) {
            if (this.animals.get(i).getName().contentEquals(name)) {
                this.animals.remove(i);
                break;
            }
        }
        checkCeiling();
    }


    public void registerClient(IClient client) throws RemoteException {
        this.clients.add(client);
        System.out.println("Client registred : " + client.toString());
    }


    public void disconnectClient(IClient client) throws RemoteException {
        this.clients.remove(client);
        System.out.println("Client disconnected:  " + client.toString());
    }


    public void alertAllClients(int n) throws RemoteException {
        for(IClient client : this.clients ) {
            client.alert(n);
        }
    }


    public boolean contains(String name) throws RemoteException {
        boolean res = false;
        for ( IAnimal a : this.animals) {
            if ( a.getName().equals(name) ) {
                res = true;
            }
        }
        return res;
    }


    public void checkCeiling() throws RemoteException {
        switch(this.animals.size()) {
            case 100:
                this.alertAllClients(100);
                break;
            case 500:
                this.alertAllClients(500);
                break;
            case 1000:
                this.alertAllClients(1000);
                break;
            default:

        }
    }


    public void display() throws RemoteException {
        for (IAnimal animal : this.animals) {
            System.out.println(animal.display());
        }
    }
}
