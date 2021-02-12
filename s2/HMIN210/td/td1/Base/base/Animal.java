package base;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Animal extends UnicastRemoteObject implements IAnimal {

	private String name;		// nom
	private Species species; 	// espèce
	private String breed; 		// race
	private String trainer;		// maître
	private FollowUpFile file; 	// dossier de suivie

	public Animal() throws RemoteException{}

	public Animal(String name, Species species, String breed, String trainer, FollowUpFile file) throws RemoteException {
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
	public FollowUpFile getAnimalFile() throws RemoteException { return this.file; }

	// Display
	public String display() throws RemoteException{
		return "{ " + this.getName() + ": " +
				"[" + this.getSpecies().getSpecies() + ", " + this.getBreed() + "] | " +
				"Trainer: " + this.getTrainer() + " | " +
				"LastCheckUp: " + this.file.getLastCheckUp() +
				"}";
	}
}