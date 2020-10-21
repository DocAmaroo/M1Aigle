public class Main {

    public static void main(String args[]) {
        Directory racine = new Directory(4, "0X001");
        Directory d1 = new Directory(7, "0X002");
        Directory d2 = new Directory(5, "0X003");
        Directory d3 = new Directory(9, "0X004");

        Files f1= new Files(12, "0X0001", 2, d1, "Bonjour");
        Files f2= new Files(21, "0X0002", 4, d2, "Hello");
        Files f3= new Files(34, "0X0003", 6, d3, "Jeej");

        racine.add(d1); racine.add(d2); racine.add(d3);
        d1.add(f1);
        d2.add(f2);
        d3.add(f3);

        Links l1=new Links(42, "0X00001", 5, d1, "f1");
        Links l2=new Links(85, "0X00002", 4, d2, "f2");
        Links l3=new Links(75, "0X00003", 9, d3, "f3");

        d1.add(l1);
        d2.add(l2);
        d3.add(l3);
    }
}
