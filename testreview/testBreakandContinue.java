
public class testBreakandContinue {
    public static void test(){
        for(int i = 0; i<100;i++){
            if(i == 82 )break;
            if(i % 5 != 0 )continue;
            System.out.println(i);
        }
    }
}