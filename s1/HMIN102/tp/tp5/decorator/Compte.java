package decorator;

public abstract class Compte {
    private Client cl;

    public Compte() {}

    public Compte(Client cl){
        this.cl = cl;
    }

    public abstract double prixLocation(Produit p);
}
