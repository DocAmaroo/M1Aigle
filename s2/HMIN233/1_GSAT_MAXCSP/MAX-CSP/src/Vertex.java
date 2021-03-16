import java.util.ArrayList;
import java.util.List;

/**
 * Sommet d'un graphe
 */
public class Vertex {
    private int id;
    private int color;
    private List<Vertex> neighbours;

    public Vertex(int id) {
        this.id = id;
        this.color = -1;
        this.neighbours = new ArrayList<>();
    }

    // --- Id Methods
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }
    // --- Color methods
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    // --- Neighbors methods
    public List<Vertex> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Vertex> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Vertex vertex) {
        if (!this.neighbours.contains(vertex)) {
            this.neighbours.add(vertex);
        }
    }

    public boolean isNeighbour(int vertexId) {
        for (Vertex neighbor : this.neighbours) {
            if (vertexId == neighbor.getId()) {
                return true;
            }
        }
        return false;
    }

    public void display() {
        System.out.println("{id :" + this.getId() + ", color: " + this.getColor() + "},");
        System.out.println(this.neighbours);
    }
}
