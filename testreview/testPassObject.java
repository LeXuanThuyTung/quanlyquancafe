
public class testPassObject {
    public static void myTest() {

        Number n = new Number();

        n.i = 14;

        PassObject.f(n);

        System.out.println(n.i);

        // what is n.i now? 15
    }
}
    