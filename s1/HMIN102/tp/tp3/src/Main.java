package tp3;

public class Main {
    public static void main(String[] args) {
        OrderedDico od = new OrderedDico(2);
//        od.put("couscous", 1);
//        od.put("pates", 500);
//        od.put("poireaux", 20);
//        od.put("brandade", 9001);
//
//        System.out.println(od.get("brandade"));

        FastDico fd = new FastDico(50);
//        fd.put("couscous", 1);
//        fd.put("pates", 500);
//        fd.put("poireaux", 20);
//        fd.put("brandade", 9001);

        SortedDico sd = new SortedDico(3);
        sd.put("poireaux", 25);
        sd.put("couscous", 1);
        sd.put("pates", 20);
        sd.put("brandade", 15);
    }
}
