package veto;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server {

    public Server() {}


    public static void main(String args[]) {
        try {
            // Set Policies
            System.setProperty("java.security.policy", "D:/mydoc/classes/fac/M1Aigle/s2/HMIN210/td/td1/veto/Server.policy");
            System.setSecurityManager (new SecurityManager());

            // Create Species
            Species dog = new Species("Dog");
            Species cat = new Species("Cat");

            // Create Animals
            Animal doggo = new Animal("Rudy", dog, "Border Collie", "Joseppe", new AnimalFile("Lederniersuivies"));
            Animal lechat = new Animal( "Mimi", cat, "Sphinx", "Palpatine", new AnimalFile("Suivisduchat"));

            // List of animals
            ArrayList<Animal> animals = new ArrayList<>();
            animals.add(doggo);
            animals.add(lechat);

            // Create the Office
            Office office = new Office(animals);

            office.display();

            Animal patient = office.getPatient("Rudy");
            if (patient != null) {
                System.out.println("\n[+]Un patient a été trouvé: \n" + patient.display());
            } else {
                System.out.println("[-]Ce patient n'existe pas");
            }

            // RMIRegistry
            Registry registry = LocateRegistry.createRegistry(1099);
            if (registry == null){
                System.err.println("RMIRegistry not found");
            }else{
                registry.bind("office", office);
                System.err.println("Server ready");
            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}