import java.io.FileInputStream;
    import java.io.File;
public class ReadFile {
public static void DocFile() {
File f = new File("final/src/test.txt");
try {
FileInputStream fis = new FileInputStream(f);
int c;
while ((c = fis.read()) != -1) {
System.out.println((char) c);
}
} catch (Exception e) {
e.printStackTrace();
}
}
}