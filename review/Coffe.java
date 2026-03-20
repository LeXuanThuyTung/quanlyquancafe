public class Coffe {
    String Tenmon;
    int gia;
    String Ma;

    Coffe() {
    };

    public Coffe(String t, int g, String m) {
        Tenmon = t;
        gia = g;
        Ma = m;
    }

    public static void test() {
        Coffe myObj = new Coffe("CaPheSua", 25000, "Cf1");
        System.out.println(myObj.Tenmon);
        System.out.println(myObj.gia);
        System.out.println(myObj.Ma);

    }
}