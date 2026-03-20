import java.io.*;
import java.util.*;
public class DirList {
 public static void HienthiFile() {
 File path = new File(".");
 String[] list;
 System.out.println(path.getAbsolutePath());
 if(args.length == 0)
 list = path.list();
 else
 list = path.list(new DirFilter(args[0]));

for (int i = 0; i < list.length; i++) System.out.println(list[i]);
 }
}