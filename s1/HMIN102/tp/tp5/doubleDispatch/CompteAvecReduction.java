package doubleDispatch;

public class CompteAvecReduction extends Compte {

    public CompteAvecReduction(Client cl){
        super(cl);
    }

    @Override
    public double prixLocation(Produit p) {
        return super.prixLocation(p)*0.9;
    }
}
