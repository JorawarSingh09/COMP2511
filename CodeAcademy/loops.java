public class loops {
    public static void main(String[] args) {
        System.out.println("While loop");
        // while loop excecutes after a condition has been met
        int num = 0;
        while (num < 5) {
            System.out.println("I am in a while loop " + num);
            num++;
        }

        // do-while loop excecutes an action before checking if a
        // condition has been met
        num = 0;
        do {
            num++;
            System.out.println("In a do-while loop");
        } while (num < 5);

        // for loop
        for (int i = 0; i < 5; i++) {
            System.out.println("I am in a for loop");
        }

        // Loops with arrays
        String[] cars = { "honda", "mazda", "lexus" };
        for (String car : cars) {
            System.out.println(car);
        }

        System.out.println("this is a break");
        for (int i = 0; i < 6; i++) {
            System.out.println(i);
            if (i == 2) {
                break;
            }
        }
        System.out.println("this is a continue");
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                continue;
            }
            System.out.println(i);
        }
    }
}