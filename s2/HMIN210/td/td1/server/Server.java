package server;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Server {

    public Server() {}


    public static void main(String args[]) {
        try {
            // Set Policies
            System.setProperty("java.security.policy", "D:/mydoc/classes/fac/M1Aigle/s2/HMIN210/td/td1/server/Server.policy");
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