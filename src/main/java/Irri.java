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
        String[] tasks = new String[100];
        int taskCount = 0;
        while(true){
            String input = in.nextLine();
            if (input.equalsIgnoreCase("bye")){
                System.out.println("____________________________________________________________");
                System.out.println("Bye. See you!");
                System.out.println("____________________________________________________________");
                break;
            }
            if (input.equalsIgnoreCase("list")){
                System.out.println("____________________________________________________________");
                if (taskCount == 0){
                    System.out.println("No task added yet.");
                }
                else{
                    for (int  i=0; i<taskCount; i++){
                        System.out.println(" " + (i+1) +". " + tasks[i]);
                    }
                }
                System.out.println("____________________________________________________________");
            }
            else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        in.close();
    }
}
