public class Flower {
    int petalCount = 0;
    String s = new String("null");

    Flower(int petals) {
        petalCount = petals;
    }

    Flower(String ss) {
        s = ss;
    }

    Flower(String s, int petals) {
        this(petals);
        this.s = s;
    }

    Flower() {
        this("hi", 47);
    }

    public static void testFlower() {

        Flower f = new Flower();
        System.out.println("test f.s :: " + f.s);
    }
}