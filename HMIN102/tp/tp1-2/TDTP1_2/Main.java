package TDTP1_2;

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
        fd.put("couscous", 1);
        fd.put("pates", 500);
        fd.put("poireaux", 20);
        fd.put("brandade", 9001);

        System.out.println(fd.get("poireaux"));
    }
}
