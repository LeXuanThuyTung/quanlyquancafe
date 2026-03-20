public class testCollection {
    public  static void test(){
        NameNumber n1 = new NameNumber("ascadasnull", "123");
        NameNumber n2 = new NameNumber("ascadasnulasdasl", "12546643");
        NNCollection nn = new NNCollection();
        nn.insert(n1);
        nn.insert(n2);
        nn.hienthi();
    }

}