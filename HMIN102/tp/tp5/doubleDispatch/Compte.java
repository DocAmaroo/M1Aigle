package doubleDispatch;

public class Compte {
    private Client cl;

    public Compte() {}

    public Compte(Client cl){
        this.cl = cl;
    }

    public double prixLocation(Produit p){
        return p.getPrice();
    }
}
