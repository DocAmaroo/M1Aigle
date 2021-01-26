
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


public class Application{
    public static void main(String[] args) throws IOException {
        try{
            System.setProperty("line.separator","\n");
            String fileName = "connaissances_txt/" + "testSemPos.txt";
            KnowledgeBase maBase = new KnowledgeBase(fileName);
            System.out.println(maBase.toString());

            //Atom atomBC = new Atom(args[1]);
            HashSet<Atom> Lb = new HashSet<Atom>();
            HashSet<Atom> dejaProuves = new HashSet<Atom>();
            HashSet<Atom> dejaRefutes = new HashSet<Atom>();

            maBase.forwardChainingOpt();
            FactBase postChain = maBase.getBfSat();
            System.out.println(postChain.toString());
        }
        catch(Exception e){System.out.println("Erreur lors du chargement : " + e.getMessage());}
    }
}
