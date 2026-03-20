public class Outertest {
    public static void test() {
        OuterClassTest oct = new OuterClassTest();
        OuterClassTest.InnerClassTest ict = oct.new InnerClassTest();
        System.out.println(ict);
    }
}