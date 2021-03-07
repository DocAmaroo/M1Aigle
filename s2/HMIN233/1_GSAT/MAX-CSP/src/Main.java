import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("Usage: java Main [COL_FILE_PATH]");
            System.out.println("\n[COL_FILE_PATH]: the file path to the .col file to resolve");
            System.out.println("--- example (with the .col file give): java Main ./le450_5a.cnf");
            System.exit(1);
        }
        String filePath = args[0];

        System.out.println("[~]Loading file...");
        Graph graph = initGraphWithFile(filePath);
        System.out.println("[+]The graph has been successfully initialize!");
        System.out.println("Couleur de départ: " + graph.getNbColors());
        graph.recuitSimule();
        System.out.println("Couleur de fin: " + graph.getNbColors());
        System.out.println(Arrays.toString(graph.getColors()));
    }

    public static Graph initGraphWithFile(String filePath) throws FileNotFoundException {

        // Initialisation du fichier
        InputStream file = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        System.out.println("[+]" + filePath + " has been loaded successfully!");
        System.out.println("\n[~]Building the graph...");
        Scanner scanner = new Scanner(br);
        String token = scanner.nextLine();
        String[] lineSplit;


        int nbVertices = 0;
        ArrayList<Vertex> vertices = new ArrayList<>(); // Liste de tout les sommets
        int MAXCSP = 0;
        double avgDegree = 0;

        // Lecture du fichier
        while (scanner.hasNextLine()) {
            lineSplit = token.trim().split(" "); //Split the line

            // Skip des commentaires
            if (lineSplit[0].equals("c")) {
                int lineSplitLength = lineSplit.length;
                if (lineSplitLength > 1) {
                    if (lineSplit[1].equals("avg")) {
                        avgDegree = Math.floor(Double.parseDouble(lineSplit[lineSplitLength - 1]));
                    }
                    if (lineSplit[1].equals("number")) {
                        MAXCSP = Integer.parseInt(lineSplit[lineSplitLength - 1]);
                        System.out.println(MAXCSP);
                    }
                }
                token = scanner.nextLine();
                continue;
            }

            // On récupère les paramètres
            if (lineSplit[0].equals("p")) {
                nbVertices = Integer.parseInt(lineSplit[2]);

                // On initialise tout les sommets
                for (int i = 0; i < nbVertices; i++) {
                    vertices.add(new Vertex(i+1));
                }
            }

            // On ajoute une nouvelle arête et/ou nouveau sommet
            else {
                getEdge(lineSplit, vertices);
            }

            // On lis la ligne suivante
            token = scanner.nextLine();
        }

        lineSplit = token.trim().split(" ");              //Split the line
        getEdge(lineSplit, vertices);

        return new Graph(vertices, (int) avgDegree, MAXCSP);
    }

    private static void getEdge(String[] lineSplit, ArrayList<Vertex> vertices) {
        int vertexId = Integer.parseInt(lineSplit[1]);          // premier sommet lus
        int vertexRelatedId = Integer.parseInt(lineSplit[2]);   // second sommet lus
        vertices.get(vertexId-1).addNeighbour(vertices.get(vertexRelatedId-1));
        vertices.get(vertexRelatedId-1).addNeighbour(vertices.get(vertexId-1));
    }
}
