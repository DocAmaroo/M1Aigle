package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AlgoGenTSP {
    int distances[][];
    int nombreVilles;
    ArrayList<Individu> population = new ArrayList<>();

    public class Individu implements Comparable<Individu> {
        private int genome[];
        private int score;

        Individu() {
            genome = new int[nombreVilles - 1];
        }

        public int[] getGenome() {
            return genome;
        }

        public int getScore() { return score; }

        // retourne 0 si egaux, negatif si plus petit, positif si plus grand
        public int compareTo(Individu compare) {
            return score - compare.score;
        }

        @Override
        public String toString() {
            String ret = "0 -> ";
            for (int x = 0; x < nombreVilles - 1; x++) {
                ret += genome[x] + " -> ";
            }
            calculerScore();
            ret += "0 = " + score;
            return ret;
        }

        public Individu random() {
            for (int x = 0; x < nombreVilles - 1; x++)
                getGenome()[x] = x + 1;

            for (int rep = 10; rep > 0; rep--) {
                int x = (int) (Math.random() * (nombreVilles - 1));
                int y = (int) (((Math.random() * (nombreVilles - 2)) + x + 1)) % (nombreVilles - 1);
                int temp = getGenome()[x];
                getGenome()[x] = getGenome()[y];
                getGenome()[y] = temp;
            }
            return this;
        }

        public void calculerScore() {
            score = 0;
            int villePrec = 0;
            for (int x = 0; x < nombreVilles - 1; x++) {
                score += distances[villePrec][genome[x]];
                villePrec = genome[x];
            }
            score += distances[genome[nombreVilles - 2]][0];
        }

        public Individu croiser(Individu compare) {
            int pivot = (int) (Math.random() * (nombreVilles - 3)) + 1;
            Individu ret = new Individu();

            // On copie les informations du premier individus jusqu'au pivot
            if (pivot >= 0) System.arraycopy(genome, 0, ret.genome, 0, pivot);
            int offset = 0;

            // On complète le chemin avec les villes du second individus (si valide)
            for (int i = 0; i < nombreVilles - 1; i++) {
                boolean keep = true;
                for (int j = 0; j < pivot; j++) {
                    if (genome[j] == compare.genome[i]) {
                        keep = false;
                        break;
                    }
                }
                if (keep) {
                    ret.genome[pivot + offset] = compare.genome[i];
                    offset++;
                }
            }
            return ret;
        }

        public Individu muter() {
            int pivot = (int) (Math.random() * (nombreVilles - 2));
            int tmp = genome[pivot];
            genome[pivot] = genome[pivot + 1];
            genome[pivot + 1] = tmp;
            return this;
        }
    }

    public AlgoGenTSP(int[][] dist, int nbVilles) {
        distances = dist;
        this.nombreVilles = nbVilles;
    }

    public void genererPopulation(int taillePop) {
        population = new ArrayList<>();
        for (int i = 0; i < taillePop; i++) {
            population.add(new Individu().random());
        }
    }

    public void evaluerPopulation() {
        for (Individu i : population) {
            i.calculerScore();
        }
    }

    public void trierPopulation() {
        Collections.sort(population);
    }

    public void nouvelleGeneration(double tauxSelection) {
        ArrayList<Individu> newGen = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            int ip1 = (int) (Math.random() * (population.size() * tauxSelection));
            int ip2 = ip1;
            while (ip2 == ip1) {
                ip2 = (int) (Math.random() * (population.size() * tauxSelection));
            }
            newGen.add(population.get(ip1).croiser(population.get(ip2)).muter());
        }
        population = newGen;
    }

    public void afficherPopulation() {
        for (Individu i : population) {
            System.out.println(i);
        }
    }

    public void testCroiser() {
        Individu p1 = new Individu().random();
        Individu p2 = new Individu().random();
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p1.croiser(p2));
    }

    public void testMuter() {
        Individu p1 = new Individu().random();
        System.out.println(p1);
        System.out.println(p1.muter());
    }
}
