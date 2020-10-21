package doubleDispatch;

public class CompteAvecSeuil extends Compte {
    private int nbLocation;

    public CompteAvecSeuil(Client cl){
        super(cl);
    }

    @Override
    public double prixLocation(Produit p) {
        if (this.nbLocation == 2) {
            this.nbLocation = 0;
            return 0;
        } else {
            this.nbLocation++;
            return super.prixLocation(p);
        }
    }
}
