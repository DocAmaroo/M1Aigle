import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

class SAT {

    private int nbclause;
    private int nbsymb;

    public SAT(int nbclause, int nbsymb) {
        this.nbclause = nbclause;
        this.nbsymb = nbsymb;
    }

    public void addNewClause(int clause, int parseInt) {
        
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println("J'aime la brandade de morue");
    }


    public static SAT parse(String filePath) throws Exception {
        InputStream is = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        Scanner scanner = new Scanner(br);
        String token = scanner.nextLine();
        String[] splitRes;
        SAT sat = null;
        int clause = 0;

        while (!token.equals("%")) {

            //Split the line
            splitRes = token.trim().split(" ");

            //Skip the comments
            if (splitRes[0].equals("c")) {
                token = scanner.nextLine();
                continue;
            }

            //Initialise the GSAT problem with the problem's sizes given in the line starting with "p"
            if (splitRes[0].equals("p")) {
                try {
                    //splitRes[2] = nombre de symboles
                    //splitRes[4] = nombre de clauses
                    //Initialiser l'objet représentant la SAT
                    sat = new SAT(Integer.parseInt(splitRes[4]), Integer.parseInt(splitRes[2]));
                } catch (Exception e){
                    e.printStackTrace();
                };
                token = scanner.nextLine();
                continue;
            }

            //TODO ICI GERER SPLITRES POUR INITIALISER LES CLAUSES :

            for (int i = 0; i < splitRes.length - 1; i++) {
                // ajoute à la clause j la variable
                sat.addNewClause(clause, Integer.parseInt(splitRes[i]));
            }

            clause++;

            //Go to the next line
            token = scanner.nextLine();
        }
        return sat;
    }
}