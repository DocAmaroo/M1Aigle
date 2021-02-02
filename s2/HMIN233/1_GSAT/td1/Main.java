import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class Literal {
    public int indexIntoTruthArray;
    public boolean positive;

    public Literal(int newIndexIntoTruthArray, boolean newPositive) {
        indexIntoTruthArray = newIndexIntoTruthArray;
        positive = newPositive;
    }
}


class Clause {
    private final ArrayList<Literal> literals;

    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    public boolean trySolve(boolean[] truthAssignments) {
        if (truthAssignments[literals.get(0).indexIntoTruthArray] == literals.get(0).positive) {
            return true;
        } else if (truthAssignments[literals.get(1).indexIntoTruthArray] == literals.get(1).positive) {
            return true;
        } else {
            return truthAssignments[literals.get(2).indexIntoTruthArray] == literals.get(2).positive;
        }
    }
}


class CNF {
    private final ArrayList<Clause> clauses;
    private final int variableCount;

    public CNF(ArrayList<Clause> clauses, int variableCount) {
        this.clauses = clauses;
        this.variableCount = variableCount;
    }

    public int numberOfClauses() {
        return this.clauses.size();
    }

    public int numberOfVariables() {
        return this.variableCount;
    }

    public int trySolve(boolean[] truthAssignments) {
        int numberSolved = 0;

        for (Clause clause : this.clauses) {
            if (clause.trySolve(truthAssignments)) { numberSolved++; }
        }

        return numberSolved;
    }
}


class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String filePath = args[0];
        System.out.println("[+]filePath: " + filePath);

        CNF cnf = readFile(filePath);

        boolean[] solved = GSAT(cnf, 50, 50);

        displaySolved(solved);
    }


    public static CNF readFile(String filePath) throws FileNotFoundException {

        // --- File reading
        InputStream file = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(file));

        Scanner scanner = new Scanner(br);
        String token = scanner.nextLine();
        String[] splitRes;

        // --- CNF var.
        ArrayList<Clause> clauses = new ArrayList<>();
        int variableCount = 0;

        // --- Start reading
        while (!token.equals("%")) {
            splitRes = token.trim().split(" "); //Split the line

            // Skip comments
            if (splitRes[0].equals("c")) {
                token = scanner.nextLine();
                continue;
            }

            // Initialise the GSAT problem with the problem's sizes given in the line starting with "p"
            if (splitRes[0].equals("p")) {
                variableCount = Integer.parseInt(splitRes[2]); //Get total number of symbols
                token = scanner.nextLine();
                continue;
            }

            // Clauses initialisation
            else {
                ArrayList<Literal> literals = new ArrayList<>();

                for (int i = 0; i < splitRes.length - 1; i++) {
                    int absoluteVar = Math.abs(Integer.parseInt(splitRes[i]));
                    boolean isTrue = Integer.parseInt(splitRes[i]) > 0;
                    literals.add(new Literal(absoluteVar, isTrue));
                }

                Clause clause = new Clause(literals);
                clauses.add(clause);
            }

            //Go to the next line
            token = scanner.nextLine();
        }

        return new CNF(clauses, variableCount);
    }


    public static void randomlyGenerateTruthAssignments(boolean[] truthAssignments) {
        for (int i = 1; i < truthAssignments.length; i++) {
            truthAssignments[i] = Math.random() > 0.5;
        }
    }


    public static boolean[] GSAT(CNF cnfToSolve, int maxRestarts, int maxClimbs) {
        boolean[] truthAssignments = new boolean[cnfToSolve.numberOfVariables() + 1];
        for (int i = 1; i <= maxRestarts; i++) {
            randomlyGenerateTruthAssignments(truthAssignments);
            for (int j = 1; j <= maxClimbs; j++) {
                int clausesSolved = cnfToSolve.trySolve(truthAssignments);

                if (clausesSolved == cnfToSolve.numberOfClauses()) {
                    truthAssignments[0] = true;
                    return truthAssignments;
                }

                truthAssignments = randomBestSuccesor(truthAssignments, cnfToSolve);
            }
        }
        truthAssignments[0] = false;
        return truthAssignments;
    }


    public static boolean[] randomBestSuccesor(boolean[] oldTruthAssignments, CNF cnfToSolve)
    {
        ArrayList<boolean[]> potentialAssignments = new ArrayList<>();
        for (int i = 1; i < oldTruthAssignments.length; i++)
        {
            boolean[] newTruthAssignments = oldTruthAssignments.clone();
            newTruthAssignments[i] = !oldTruthAssignments[i];
            potentialAssignments.add(newTruthAssignments);
        }

        ArrayList<boolean[]> bestSuccesors = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            Iterator assignmentsList = potentialAssignments.iterator();

            int currentBestClausesSolved = -1;
            boolean[] currentBestAssignment = null;
            while (assignmentsList.hasNext())
            {
                boolean[] currentAssignment = (boolean[])assignmentsList.next();

                int currentClausesSolved = cnfToSolve.trySolve(currentAssignment);
                if (currentClausesSolved > currentBestClausesSolved)
                {
                    currentBestClausesSolved = currentClausesSolved;
                    currentBestAssignment = currentAssignment;
                }
            }

            potentialAssignments.remove(currentBestAssignment);
            bestSuccesors.add(currentBestAssignment);
        }

        int randomChoice = (int)Math.floor(Math.random() * 10.0);

        return bestSuccesors.get(randomChoice);
    }

    public static void displaySolved(boolean[] solved) {
        if (solved[0]) {
            System.out.println("An answer was found, it is as follows :");
            for (int i = 1; i < solved.length; i++) {
                System.out.println("Variable " + i + " is " + solved[i]);
            }
        } else {
            System.out.println("No answer was found, sorry");
        }
    }

}


//    public static SAT parse(String filePath) throws Exception {
//        InputStream is = new FileInputStream(filePath);
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//        Scanner scanner = new Scanner(br);
//        String token = scanner.nextLine();
//        String[] splitRes;
//        SAT sat = null;
//        int clause = 0;

//        while (!token.equals("%")) {
//
//            //Split the line
//            splitRes = token.trim().split(" ");
//
//            //Skip the comments
//            if (splitRes[0].equals("c")) {
//                token = scanner.nextLine();
//                continue;
//            }
//
//            //Initialise the GSAT problem with the problem's sizes given in the line starting with "p"
//            if (splitRes[0].equals("p")) {
//                try {
//                    //splitRes[2] = nb de symboles
//                    //splitRes[4] = nb de clauses
//                    sat = new SAT(Integer.parseInt(splitRes[4]), Integer.parseInt(splitRes[2]));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                ;
//                token = scanner.nextLine();
//                continue;
//            }
//
//            //TODO ICI GERER SPLITRES POUR INITIALISER LES CLAUSES :
//
//            for (int i = 0; i < splitRes.length - 1; i++) {
//                // ajoute à la clause j la variable
//                sat.addNewClause(clause, Integer.parseInt(splitRes[i]));
//            }
//
//            clause++;
//
//            //Go to the next line
//            token = scanner.nextLine();
//        }
//        return sat;
//    }