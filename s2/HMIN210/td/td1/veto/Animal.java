package veto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Animal extends UnicastRemoteObject implements IAnimal {

	private String name;		// nom
	private Species species; 	// espèce
	private String breed; 		// race
	private String trainer;		// maître
	private AnimalFile file; 	// dossier de suivie

	public Animal(String name, Species species, String breed, String trainer, AnimalFile file) throws RemoteException {
		this.name = name;
		this.species = species;
		this.breed = breed;
		this.trainer = trainer;
		this.file = file;
	}

	// Getters
	public String getName() throws RemoteException { return this.name; }
	public Species getSpecies() throws RemoteException { return this.species; }
	public String getBreed() throws RemoteException { return this.breed; }
	public String getTrainer() throws RemoteException { return this.trainer; }
	public AnimalFile getAnimalFile() throws RemoteException { return this.file; }

	// Display
	public String display() throws RemoteException{
		String res = "";
		res += "{ " + this.getName() + ": \n";
		res += "\t[" + this.getSpecies().getSpecies() + ", " + this.getBreed() + "]\n";
		res += "\tTrainer: " + this.getTrainer() + "\n";
		res += "\tLastCheckUp: " + this.file.getLastCheckUp();
		res += "\n}";
		return res;
	}
}