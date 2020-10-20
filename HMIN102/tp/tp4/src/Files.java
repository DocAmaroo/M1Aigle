public class Files extends Feuilles{
    public Files() {
        super();
    }
    public Files(int s, String a, int bs, Directory d, String c) {
        super(s, a, bs, d, c);
    }
    public int nbElement() {
        return this.contenu.length();
    }
}
