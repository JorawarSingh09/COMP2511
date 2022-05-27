public class StringMethods {
    public static void main(String[] args) {
        String str1 = "hello";
        String str2 = "hi";
        String str3 = "bye";

        System.out.println(str1.length());
        System.out.println(str2.length());
        System.out.println(str3.length());

        str1.concat(" " + str2);
        System.out.println(str1);
        str1 = str1.concat(" " + str2);
        System.out.println(str1);

    }
}