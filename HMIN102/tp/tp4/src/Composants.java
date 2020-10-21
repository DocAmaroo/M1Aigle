public abstract class Composants {

    protected int size;
    protected String adresse;

    public Composants() {
        size=0;
        adresse="0X000000";
    }
    public Composants(int s, String a) {
        size=s;
        adresse=a;
    }
    public int size() {
        return this.size;
    }
    public String absoluteAdress() {
        return this.adresse;
    }
}
