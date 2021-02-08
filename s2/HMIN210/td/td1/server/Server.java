package server;
import base.*;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Server {

    public Server() {}


    public static void main(String args[]) {
        try {

            // Codebase
            System.setProperty("java.rmi.server.codebase", "file:/D:/mydoc/classes/fac/M1Aigle/s2/HMIN210/td/td1/out/production/td1/base");

            // Set Policies
            System.setProperty("java.security.policy", "file:/D:/mydoc/classes/fac/M1Aigle/s2/HMIN210/td/td1/server/Server.policy");
            System.setSecurityManager (new SecurityManager());

            // Create Species
            Species dog = new Species("Dog");
            Species cat = new Species("Cat");

            // Create Animals
            IAnimal doggo = new Animal("Rudy", dog, "Border Collie", "Joseppe", new AnimalFile("Lederniersuivies"));
            IAnimal lechat = new Animal( "Mimi", cat, "Sphinx", "Palpatine", new AnimalFile("Suivisduchat"));


            // Create the Office
            Office office = new Office();
            office.addPatient(doggo);
            office.addPatient(lechat);
            for (int i = 0; i < 97; i++) {
                office.addPatient(doggo); // I lub doggos <3
            }

            // RMIRegistry
            Registry registry = LocateRegistry.createRegistry(1099);
            if (registry == null){
                System.err.println("RMIRegistry not found");
            } else{
                registry.bind("office", office);
                System.err.println("Server ready");
            }

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}