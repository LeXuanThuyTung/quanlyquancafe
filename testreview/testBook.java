import java.util.List;
import java.util.ArrayList;
public class testBook {
    public static void test(){
    Book b1 = new Book(1,"test1");
    Book b2 = new Book(2,"test2");
    List<Book> ds = new ArrayList<>();
    ds.add(b1);
    ds.add(b2);
    Library lib = new Library(ds);
    for(Book b : lib.getBooks()){
      System.out.println(b.getId() + " " + b.getTen());
    }
    }
}