public class TestCellPhone {
    public static void test() {
        CellPhone noiseMaker = new CellPhone();
        ObnoxiousTune ot = new ObnoxiousTune(); 
        noiseMaker.ring(ot);
    }
}