package decorator;

public abstract class CompteForfait extends CompteSimple {
    protected CompteSimple cpt;

    protected CompteForfait(Client cl, CompteSimple cpt) {
        super(cl);
        this.cpt = cpt;
    }

}
