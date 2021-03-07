import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.exp;

public class Graph {

    // --- Attributs pour l'algorithme recuit simulé
    private final int MAXCSP;
    private final int TEMPERATURE_MAX;
    private final int MAX_TRY;
    private int tryCounter;
    private final int MAX_NEIGHBOURS_TO_CHANGE;
    private int neighboursToChangeCounter;

    // --- Attribut principals du graphe
    private ArrayList<Vertex> vertices;
    private int avgDegree;
    private int nbColors;
    private int[] colors;

    // --- Sauvegarde de graphe
    private Graph backUp;
    private Graph graphWithMinColors;

    public Graph(int verticesSize, int avgDegree, int MAXCSP) {
        this.vertices = new ArrayList<>();
        this.avgDegree = avgDegree;
        this.colors = new int[verticesSize];
        this.nbColors = 0;
        this.MAXCSP = MAXCSP;
        this.TEMPERATURE_MAX = 1000 * verticesSize;
        this.MAX_TRY = avgDegree;
        this.MAX_NEIGHBOURS_TO_CHANGE = 8 * avgDegree;
    }
    
    public Graph(ArrayList<Vertex> vertices, int avgDegree, int MAXCSP) {
        this.vertices = vertices;
        this.avgDegree = avgDegree;
        this.colors = new int[vertices.size()];
        this.nbColors = 0;
        this.colorGraph();
        this.MAXCSP = MAXCSP;
        this.TEMPERATURE_MAX = 1000 * vertices.size();
        this.MAX_TRY = 64;
        this.MAX_NEIGHBOURS_TO_CHANGE = 8 * avgDegree;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public int getEnergy() {
        return this.getNbColors();
    }

    public ArrayList<Vertex> getVertices() {
        return this.vertices;
    }

    public int getNbColors() {
        return this.nbColors;
    }

    public int[] getColors() {
        return this.colors;
    }

    public void initAllBackUp() {
        this.backUp = new Graph(this.vertices.size(), this.avgDegree, this.MAXCSP);
        for (int i = 0; i < this.vertices.size(); i++) {
            this.backUp.vertices.add(new Vertex(i+1));
        }
        makeBackUp();

        this.graphWithMinColors = new Graph(this.vertices.size(), this.avgDegree, this.MAXCSP);
        for (int i = 0; i < this.vertices.size(); i++) {
            this.graphWithMinColors.vertices.add(new Vertex(i+1));
        }
        setGraphWithMinColors();
    }

    public Graph getBackUp() {
        return this.backUp;
    }

    public void makeBackUp() { this.getBackUp().clone(this); }

    public void loadBackUp() { this.clone(this.getBackUp()); }

    public Graph getGraphWithMinColors() {
        return this.graphWithMinColors;
    }

    public void setGraphWithMinColors() { this.getGraphWithMinColors().clone(this); }

    public void loadGraphWithMinColors() { this.clone(this.getGraphWithMinColors()); }

    public void recuitSimule() {

        // --- Gestion des backups
        initAllBackUp();

        // --- Gestion de l'énergie
        int energy = this.getEnergy();
        int oldEnergy;

        // --- Gestion de la température
        int temperature = TEMPERATURE_MAX;

        // --- Compteurs et variable pour la boucle
        Vertex vertex;
        int color;
        int nbMinFound = 0;
        while (temperature > 0 && this.getNbColors() > this.MAXCSP) {
            vertex = this.getRandomVertex();
            color = this.getRandomColor("allColors", vertex);
            if (color == -1) {
                return;
            }

            // --- On sauvegarde
            oldEnergy = energy;
            this.makeBackUp();

            // --- On vérifie si le/les changements sont plus optimisée,
            // sinon on reprend sur la backup
            this.neighboursToChangeCounter = 0;
            energy = this.getEnergy();
            if (this.changeColor(vertex, color) == -1) {

                System.out.println("\nACTUAL");
                this.display();
                System.out.println(Arrays.toString(this.getColors()));

                System.out.println("\n\nBACKUP");
                this.getBackUp().display();
                System.out.println(Arrays.toString(this.getBackUp().getColors()));

                this.loadBackUp();
            }

            if(!this.keepChange(temperature, energy, oldEnergy)){
                this.loadBackUp();
            }

            temperature--;

            // --- Si on trouve un nouveau graphe minimum
            if (this.getNbColors() < this.graphWithMinColors.getNbColors()) {
                System.out.println("[+]Nouveau minimum trouvée : " + this.getNbColors() + " | n°" + nbMinFound);
                System.out.println("Température actuelle: " + temperature);
                nbMinFound++;
                this.graphWithMinColors.clone(this);
                this.tryCounter = 0;
            }

            // --- Sinon on essaye de continuer
            else {

                // On as déjà MAX_TRY essaie, alors on reprend sur le graphe minimum
                if (this.tryCounter == this.MAX_TRY) {
                    this.loadGraphWithMinColors();
                    this.tryCounter = 0;
                }

                // Sinon on réessaye
                else {
                    this.tryCounter++;
                }
            }
        }

        if (this.getNbColors() > this.graphWithMinColors.getNbColors()) { this.loadGraphWithMinColors(); }
    }

    public Vertex getRandomVertex() {
        int vertexIndex = new Random().nextInt(this.vertices.size());
        return this.vertices.get(vertexIndex);
    }

    private int getRandomColor(String groupOfColorsDescription, Vertex vertex) {
        int[] groupOfColors = this.getGroupOfColors(groupOfColorsDescription, vertex);
        if (groupOfColors == null) {
            return -1;
        }

        return groupOfColors[new Random().nextInt(groupOfColors.length)];
    }

    public int[] getGroupOfColors(String specifiedGroup, Vertex vertex) {
        int arraySize;
        int arrayIndex = 0;
        int vertexColor = -1;

        switch (specifiedGroup) {
            case "existingColors":
                if (vertex != null) {
                    vertexColor = vertex.getColor();
                    arraySize = this.getNbColors() - 1;
                } else {
                    arraySize = this.getNbColors();
                }
                break;
            case "deletedColors":
                arraySize = this.colors.length - this.getNbColors();
                break;
            case "allColors":
                if (vertex == null) {
                    vertexColor = -1;
                    arraySize = this.colors.length;
                } else {
                    vertexColor = vertex.getColor();
                    arraySize = this.colors.length - 1;
                }
                break;
            default:
                return null;
        }
        if (arraySize == 0) {
            return null;
        }
        int[] groupOfColors = new int[arraySize];
        for (int i = 0; i < this.colors.length; i++) {
            if (arrayIndex < groupOfColors.length &&
                    ((specifiedGroup.equals("existingColors") && this.colors[i] > 0 && i != vertexColor)
                            || (specifiedGroup.equals("deletedColors") && this.colors[i] == 0)
                            || (specifiedGroup.equals("allColors") && i != vertexColor))) {
                groupOfColors[arrayIndex] = i;
                arrayIndex++;
            }
        }
        return groupOfColors;
    }

    public int changeColor(Vertex vertex, int color) {
        this.eraseVertexColor(vertex);

        vertex.setColor(color);
        this.colors[color]++;
        if (this.colors[color] == 1) { this.nbColors++; }

        int continueColorChanges = this.adaptNeighbours(vertex);
        if (continueColorChanges == -1) { return -1; }
        return 1;
    }

    private void eraseVertexColor(Vertex vertex) {
        int deletedColor = vertex.getColor();
        vertex.setColor(-1);
        this.colors[deletedColor]--;
        if (this.colors[deletedColor] == 0) {
            this.nbColors--;
        }
    }

    public int adaptNeighbours(Vertex vertex) {
        for (Vertex neighbour : vertex.getNeighbours()) {
            if (vertex.getColor() == neighbour.getColor()) {
                this.neighboursToChangeCounter++;
                if (this.neighboursToChangeCounter++ >= this.MAX_NEIGHBOURS_TO_CHANGE) {
                    this.neighboursToChangeCounter = 0;
                    return -1;
                }

                int color = this.getRandomColor("existingColors", vertex);
                int continueColorChanges = this.changeColor(neighbour, color);
                if (continueColorChanges == -1) {
                    return -1;
                }
            }
        }
        return 1;
    }

    public void clone(Graph graph) {

        int i = 0; for (Vertex cloneVertex : graph.vertices) {
            Vertex vertex = this.vertices.get(i);
            vertex.setId(cloneVertex.getId());
            vertex.setColor(cloneVertex.getColor());
            i++;
        }

        i = 0; for (Vertex cloneVertex : graph.vertices) {
            Vertex vertex = this.vertices.get(i);
            for (Vertex cloneNeighbour : cloneVertex.getNeighbours()) {
                int neighbourId = cloneNeighbour.getId();
                vertex.addNeighbour(this.findVertex(neighbourId));
            }
            i++;
        }
        this.nbColors = graph.getNbColors();
        System.arraycopy(graph.colors, 0, this.colors, 0, graph.colors.length);
    }

    public boolean keepChange(double temperature, double newEnergy, double oldEnergy) {
        double energyVariation = oldEnergy - newEnergy;
        //Si l'énergie a augmenté, on rentre dans cette condition.
        if (energyVariation > 0) {
            double prob = exp((-1 * energyVariation) / (temperature * 0.1)); //Calcul de la probabilité de garder le changement.
            return !(new Random().nextDouble() > prob);
        }
        return true;
    }

    public void display() {
        for (Vertex vertex : this.vertices) {
            vertex.display();
        }
//        for (Vertex mainVertex : this.vertices) {
//            for (Vertex neighbourVertex : mainVertex.getNeighbours()) {
//                if (neighbourVertex.getId() > mainVertex.getId()) {
//                    System.out.println("{source : " + mainVertex.getId() + ", target : " + neighbourVertex.getId() + "},");
//                }
//            }
//        }
    }

    public void colorGraph() {
        int color = 0;
        for (Vertex vertex : this.vertices) {
            vertex.setColor(color);
            this.colors[color] = 1;
            color++;
            this.nbColors++;
        }
    }

    public Vertex findVertex(int vertexId) {
        for (Vertex vertex : this.vertices) {
            if (vertex.getId() == vertexId) {
                return vertex;
            }
        }
        return null;
    }
}
