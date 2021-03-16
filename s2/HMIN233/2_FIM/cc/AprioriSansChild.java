import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AprioriSansChild {

    public static void decreasing_order(HashMap<Integer, String> l, int freqMax, int minSup) {
        System.out.println("Affichage des items par ordre décroisant des fréquence : ");
        for (int i = freqMax; i >= minSup ; i--) {
            System.out.println("Freq " + i + " : " + l.get(i));
        }
    }

    public static void increasing_order(HashMap<Integer, String> l, int freqMax, int minSup) {
        System.out.println("Affichage des items par ordre croisant des fréquence : ");
        for (int i = minSup; i <= freqMax ; i++) {
            System.out.println("Freq " + i + " : " + l.get(i));
        }
    }

    public static void main(String[] args) {

        try {
            long startTime = System.nanoTime();
            // Minimum support
            int minSup = 3;
            int k = 1;
            HashMap<Integer, String> lsorted = new HashMap<>();
            ArrayList<String> items = new ArrayList<>();
            ArrayList<ArrayList<String>> candidats = new ArrayList<>();
            ArrayList<ArrayList<String>> lk = new ArrayList<>();
            ArrayList<ArrayList<String>> lf = new ArrayList<>();

            // Premiere lecture de fichier
            // Recuperation des items ainsi que le nombre de fois qu'ils apparaissent
            File f = new File("DataSets/Toy.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                String[] mots = readLine.split(" ");
                for (String mot : mots) {
                    items.add(mot);
                }
            }
            Collections.sort(items);

            // Creation d'une liste contenant chaque item
            Set<String> mySet = new HashSet<String>(items);
            List<String> listItems = new ArrayList<String>(mySet);
            System.out.print("Liste des Items : { ");
            for (String s : listItems) {
                System.out.print(s + " ");
            }
            System.out.println("}");

            // Creation de L1, et insertion des premiers itemsets ayant un minsup superieur a minsup dans la liste lf
            int freqMax = 0;
            int freqTmp = 0;
            for (String i : items) {
                freqTmp = Collections.frequency(items,i);
                if (freqTmp >= minSup){
                    if (!lf.contains(new ArrayList<>(Collections.singleton(i))) && !lk.contains(new ArrayList<>(Collections.singleton(i)))){
                        if (Collections.frequency(items,i) > freqMax) {
                            freqMax = Collections.frequency(items,i);
                        }
                        lk.add(new ArrayList<>(Collections.singleton(i)));
                        lf.add(new ArrayList<>(Collections.singleton(i)));
                        if (lsorted.containsKey(freqTmp)) {
                            lsorted.replace(freqTmp, lsorted.get(freqTmp) + ", " + i);
                        } else {
                            lsorted.put(freqTmp, i);
                        }
                    }
                }
            }

            System.out.println("Fréquence Max d'un item = " + freqMax);

            // Affichage de L1
//            System.out.print("L"+ k +" = { ");
//            for (ArrayList<String> i : lk) {
//                for (String s : i) {
//                    System.out.print(s);
//                }
//                System.out.print(" ");
//            }
//            System.out.println("}");

            // Boucle while
            // Generation de candidats
            // Creation des nouveaux Lk
            while (!lk.isEmpty()) {

                // Creation des candidats
                for (int i = 0; i < lk.size(); i++) {
                    for (int j = i+1; j < lk.size(); j++) {
                        ArrayList<String> tmp = new ArrayList<>();
                        for (String item1 : lk.get(i)) {
                            if (!tmp.contains(item1)) {
                                tmp.add(item1);
                            }
                        }
                        for (String item2 : lk.get(j)) {
                            if (!tmp.contains(item2)) {
                                tmp.add(item2);
                            }
                        }
                        Collections.sort(tmp);
                        if (!candidats.contains(tmp) && (tmp.size() == k+1)) {
                            candidats.add(tmp);
                        }
                    }
                }

                // Affichage des candidats
//                System.out.print("Candidats = { ");
//                for (ArrayList<String> i : candidats) {
//                    for (String s : i) {
//                        System.out.print(s);
//                    }
//                    System.out.print(" ");
//                }
//                System.out.println("}");

                // Calcul de frequence des candidats
                lk.clear();
                k++;
                int freq;
                int cpt;
                for (ArrayList<String> candidat : candidats) {
                    freq = 0;
                    BufferedReader b1 = new BufferedReader(new FileReader(f));
                    String readLine1 = "";
                    while ((readLine1 = b1.readLine()) != null){
                        cpt = 0;
                        for (int i = 0; i < candidat.size(); i++) {
                            String[] mots = readLine1.split(" ");
                            for (String mot : mots) {
                                if (candidat.get(i).equals(mot)) {
                                    cpt++;
                                }
                            }
                        }
                        if (cpt == candidat.size()) {
                            freq++;
                        }
                    }
                    // Insertion des candidats dans le nouveau Lk
                    // et dans lf
                    if (freq >= minSup) {
                        lk.add(candidat);
                        lf.add(candidat);
                        if (lsorted.containsKey(freq)) {
                            lsorted.replace(freq, lsorted.get(freq) + ", " + candidat.toString());
                        } else {
                            lsorted.put(freq, candidat.toString());
                        }
                    }
                }
                candidats.clear();

//                System.out.print("L" + k + " = { ");
//                for (ArrayList<String> i : lk) {
//                    for (String s : i) {
//                        System.out.print(s);
//                    }
//                    System.out.print(" ");
//                }
//                System.out.println("}");
            }

            // Affichage de la liste finale
            System.out.print("L = { ");
            for (ArrayList<String> i : lf) {
                for (String s : i) {
                    System.out.print(s);
                }
                System.out.print(" ");
            }
            System.out.println("}");

            decreasing_order(lsorted, freqMax, minSup);
            increasing_order(lsorted, freqMax, minSup);

            long realTime = System.nanoTime() - startTime;
            System.out.println("tempsReel : " + realTime/1000000 + "ms");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
