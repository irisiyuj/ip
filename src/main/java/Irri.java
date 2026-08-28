import java.util.Scanner;
public class Irri {
    private static final String LINE = "____________________________________________________________";
    private static final String banner =
            " ___           _ \n"
                    + "|_ _|_ __ _ __(_)\n"
                    + " | || '__| '__| |\n"
                    + " | || |  | |  | |\n"
                    + "|___|_|  |_|  |_|\n";

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println(banner);
        System.out.println("Hi! I'm Irri.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;
        while(true){
            String input = in.nextLine();
            if (input.equalsIgnoreCase("bye")){
                System.out.println(LINE);
                System.out.println("Bye. See you!");
                System.out.println(LINE);
                break;
            }
            if (input.equalsIgnoreCase("list")){
                System.out.println(LINE);
                if (taskCount == 0){
                    System.out.println("No task added yet.");
                }
                else{
                    System.out.println("Here are the tasks in your list:");
                    for (int i=0; i<taskCount; i++){
                        System.out.println(" " + (i+1) +". " + tasks[i]);
                    }
                }
                System.out.println(LINE);
                continue;
            }
            if (input.toLowerCase().startsWith("mark ")){
                try {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsDone();
                        System.out.println(LINE);
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[index]);
                        System.out.println(LINE);
                    }
                    else {
                        System.out.println(LINE);
                        System.out.println(" Invalid task number. Please enter a number between 1 and " + taskCount);
                        System.out.println(LINE);
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println(LINE);
                    System.out.println(" Please enter a valid task number (e.g., mark 2)");
                    System.out.println(LINE);
                }
                continue;
            }

            if (input.toLowerCase().startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsNotDone();
                        System.out.println(LINE);
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[index]);
                        System.out.println(LINE);
                    }
                    else {
                        System.out.println(LINE);
                        System.out.println(" Invalid task number. Please enter a number between 1 and " + taskCount);
                        System.out.println(LINE);
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println(LINE);
                    System.out.println(" Please enter a valid task number (e.g., unmark 2)");
                    System.out.println(LINE);
                }
                continue;
            }
            tasks[taskCount] = new Task(input);
            taskCount++;
            System.out.println(LINE);
            System.out.println("added: " + input);
            System.out.println(LINE);
        }
        in.close();
    }
}
