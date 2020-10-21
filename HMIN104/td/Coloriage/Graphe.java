import java.util.ArrayList;

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

class Sommet{
    String name;
    int couleur;

    public Sommet(String name, int couleur){
        this.name = name;
        this.couleur = couleur;
    }

    public String getName() {
        return name;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public void print() {
        System.out.println("Sommet " + this.getName() + "\ncolor(" + this.getCouleur() + ")" );
    }
}//Sommet

class Graphe{
    ArrayList<Sommet> vertices = new ArrayList<Sommet>();
    ArrayList<Pair<Sommet,Sommet>> inter = new ArrayList<Pair<Sommet,Sommet>>();
    ArrayList<Pair<Sommet,Sommet>> pref = new ArrayList<Pair<Sommet,Sommet>>();
    int k;

    public Graphe(ArrayList<Sommet> vertices, ArrayList<Pair<Sommet,Sommet>> inter,ArrayList<Pair<Sommet,Sommet>> pref,int k){
        this.vertices = vertices;
        this.inter = inter;
        this.pref = pref;
        this.k = k;
    }

    void print(){
        System.out.println("Interference :");
        for (Pair<Sommet,Sommet> i : this.inter) {
            System.out.print("(" + i.left.name + "," + i.right.name + ") ");
        }
        System.out.println("\nPreference :");
        for (Pair<Sommet,Sommet> p : this.pref) {
            System.out.print("(" + p.left.name + "," + p.right.name + ") ");
        }
    }

}//Graphe

class Main{
    public static void main(String[] args) {
        Sommet u = new Sommet("u",2);
        Sommet v = new Sommet("v",3);
        Sommet t = new Sommet("t",2);
        Sommet x = new Sommet("x",3);
        Sommet y = new Sommet("y",3);
        Sommet z = new Sommet("z",1);
        ArrayList<Sommet> vertices = new ArrayList<Sommet>();
        vertices.add(u);
        vertices.add(v);
        vertices.add(t);
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);

        ArrayList<Pair<Sommet,Sommet>> inter = new ArrayList<Pair<Sommet,Sommet>>();
        inter.add(new Pair(u,x));
        inter.add(new Pair(u,y));
        inter.add(new Pair(v,x));
        inter.add(new Pair(v,z));
        inter.add(new Pair(v,t));
        inter.add(new Pair(y,x));
        inter.add(new Pair(y,t));

        ArrayList<Pair<Sommet,Sommet>> pref = new ArrayList<Pair<Sommet,Sommet>>();
        pref.add(new Pair(u,t));


        Graphe g = new Graphe(vertices,inter,pref,3);

        g.print();
    }
}