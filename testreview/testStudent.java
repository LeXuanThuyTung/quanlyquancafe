import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class testStudent {
    public static void test() {
        Student s1 = new Student("fred" , 3.0F);
        Student s2 = new Student("Sam", 3.5F);
        Student s3 = new Student("Steve", 2.1F);
        if(s3.compareTo((Object)s2)<0)
        System.out.println(s3.getName()+ "has a lower gpa than" +s2.getName());
        Set studentset = new TreeSet();
        studentset.add(s1); studentset.add(s2); studentset.add(s3);
        Iterator i = studentset.iterator();
        while(i.hasNext())System.out.println(((Student)i.next()).getName());
    }
}