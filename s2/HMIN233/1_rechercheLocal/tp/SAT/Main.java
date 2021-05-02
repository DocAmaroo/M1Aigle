import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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

        // --- If found one literal as true => return true
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

    // --- Return the count of clause resolved
    public int trySolve(boolean[] truthAssignments) {
        int numberSolved = 0;

        for (Clause clause : this.clauses) {
            if (clause.trySolve(truthAssignments)) {
                numberSolved++;
            }
        }

        return numberSolved;
    }
}


class Main {
    public static void main(String[] args) throws FileNotFoundException {

        if (args.length == 0) {
            System.out.println("Usage: java Main [CNF_FILE_PATH]");
            System.out.println("\n[CNF_FILE_PATH]: the file path to the cnf file to resolve");
            System.out.println("--- example (with the cnf file give): java Main ./mycnf.cnf");
            System.exit(1);
        }
        String filePath = args[0];

        System.out.println("[~]Loading file...");
        CNF cnf = readFile(filePath);

        boolean[] solved = GSAT(cnf, 50, 50);
        displaySolved(solved);
    }


    public static CNF readFile(String filePath) throws FileNotFoundException {

        // --- File reading
        InputStream file = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        System.out.println("[+]" + filePath + " has been loaded successfully");
        System.out.println("\n[~]Searching a potential solution...");
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


    public static boolean[] GSAT(CNF cnfToSolve, int maxTries, int maxFlips) {

        // --- Credits :
        // --- https://cs.stackexchange.com/questions/219/implementing-the-gsat-algorithm-how-to-select-which-literal-to-flip

        boolean[] truthAssignments = new boolean[cnfToSolve.numberOfVariables() + 1];

        for (int i = 1; i <= maxTries; i++) {
            randomlyGenerateTruthAssignments(truthAssignments);

            for (int j = 1; j <= maxFlips; j++) {

                // --- If is satisfiable
                int clausesSolved = cnfToSolve.trySolve(truthAssignments);
                if (clausesSolved == cnfToSolve.numberOfClauses()) {
                    truthAssignments[0] = true;
                    return truthAssignments;
                }

                // --- Flip literals
                truthAssignments = randomBestSuccessors(truthAssignments, cnfToSolve);
            }
        }
        truthAssignments[0] = false;
        return truthAssignments;
    }


    // --- Looking for the variable whose flip yield the most important raise in the number of satisfied clauses;
    public static boolean[] randomBestSuccessors(boolean[] oldTruthAssignments, CNF cnfToSolve) {

        // Get all possibilities by flipping one by one each assignments
        ArrayList<boolean[]> potentialAssignments = new ArrayList<>();
        for (int i = 1; i < oldTruthAssignments.length; i++) {
            boolean[] newTruthAssignments = oldTruthAssignments.clone();
            newTruthAssignments[i] = !oldTruthAssignments[i];
            potentialAssignments.add(newTruthAssignments);
        }

        // Solved all possibilities and get the 10 best ones possibilities
        // (We could also only get the first one we found)
        // (Also we could checking the best one of the best possibilities)
        ArrayList<boolean[]> bestSuccessors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int currentBestClausesSolved = -1;
            boolean[] currentBestAssignment = null;

            for (boolean[] currentAssignment : potentialAssignments) {
                int currentClausesSolved = cnfToSolve.trySolve(currentAssignment);
                if (currentClausesSolved > currentBestClausesSolved) {
                    currentBestClausesSolved = currentClausesSolved;
                    currentBestAssignment = currentAssignment;
                }
            }

            // if no solution find => no more solution available => quit the loop
            if (currentBestAssignment == null) {
                break;
            }

            // Remove the one we found, avoiding getting him again next round
            potentialAssignments.remove(currentBestAssignment);

            // Add it in our best successors collection
            bestSuccessors.add(currentBestAssignment);
        }

        // return one (out the 10 we possibly found) and make sure he his not null
        int randomChoice;
        do {
            randomChoice = (int) Math.floor(Math.random() * 10.0);
        } while (bestSuccessors.get(randomChoice) == null);

        return bestSuccessors.get(randomChoice);
    }


    public static void displaySolved(boolean[] solved) {
        if (solved[0]) {
            System.out.println("[+]An answer was found! See solution below:");
            System.out.println("\n======= SOLVED =======");
            for (int i = 1; i < solved.length; i++) {
                System.out.println("Literal " + i + " -> " + solved[i]);
            }
        } else {
            System.out.println("[-]No answer was found! Try again!");
        }
    }

}