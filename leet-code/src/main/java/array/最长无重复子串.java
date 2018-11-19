package array;

/**
 * Created by wgp on 2018/11/18.
 */
public class 最长无重复子串 {

    public static void main(String[] args) {
//        String a = "3443333332";
//        int index = a.lastIndexOf("3");
//        String b = a.substring(index);
//        System.out.println(b);

        String a = new 最长无重复子串().test("43567c89xx0sdftyxtyxy");
        System.out.println(a);
    }

    public String test(String str){
        char[] chars = str.toCharArray();
        String result = String.valueOf(chars[0]);
        String temp = "";
        for (char c:chars) {
            int index = temp.lastIndexOf(c);
            if(index > -1){
                temp = temp.substring(index +1) + String.valueOf(c);
            }else{
                temp = temp + String.valueOf(c);
            }
            if(result.length()< temp.length()){
                result = temp;
            }
        }


        return result;
    }
}
