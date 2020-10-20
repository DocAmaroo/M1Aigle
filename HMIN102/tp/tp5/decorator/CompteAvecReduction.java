package decorator;

public class CompteAvecReduction extends CompteForfait {

    public CompteAvecReduction(Client cl, CompteSimple cpt){
        super(cl, cpt);
    }

    @Override
    public double prixLocation(Produit p) {
        return cpt.prixLocation(p)*0.9;
    }
}
