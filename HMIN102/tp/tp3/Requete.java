package tp3;

import java.util.ArrayList;

public class Requete {
    private ArrayList<String> mediatheque = new ArrayList<String>();

    public Requete() {
        this.mediatheque.add("Fabrègues");
        this.mediatheque.add("Baillargues");
        this.mediatheque.add("Prades");
    }

    public static ArrayList<String> listeMediatheques(IOuvrage ouvrage){
        return new ArrayList<String>();
    }

    public ArrayList<String> getMediatheque(IOuvrage ouvrage) {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }
}
