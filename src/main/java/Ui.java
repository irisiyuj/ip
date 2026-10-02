import java.util.ArrayList;
import java.util.Scanner;
import task.Task;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String BANNER =
            " ___           _ \n"
                    + "|_ _|_ __ _ __(_)\n"
                    + " | || '__| '__| |\n"
                    + " | || |  | |  | |\n"
                    + "|___|_|  |_|  |_|\n";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void printWelcome() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Hi! I'm Irri.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public void printGoodbye() {
        System.out.println(LINE);
        System.out.println("Bye. See you!");
        System.out.println(LINE);
    }

    public void printError(String message) {
        System.out.println(LINE);
        System.out.println("Oh no! " + message);
        System.out.println(LINE);
    }

    public void printTaskList(ArrayList<Task> tasks) {
        System.out.println(LINE);
        if (tasks.isEmpty()) {
            System.out.println("No task added yet.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println(LINE);
    }

    public void printTaskAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    public void printTaskDeleted(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println(" OK. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    public void printTaskMarked(Task task) {
        System.out.println(LINE);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        System.out.println(LINE);
    }

    public void printTaskUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        System.out.println(LINE);
    }

    public void printLoadingError() {
        System.out.println(LINE);
        System.out.println(" Warning: Unable to load tasks from file. Starting with an empty list.");
        System.out.println(LINE);
    }

    public void close() {
        scanner.close();
    }
}
