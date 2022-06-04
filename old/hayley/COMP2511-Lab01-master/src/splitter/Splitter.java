package splitter;

import java.util.Scanner;

public class Splitter {

    public static void main(String[] args) {
        System.out.println("Enter a sentence specified by spaces only: ");
        Scanner sc = new Scanner(System.in);  
        
        String str = sc.nextLine();

        String[] split = str.split("\\s+");

        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
        sc.close();
    }
}
