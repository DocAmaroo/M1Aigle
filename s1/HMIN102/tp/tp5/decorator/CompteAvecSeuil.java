package decorator;

public class CompteAvecSeuil extends CompteForfait {
    private int nbLocation;

    public CompteAvecSeuil(Client cl, CompteSimple cpt){
        super(cl, cpt);
    }

    @Override
    public double prixLocation(Produit p) {
        if (this.nbLocation == 2) {
            this.nbLocation = 0;
            return 0;
        } else {
            this.nbLocation++;
            return cpt.prixLocation(p);
        }
    }
}
