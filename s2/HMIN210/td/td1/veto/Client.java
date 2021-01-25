package veto;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    private Client() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];

        try {
            // Set policies
            System.setProperty("java.security.policy", "D:/mydoc/classes/fac/M1Aigle/s2/HMIN210/td/td1/veto/Client.policy");
            System.setSecurityManager(new SecurityManager());

            Registry registry = LocateRegistry.getRegistry(host);
            IOffice proxy = (IOffice) registry.lookup("office");

            System.out.println("[+]Ajouter un animal:\n");
            String name = "";
            String trainer = "";
            String spieces = "";
            String breed = "";

            Scanner sc2 = new Scanner(System.in);

            System.out.println("Name of the animal ?");
            name = sc2.nextLine();
            System.out.println("Name of the animal's master ?");
            trainer = sc2.nextLine();
            System.out.println("Breed of the animal ?");
            breed = sc2.nextLine();
            System.out.println("Species of the animal ?");
            spieces = sc2.nextLine();
            Species s = new Species(spieces);

            AnimalFile file = new AnimalFile("Empty");

            proxy.addPatient(new Animal(name, s, breed, trainer, file));


        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}