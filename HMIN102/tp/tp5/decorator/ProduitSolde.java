package decorator;

public class ProduitSolde extends Produit {
    private String name;
    private double price;

    public ProduitSolde(String name, double price) {
        super(name, price);
    }

    @Override
    public double getPrice() {
        return super.getPrice()*0.5;
    }
}
