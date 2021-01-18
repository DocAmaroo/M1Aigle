import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Directory extends Composants{
    private List<Composants> contenus;

    public Directory() {
        super();
        contenus=new ArrayList<Composants>();
    }
    public Directory(int s, String a) {
        super(s,a);
        contenus=new ArrayList<Composants>();
    }
    public void add(Composants c) {
        contenus.add(c);
    }
    public void ls () {
        String s="";
        for(Composants c : contenus) {
            s=s+c.toString()+" ";
        }
        System.out.println(s);
    }
    public int nbElement() {
        return this.contenus.size();
    }
}
