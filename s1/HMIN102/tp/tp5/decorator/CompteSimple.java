package decorator;

public class CompteSimple extends Compte {

    public CompteSimple() {}

    public CompteSimple(Client cl){
        super(cl);
    }

    public double prixLocation(Produit p){
        return p.getPrice();
    }
}
