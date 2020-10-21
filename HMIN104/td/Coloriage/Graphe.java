import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Définition plus simpliste pour la gestion des Pair
 * Merci Mr. Delahaye <3
 * @param <L>
 * @param <R>
 */
//
//
class Pair<L,R> {

    final L left;
    final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }//Pair

    static <L,R> Pair<L,R> of(L left, R right){
        return new Pair<L,R>(left, right);
    }//of

}//Pair


/**
 * Un sommet est définis par un libellé, une couleur et du nombre de sommet avec qui il est relié (degrée)
 */
class Sommet{
    String name;
    int color;
    int degree;

    public Sommet(String name){
        this.name = name;
        this.color = 0;
        this.degree = 0;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getDegree() {
        return degree;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}//Sommet


/**
 * Un graphe est un ensemble de sommet qui sont relié ou non
 */
class Graphe{
    ArrayList<Sommet> vertices;
    ArrayList<Pair<Sommet,Sommet>> inter;
    ArrayList<Pair<Sommet,Sommet>> pref;
    ArrayList<Pair<Sommet,Sommet>> edges = new ArrayList<>();
    int k;

    public Graphe(ArrayList<Sommet> vertices, ArrayList<Pair<Sommet,Sommet>> inter,ArrayList<Pair<Sommet,Sommet>> pref,int k){
        this.vertices = vertices;
        this.inter = inter;
        this.pref = pref;
        this.k = k;
        this.initEdges();
        this.initDegree(this.inter, this.vertices);
    }

    /**
     * initialise un tableau avec TOUTES les arêtes
     */
    void initEdges() {
        for(Pair<Sommet, Sommet> p : pref){ this.edges.add(p); }
        for(Pair<Sommet, Sommet> i : inter){ this.edges.add(i); }
    }

    /**
     * initialise les degrées de chaque sommet
     * @param edges
     * @param vertices
     */
    void initDegree(ArrayList<Pair<Sommet, Sommet>> edges, ArrayList<Sommet> vertices) {
        for (Sommet s : vertices) {
            for (Pair<Sommet, Sommet> edge : edges) {
                if (edge.left.name == s.getName() || edge.right.name == s.getName()) {
                    s.setDegree(s.getDegree()+1);
                }
            }
        }
    }

    /**
     * Renvoie une liste de sommet privé du sommet donné en paramètre
     * @param vertices
     * @param sommet
     * @return
     */
    ArrayList<Sommet> removeSommet(ArrayList<Sommet> vertices, Sommet sommet) {
        if (vertices.contains(sommet)) { vertices.remove(vertices.indexOf(sommet)); }
        return vertices;
    }

    /**
     * Renvoie une liste d'arête privé de celle étant relié au sommet donné en paramètre
     * @param edges
     * @param sommet
     * @return
     */
    ArrayList<Pair<Sommet,Sommet>> removeEdges(ArrayList<Pair<Sommet,Sommet>> edges, Sommet sommet) {
        ArrayList<Pair<Sommet,Sommet>> result = new ArrayList<>();
        for (Pair<Sommet,Sommet> edge : edges){
            if ( edge.left.name != sommet.name && edge.right.name != sommet.name ) { result.add(edge); }
            else if ( edge.left.name != sommet.name && edge.right.name == sommet.name) { edge.left.setDegree(edge.left.getDegree()-1); }
            else { edge.right.setDegree(edge.right.getDegree()-1); }
        }

        return result;
    }

    /**
     * retourne le sommet avec le degrée le plus faible et < k
     * @param vertices
     * @return
     */
    Sommet getSommetDegreeMin(ArrayList<Sommet> vertices){
        int min=k;
        Sommet result = vertices.get(0);
        for (Sommet s : vertices){
            if (min >= s.getDegree() && s.getDegree() < this.k){
                min = s.getDegree();
                result = s;
            }
        }
        if (min == k){
            return null;
        } else {
            return result;
        }
    }

    /**
     * retourne le sommet avec le degrée le plus élevée
     * @param vertices
     * @return
     */
    Sommet getSommetDegreeMax(ArrayList<Sommet> vertices){
        int max=vertices.get(0).getDegree();
        for (Sommet s : vertices){
            if (max < s.getDegree()){
                max = s.getDegree();
            }
        }
        return vertices.get(max);
    }

    /**
     * Si le sommet donné en paramètre fait partie des sommet de préférences
     * il renvoie tout les sommet, eux aussi de préférence, avec lesquelles il est lié
     * @param sommet
     * @return
     */
    ArrayList<Sommet> asPref(Sommet sommet) {
        ArrayList<Sommet> res = new ArrayList<>();
        for (Pair<Sommet,Sommet> p : this.pref) {
            if (p.left.name == sommet.getName()) { res.add(p.right); }
            if (p.right.name == sommet.getName()) { res.add(p.left); }
        }
        return res;
    }

    /**
     * retourne les sommets avec lequel le sommet en paramètre est lier
     * @param sommet
     * @return
     */
    ArrayList<Sommet> getNeighbours(Sommet sommet){
        ArrayList<Sommet> result = new ArrayList<>();
        for (Pair<Sommet,Sommet> p : this.edges) {
            if (sommet == p.left) {
                result.add(p.right);
            }
            if (sommet == p.right) {
                result.add(p.left);
            }
        }

        return result;
    }

    /**
     * permet de retourner ou non une nouvelle couleur en fonction des couleurs déjà vues
     * @param colors
     * @return
     */
    int getNewColor(ArrayList<Integer> colors) {
        for (int i=1; i <= k; i++){
            if (!colors.contains(i)){
                return i;
            }
        }
        return -1;
    }

    /**
     * permet de colorier le graphe
     */
    void initColor() {

        // recopie verticies
        ArrayList<Sommet> verticescpy = new ArrayList<>();
        for(Sommet s : this.vertices) { verticescpy.add(s); }

        // recopies interferences
        ArrayList<Pair<Sommet,Sommet>> edgescpy = new ArrayList<>();
        for(Pair<Sommet,Sommet> p : this.inter) { edgescpy.add(p); }

        // steps permettra de conserver à chaque décomposition du graphe
        // de conserver à chaque étape le noeud qui à été supprimé/spill.
        // Permettant ainsi de refaire l'arbre en marche arrière
        ArrayList<Sommet> steps = new ArrayList<>();

        //on déconstruit l'arbre
        while (verticescpy.size() > 1){
            Sommet sommet = getSommetDegreeMin(verticescpy);
            if (sommet == null) {


                sommet = getSommetDegreeMax(verticescpy);
                sommet.setColor(-1);

                // ajoute une étape
                steps.add(sommet);

                // supprime le sommet et les arêtes
                verticescpy = removeSommet(verticescpy, sommet);
                edgescpy = removeEdges(edgescpy, sommet);
            }
            else {
                // ajoute le sommet dans une pile maison
                steps.add(sommet);

                // supprime le sommet et les arêtes
                verticescpy = removeSommet(verticescpy, sommet);
                edgescpy = removeEdges(edgescpy, sommet);
            }

            // Debug
            //this.printDegree(verticescpy);
            //this.printEdges(edgescpy);
        }


        // set couleur du dernier sommet
        Sommet sommet = verticescpy.get(0);
        sommet.setColor(1);

        // si sommet préférence => pour chacun des sommets avec qui il est on associe la même couleur
        if (asPref(sommet).size() != 0) {
            for (Sommet s : asPref(sommet)){
                s.setColor(sommet.getColor());
            }
        }


        //on recontruit l'arbre
        for (int i=steps.size()-1; i > -1; i--) {

            sommet = steps.get(i);

            // si a été spillé
            if (sommet.getColor() == -1){
                //Debug
                //System.out.println("Ce sommet a été spill");
            }

            // si aucune couleur définis
            if (sommet.getColor() >= 0) {

                // Si notre sommet est déjà colorié est qu'il est dans les préférences
                if (sommet.getColor() > 0 && asPref(sommet) != null){

                    // on récupère les couleurs de chaques sommets lié au sommet traité sauf ceux de préférences
                    ArrayList<Integer> viewColors = new ArrayList<>();
                    for (Sommet s : this.getNeighbours(sommet)) {
                        if (s.getColor() > 0 && !asPref(sommet).contains(s)) {
                            viewColors.add(s.getColor());
                        }
                    }

                    sommet.setColor(this.getNewColor(viewColors));
                } else {

                    // on récupère les couleurs de chaques sommets du sommet traité
                    ArrayList<Integer> viewColors = new ArrayList<>();
                    for (Sommet s : this.getNeighbours(sommet)) {
                        if (s.getColor() > 0) {
                            viewColors.add(s.getColor());
                        }
                    }

                    // on donne une nouvelle couleur possible à notre sommet
                    sommet.setColor(this.getNewColor(viewColors));

                    // si sommet préférence, on donne la même couleur sommet avec qui il est relié
                    if (asPref(sommet).size() != 0) {
                        for (Sommet s : asPref(sommet)) {
                            s.setColor(sommet.getColor());
                        }
                    }
                }
            }

            //on rajoute le sommet
            verticescpy.add(sommet);

            //DEBUG
            //this.printColor();
        }

        // Fin du traitement on affiche le résultat
        System.out.println("\nLe coloriage est terminé...");
    }


    /**
     * Affiche le degrée de chaque sommet du graphe
     * @param vertices
     */
    void printDegree(ArrayList<Sommet> vertices) {
        System.out.print("deg -> "); for (Sommet s : vertices){ System.out.print(s.getName() + ":" + s.getDegree() + " "); } System.out.println("");
    }

    /**
     * Affiche les arêtes du graphe
     * @param edges
     */
    void printEdges(ArrayList<Pair<Sommet,Sommet>> edges) {
        System.out.print("Edges -> ");
        for (Pair<Sommet,Sommet> p : edges) { System.out.print("(" + p.left.name + "," + p.right.name + ") "); }
        System.out.println("");
    }

    /**
     * Affiche les couleurs associés à chaque sommet
     */
    void printColor() {
        System.out.print("color -> "); for (Sommet s : this.vertices){ System.out.print(s.getName() + ":" + s.getColor() + " "); } System.out.println("");
    }

}//Graphe

class Main {
    public static void main(String[] args) {

        // Création des sommets
        Sommet u = new Sommet("u");
        Sommet v = new Sommet("v");
        Sommet t = new Sommet("t");
        Sommet x = new Sommet("x");
        Sommet y = new Sommet("y");
        Sommet z = new Sommet("z");

        // Ajout des sommets dans un tableau
        ArrayList<Sommet> vertices = new ArrayList<Sommet>();
        vertices.add(u);
        vertices.add(v);
        vertices.add(t);
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);

        // Création des arêtes d'interférences
        ArrayList<Pair<Sommet,Sommet>> inter = new ArrayList<Pair<Sommet,Sommet>>();
        inter.add(new Pair(u,x));
        inter.add(new Pair(u,y));
        inter.add(new Pair(v,x));
        inter.add(new Pair(v,z));
        inter.add(new Pair(v,t));
        inter.add(new Pair(y,x));
        inter.add(new Pair(y,t));

        // Création des arêtes de préférences
        ArrayList<Pair<Sommet,Sommet>> pref = new ArrayList<Pair<Sommet,Sommet>>();
        pref.add(new Pair(u,t));

        // Création du graphe
        Graphe g = new Graphe(vertices,inter,pref, Integer.parseInt(args[0]));

        // Colorisation du graphe
        g.initColor();

        // Affichage des couleurs
        g.printColor();
    }
}
