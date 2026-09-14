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
            try {
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
                throw new IrriException("I'm sorry, but I don't know what to do.");
            } catch (IrriException e) {
                printError(e.getMessage());
            }
        }
    }

    private static int handleTodo(String input, Task[] tasks, int taskCount) throws IrriException {
        String description = Parser.parseTodo(input);
        tasks[taskCount] = new ToDo(description);
        taskCount++;
        printAddConfirmation(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleDeadline(String input, Task[] tasks, int taskCount) throws IrriException {
        String[] parts = Parser.parseDeadline(input);
        tasks[taskCount] = new Deadline(parts[0], parts[1]);
        taskCount++;
        printAddConfirmation(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleEvent(String input, Task[] tasks, int taskCount) throws IrriException {
        String[] parts = Parser.parseEvent(input);
        tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
        taskCount++;
        printAddConfirmation(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static void handleMark(String input, Task[] tasks, int taskCount) throws IrriException {
        int index = Parser.parseTaskNumber(input, "mark");
        if (index >= taskCount) {
            throw new IrriException("Task number " + (index + 1) + " does not exists. " + "You have " + taskCount + " task" + (taskCount == 1 ? "" : "s") + " in the list.");
        }
        tasks[index].markAsDone();
        System.out.println(LINE);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + tasks[index]);
        System.out.println(LINE);
    }

    private static void handleUnmark(String input, Task[] tasks, int taskCount) throws IrriException {
        int index = Parser.parseTaskNumber(input, "unmark");
        if (index >= taskCount) {
            throw new IrriException("Task number \" + (index + 1) + \" does not exist. " + "You have " + taskCount + " task" + (taskCount == 1 ? "" : "s") + " in the list.");
        }
        tasks[index].markAsNotDone();
        System.out.println(LINE);
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks[index]);
        System.out.println(LINE);
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

    private static void printError(String message) {
        System.out.println(LINE);
        System.out.println("Oh no! " + message);
        System.out.println(LINE);
    }

    private static void printAddConfirmation(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }
}
