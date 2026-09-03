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
        Scanner inputScanner = new Scanner(System.in);
        printWelcome();
        runCommandLoop(inputScanner);
        printGoodbye();
        inputScanner.close();
    }

    private static void runCommandLoop(Scanner inputScanner) {
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        boolean isRunning = true;
        while (isRunning) {
            String input = inputScanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                isRunning = false;
                continue;
            }
            if (input.equalsIgnoreCase("list")) {
                printTaskList(tasks, taskCount);
                continue;
            }
            if (input.toLowerCase().startsWith("todo ")) {
                taskCount = handleTodo(input, tasks, taskCount);
                continue;
            }
            if (input.toLowerCase().startsWith("deadline ")) {
                taskCount = handleDeadline(input, tasks, taskCount);
                continue;
            }
            if (input.toLowerCase().startsWith("event ")) {
                taskCount = handleEvent(input, tasks, taskCount);
                continue;
            }
            if (input.toLowerCase().startsWith("mark ")) {
                handleMark(input, tasks, taskCount);
                continue;
            }
            if (input.toLowerCase().startsWith("unmark ")) {
                handleUnmark(input, tasks, taskCount);
                continue;
            }
            printUnknownCommand();
        }
    }

    private static void printWelcome() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Hi! I'm Irri.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    private static void printGoodbye(){
        System.out.println(LINE);
        System.out.println("Bye. See you!");
        System.out.println(LINE);
    }

    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println(LINE);
        if (taskCount == 0) {
            System.out.println("No task added yet.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println(" " + (i + 1) + ". " + tasks[i]);
            }
        }
        System.out.println(LINE);
    }

    private static int handleTodo(String input, Task[] tasks, int taskCount) {
        String description = input.substring(5);
        tasks[taskCount] = new ToDo(description);
        taskCount++;
        printAddConfirmation(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleDeadline(String input, Task[] tasks, int taskCount) {
        String rest = input.substring(9);
        int byIndex = rest.indexOf(" /by ");
        if (byIndex == -1) {
            printInvalidFormat("deadline <description> /by <date/time>");
            return taskCount;
        }
        String description = rest.substring(0, byIndex);
        String by = rest.substring(byIndex + 5);
        tasks[taskCount] = new Deadline(description, by);
        taskCount++;
        printAddConfirmation(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleEvent(String input, Task[] tasks, int taskCount) {
        String rest = input.substring(6);
        int fromIndex = rest.indexOf(" /from ");
        int toIndex = rest.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
            printInvalidFormat("event <description> /from <start> /to <end>");
            return taskCount;
        }
        String description = rest.substring(0, fromIndex);
        String from = rest.substring(fromIndex + 6, toIndex);
        String to = rest.substring(toIndex + 5);
        tasks[taskCount] = new Event(description, from, to);
        taskCount++;
        printAddConfirmation(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static void handleMark(String input, Task[] tasks, int taskCount){
        try {
            int index = Integer.parseInt(input.substring(5)) - 1;
            if (index >= 0 && index < taskCount) {
                tasks[index].markAsDone();
                System.out.println(LINE);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[index]);
                System.out.println(LINE);
            } else {
                printInvalidTaskNumber(taskCount);
            }
        } catch (NumberFormatException e) {
            printInvalidNumberFormat("mark");
        }
    }

    private static void handleUnmark(String input, Task[] tasks, int taskCount){
        try {
            int index = Integer.parseInt(input.substring(7)) - 1;
            if (index >= 0 && index < taskCount) {
                tasks[index].markAsNotDone();
                System.out.println(LINE);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[index]);
                System.out.println(LINE);
            } else {
                printInvalidTaskNumber(taskCount);
            }
        } catch (NumberFormatException e) {
            printInvalidNumberFormat("unmark");
        }
    }

    private static void printAddConfirmation(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void printInvalidFormat(String format) {
        System.out.println(LINE);
        System.out.println(" Invalid format. Use: " + format);
        System.out.println(LINE);
    }

    private static void printInvalidTaskNumber(int taskCount) {
        System.out.println(LINE);
        System.out.println(" Invalid task number. Please enter a number between 1 and " + taskCount);
        System.out.println(LINE);
    }

    private static void printInvalidNumberFormat(String command) {
        System.out.println(LINE);
        System.out.println(" Please enter a valid task number (e.g., " + command + " 2)");
        System.out.println(LINE);
    }

    private static void printUnknownCommand(){
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
}
