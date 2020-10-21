package doubleDispatch;

public class Produit {
    private String name;
    private double price;

    public Produit(String name, double location){
        this.name = name;
        this.price = location;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
