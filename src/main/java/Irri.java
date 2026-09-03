import task.*;

import java.util.Scanner;

public class Irri {
    private static final String LINE = "____________________________________________________________";
    private static final String BANNER =
            " ___           _ \n"
                    + "|_ _|_ __ _ __(_)\n"
                    + " | || '__| '__| |\n"
                    + " | || |  | |  | |\n"
                    + "|___|_|  |_|  |_|\n";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Hi! I'm Irri.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
        Scanner inputScanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        while(true){
            String input = inputScanner.nextLine();
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
                } else{
                    System.out.println("Here are the tasks in your list:");
                    for (int i=0; i<taskCount; i++){
                        System.out.println(" " + (i+1) +". " + tasks[i]);
                    }
                }
                System.out.println(LINE);
                continue;
            }

            if (input.toLowerCase().startsWith("todo ")){
                String description = input.substring(5);
                tasks[taskCount] = new ToDo(description);
                taskCount++;
                System.out.println(LINE);
                System.out.println(" Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(LINE);
                continue;
            }

            if (input.toLowerCase().startsWith("deadline ")){
                String rest = input.substring(9);
                int byIndex = rest.indexOf(" /by ");
                if (byIndex != -1){
                    String description = rest.substring(0, byIndex);
                    String by = rest.substring(byIndex + 5);
                    tasks[taskCount] = new Deadline(description, by);
                    taskCount++;
                    System.out.println(LINE);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println(LINE);
                } else {
                    System.out.println(LINE);
                    System.out.println(" Invalid deadline format. Use: deadline <description> /by <date/time>");
                    System.out.println(LINE);
                }
                continue;
            }

            if (input.toLowerCase().startsWith("event ")){
                String rest = input.substring(6);
                int fromIndex = rest.indexOf(" /from ");
                int toIndex = rest.indexOf(" /to ");
                if (fromIndex != -1 && toIndex != -1 && fromIndex < toIndex){
                    String description = rest.substring(0, fromIndex);
                    String from = rest.substring(fromIndex + 6, toIndex);
                    String to = rest.substring(toIndex + 5);
                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;
                    System.out.println(LINE);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println(LINE);
                } else {
                    System.out.println(LINE);
                    System.out.println(" Invalid deadline format. Use: deadline <description> /by <date/time>");
                    System.out.println(LINE);
                }
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
                    } else {
                        System.out.println(LINE);
                        System.out.println(" Invalid task number. Please enter a number between 1 and " + taskCount);
                        System.out.println(LINE);
                    }
                } catch (NumberFormatException e) {
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
                    } else {
                        System.out.println(LINE);
                        System.out.println(" Invalid task number. Please enter a number between 1 and " + taskCount);
                        System.out.println(LINE);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(LINE);
                    System.out.println(" Please enter a valid task number (e.g., unmark 2)");
                    System.out.println(LINE);
                }
                continue;
            }

            System.out.println(LINE);
            System.out.println(" Unknown command. Available commands:");
            System.out.println("   todo <description>");
            System.out.println("   deadline <description> /by <date>");
            System.out.println("   event <description> /from <start> /to <end>");
            System.out.println("   list");
            System.out.println("   mark <number>");
            System.out.println("   unmark <number>");
            System.out.println("   bye");
            System.out.println(LINE);
        }
        inputScanner.close();
    }
}
