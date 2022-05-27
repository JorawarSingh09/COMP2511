public class UserDefinedMethods {
    public static int testPrint(String name) {
        System.out.println("hello");
        System.out.println(name);

        return 2;
    }

    public static void main(String[] args) {
        if (testPrint("jora") == 2) {
            System.out.println("hi");

        }
    }
}
