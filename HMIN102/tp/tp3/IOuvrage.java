package tp3;

import javafx.util.Pair;
import java.util.ArrayList;

public interface IOuvrage {
    public int nbExemplaire();
    public ArrayList<Pair<String, Integer>> listMediaAvecNbOuvrageDispo();
}
