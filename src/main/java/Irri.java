import task.*;

import java.util.Scanner;
import java.util.ArrayList;

public class Irri {
    private static final String LINE = "____________________________________________________________";
    private static final String BANNER =
            " ___           _ \n"
                    + "|_ _|_ __ _ __(_)\n"
                    + " | || '__| '__| |\n"
                    + " | || |  | |  | |\n"
                    + "|___|_|  |_|  |_|\n";

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        printWelcome();
        runCommandLoop(inputScanner);
        printGoodbye();
        inputScanner.close();
    }

    private static void runCommandLoop(Scanner inputScanner) {
        ArrayList<Task> tasks = Storage.load();
        boolean isRunning = true;

        while (isRunning) {
            String input = inputScanner.nextLine();

            try {
                if (input.equalsIgnoreCase("bye")) {
                    isRunning = false;
                    continue;
                }
                if (input.equalsIgnoreCase("list")) {
                    printTaskList(tasks);
                    continue;
                }
                if (input.toLowerCase().startsWith("todo ")) {
                    handleTodo(input, tasks);
                    continue;
                }
                if (input.toLowerCase().startsWith("deadline ")) {
                    handleDeadline(input, tasks);
                    continue;
                }
                if (input.toLowerCase().startsWith("event ")) {
                    handleEvent(input, tasks);
                    continue;
                }
                if (input.toLowerCase().startsWith("delete ")) {
                    handleDelete(input, tasks);
                    continue;
                }
                if (input.toLowerCase().startsWith("mark ")) {
                    handleMark(input, tasks);
                    continue;
                }
                if (input.toLowerCase().startsWith("unmark ")) {
                    handleUnmark(input, tasks);
                    continue;
                }
                throw new IrriException("I'm sorry, but I don't know what to do.");
            } catch (IrriException e) {
                printError(e.getMessage());
            }
        }
    }

    private static void handleTodo(String input, ArrayList<Task> tasks) throws IrriException {
        String description = Parser.parseTodo(input);
        tasks.add(new ToDo(description));
        printAddConfirmation(tasks.get(tasks.size() - 1), tasks.size());
        Storage.save(tasks);
    }

    private static void handleDeadline(String input, ArrayList<Task> tasks) throws IrriException {
        String[] parts = Parser.parseDeadline(input);
        tasks.add(new Deadline(parts[0], parts[1]));
        printAddConfirmation(tasks.get(tasks.size() - 1), tasks.size());
        Storage.save(tasks);
    }

    private static void handleEvent(String input, ArrayList<Task> tasks) throws IrriException {
        String[] parts = Parser.parseEvent(input);
        tasks.add(new Event(parts[0], parts[1], parts[2]));
        printAddConfirmation(tasks.get(tasks.size() - 1), tasks.size());
        Storage.save(tasks);
    }

    private static void handleDelete(String input, ArrayList<Task> tasks) throws IrriException {
        int index = Parser.parseTaskNumber(input, "delete");
        if (index >= tasks.size()) {
            throw new IrriException("Task number " + (index + 1) + " does not exist. "
                    + "You have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list.");
        }
        Task removed = tasks.remove(index);
        System.out.println(LINE);
        System.out.println(" OK. I've removed this task:");
        System.out.println("   " + removed);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
        Storage.save(tasks);
    }

    private static void handleMark(String input, ArrayList<Task> tasks) throws IrriException {
        int index = Parser.parseTaskNumber(input, "mark");
        if (index >= tasks.size()) {
            throw new IrriException("Task number " + (index + 1) + " does not exist. " + "You have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list.");
        }
        tasks.get(index).markAsDone();
        System.out.println(LINE);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + tasks.get(index));
        System.out.println(LINE);
        Storage.save(tasks);
    }

    private static void handleUnmark(String input, ArrayList<Task> tasks) throws IrriException {
        int index = Parser.parseTaskNumber(input, "unmark");
        if (index >= tasks.size()) {
            throw new IrriException("Task number " + (index + 1) + " does not exist. " + "You have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list.");
        }
        tasks.get(index).markAsNotDone();
        System.out.println(LINE);
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks.get(index));
        System.out.println(LINE);
        Storage.save(tasks);
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

    private static void printTaskList(ArrayList<Task> tasks) {
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
