package doubleDispatch;

public class Main {

    public static void main(String[] args) {
        System.out.println("-- doubleDispatch --");
        Produit lgv = new Produit("La grande vadrouille", 10.0);
        Client cl = new Client("Dupont");
        Compte cmt = new Compte(cl);
        cmt.prixLocation(lgv);

        Compte cmt2 = new CompteAvecReduction(cl);
        System.out.println("CompteReduction : " + cmt2.prixLocation(lgv));
        Compte cmt3 = new CompteAvecSeuil(cl); // le seuil est `a 2 par d´efaut
        System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv)); // doit afficher 0
        Produit r4 = new ProduitSolde("RockyIV", 10.0);
        System.out.println("CompteNormal+normal.ProduitSolde : " + cmt.prixLocation(r4));
        System.out.println("CompteReduction+normal.ProduitSolde : " + cmt2.prixLocation(r4));}

}


