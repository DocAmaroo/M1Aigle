public abstract class Feuilles extends Composants{
    protected int basicSize;
    protected Directory parent;
    protected String contenu;

    public Feuilles() {
        super();
        basicSize=0;
        parent=null;
        contenu="";
    }
    public Feuilles(int s, String a, int bs, Directory dir, String c) {
        super(s, a);
        basicSize=bs;
        parent=dir;
        contenu=c;
    }
    public void cat() {
        System.out.println(contenu);
    }
}
