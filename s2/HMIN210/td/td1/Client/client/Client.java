package client;

import base.IClient;
import base.IOffice;
import base.Species;
import base.Animal;
import base.IAnimal;
import base.AnimalFile;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client implements IClient {
    private Client() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }

    public static void main(String[] args) throws RemoteException {

        IClient client = new Client();
        String host = (args.length < 1) ? null : args[0];

        try {
            // Codebase
            System.setProperty("java.rmi.server.codebase", "file:/D:/mydoc/classes/fac/M1Aigle/s2/HMIN210/td/td1/Base/out/production/Base");

            // Set policies
            System.setProperty("java.security.policy", "D:/mydoc/classes/fac/M1Aigle/s2/HMIN210/td/td1/Client/client/Client.policy");
            System.setSecurityManager(new SecurityManager());

            // Get Proxy
            Registry registry = LocateRegistry.getRegistry(host);
            IOffice stub = (IOffice) registry.lookup("office");

            // Register client connection
            stub.registerClient(client);

            int state = 1;
            Scanner StateScanner = new Scanner(System.in);
            while(state != 0) {

                // Message de bienvenue
                System.out.println("Bienvenue, que souhaitez-vous faire ?\n(0:quitter | 1:affichage | 2:ajouter un patient | 3:supprimer un patient | 4:info d'un patient)");

                state = StateScanner.nextInt();

                switch (state) {
                    case 0 -> {
                        stub.disconnectClient(client);
                        System.out.println("[+]Vous avez été déconnecté!");
                        System.exit(0);
                    }
                    case 1 -> stub.display();
                    case 2 -> {
                        String name = "";
                        String trainer = "";
                        String spieces = "";
                        String breed = "";
                        Scanner AnimalScanner = new Scanner(System.in);
                        System.out.println("Name of the animal ?");
                        name = AnimalScanner.nextLine();
                        System.out.println("Name of the animal's master ?");
                        trainer = AnimalScanner.nextLine();
                        System.out.println("Breed of the animal ?");
                        breed = AnimalScanner.nextLine();
                        System.out.println("Species of the animal ?");
                        spieces = AnimalScanner.nextLine();
                        IAnimal patient = new Animal(name, new Species(spieces), breed, trainer, new AnimalFile("Empty"));
                        stub.addPatient(patient);
                    }
                    case 3 -> {
                        System.out.println("Quel patient souhaitez-vous supprimer ?(par son nom) ");
                        Scanner patientToDeleteScanner = new Scanner(System.in);
                        String patientToDelete = patientToDeleteScanner.nextLine();
                        stub.removePatient(patientToDelete);
                    }
                    case 4 -> {
                        System.out.println("Quel patient recherchez-vous ?(par son nom) ");
                        Scanner patientToCheckScanner = new Scanner(System.in);
                        String patientToCheck = patientToCheckScanner.nextLine();
                        IAnimal patientRetrieve = stub.getPatient(patientToCheck);
                        if (patientRetrieve != null) {
                            System.out.println("[+]Patient retrouvé: ");
                        }
                    }
                    default -> System.out.println("[-]Commande non reconnue, réessayez s'il vous plaît");
                }
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void alert(int n) throws RemoteException {
        System.out.println("[ALERT] " + n + " patients are registred");
    }
}