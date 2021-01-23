
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


public class Application{
    public static void main(String[] args) throws IOException {
        try{
            //if (args.length==0){throw new Exception("Merci d'indiquer un nom de fichier et un Atom a trouver");}

            System.setProperty("line.separator","\n");
            String fileName = "connaissances_txt/" + "testSemPos.txt";
            KnowledgeBase maBase = new KnowledgeBase(fileName);
            System.out.println(maBase.toString());
            //Atom atomBC = new Atom(args[1]);
            HashSet<Atom> Lb = new HashSet<Atom>();
            HashSet<Atom> dejaProuves = new HashSet<Atom>();
            HashSet<Atom> dejaRefutes = new HashSet<Atom>();
            //String[] listeNoms = {"Benoit", "Cloe", "Djamel", "Emma" , "Felix" , "Gaelle" , "Amandine", "Xena", "Habiba"};
            //String[] listeNoms = {"c"};
            
            //for(String nom : listeNoms){Atom aNom = new Atom(nom) ; System.out.println(nom +"  "+ maBase.BC3(aNom, Lb,0));}
            // for(String nom : listeNoms){
            //     Atom aNom = new Atom(nom);
            //     //System.out.println(maBase.BC3(aNom, Lb, 0));
            //System.out.println(maBase.BC3opti(new Atom("c"), Lb, dejaProuves, dejaRefutes, 0));
            // }
            maBase.forwardChainingOpt();
            FactBase postChain = maBase.getBfSat();
            System.out.println(postChain.toString());
        }
        catch(Exception e){System.out.println("Erreur lors du chargement : " + e.getMessage());}
    }
}
