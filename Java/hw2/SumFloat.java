public class SumFloat {
  public static void main(String[] args) {
    float ans = 0;
    for (String str : args) {
      str += " ";
      int start = -1;
      int end = -1;
      for (int i = 0; i < str.length(); i++) {
        if (Character.isWhitespace(str.charAt(i))) {
          end = i;
          if (start >= 0) {
            ans += Float.parseFloat(str.substring(start, end));
          }
        //System.out.println(str + "  " + ans + " " + start + " " + end);
          start = -1; 
          end = -1;
        } else {
          if (start < 0) {
            start = i;
          }
        }
      }
    }
    System.out.println(ans);
  }
}