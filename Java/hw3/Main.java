import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    try {
      MyScanner mscn = new MyScanner(System.in);
      try {
        System.out.println(mscn.nextInt());
      } finally {
        mscn.close();
      }
    } catch (IOException err) {
      System.out.println(err.getMessage());
    }
  }
}
