package decorator;

public class Main {

    public static void main(String[] args) {
        System.out.println("-- decorator --");
        Produit lgv = new Produit("La grande vadrouille", 10.0);
        Client cl = new Client("Dupont");
        CompteSimple cmt = new CompteSimple(cl);
        cmt.prixLocation(lgv);
        CompteSimple cmt2 = new CompteAvecReduction(cl, cmt);
        System.out.println("CompteReduction : " + cmt2.prixLocation(lgv));
        CompteSimple cmt3 = new CompteAvecSeuil(cl, cmt); // le seuil est à 2 par défaut
        System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv)); // doit afficher 0
        Produit r4 = new ProduitSolde("RockyIV", 10.0);
        System.out.println("CompteNormal+ProduitSoldé : " + cmt.prixLocation(r4));
        System.out.println("CompteReduction+ProduitSoldé : " + cmt2.prixLocation(r4));
    }
}


