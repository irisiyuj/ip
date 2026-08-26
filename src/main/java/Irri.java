import java.util.Scanner;
public class Irri {
    public static void main(String[] args) {
        String banner =
                " ___           _ \n"
                        + "|_ _|_ __ _ __(_)\n"
                        + " | || '__| '__| |\n"
                        + " | || |  | |  | |\n"
                        + "|___|_|  |_|  |_|\n";


        System.out.println("____________________________________________________________");
        System.out.println(banner);
        System.out.println("Hi! I'm Irri.");
        System.out.println("What's on your mind?");
        System.out.println("____________________________________________________________");

        Scanner in = new Scanner(System.in);
        while(true){
            String input = in.nextLine();
            if (input.equalsIgnoreCase("bye")){
                System.out.println("____________________________________________________________");
                System.out.println("Bye. See you!");
                System.out.println("____________________________________________________________");
                break;
            }
            System.out.println("____________________________________________________________");
            System.out.println(": " + input);
            System.out.println("____________________________________________________________");
        }
        in.close();
    }
}
