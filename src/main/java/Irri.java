import task.*;

import java.util.ArrayList;

public class Irri {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    public Irri(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IrriException e) {
            ui.printLoadingError();
            tasks = new TaskList();
        }
    }

    public void runCommandLoop() {
        ui.printWelcome();
        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();

            try {
                if (input.equalsIgnoreCase("bye")) {
                    isRunning = false;
                    continue;
                }
                if (input.equalsIgnoreCase("list")) {
                    ui.printTaskList(tasks.getTasks());
                    continue;
                }
                if (input.toLowerCase().startsWith("todo ")) {
                    handleTodo(input);
                    continue;
                }
                if (input.toLowerCase().startsWith("deadline ")) {
                    handleDeadline(input);
                    continue;
                }
                if (input.toLowerCase().startsWith("event ")) {
                    handleEvent(input);
                    continue;
                }
                if (input.toLowerCase().startsWith("delete ")) {
                    handleDelete(input);
                    continue;
                }
                if (input.toLowerCase().startsWith("mark ")) {
                    handleMark(input);
                    continue;
                }
                if (input.toLowerCase().startsWith("unmark ")) {
                    handleUnmark(input);
                    continue;
                }
                if (input.toLowerCase().startsWith("find ")) {
                    handleFind(input);
                    continue;
                }
                throw new IrriException("I'm sorry, but I don't know what to do.");
            } catch (IrriException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    private void handleTodo(String input) throws IrriException {
        Task task = Parser.parseTodo(input);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.printTaskAdded(task, tasks.size());
    }

    private void handleDeadline(String input) throws IrriException {
        Task task = Parser.parseDeadline(input);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.printTaskAdded(task, tasks.size());
    }

    private void handleEvent(String input) throws IrriException {
        Task task = Parser.parseEvent(input);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.printTaskAdded(task, tasks.size());
    }

    private void handleDelete(String input) throws IrriException {
        int index = Parser.parseTaskNumber(input, "delete");
        if (index >= tasks.size()) {
            throw new IrriException("Task number " + (index + 1) + " does not exist.");
        }
        Task removed = tasks.remove(index);
        storage.save(tasks.getTasks());
        ui.printTaskDeleted(removed, tasks.size());
    }

    private void handleMark(String input) throws IrriException {
        int index = Parser.parseTaskNumber(input, "mark");
        if (index >= tasks.size()) {
            throw new IrriException("Task number " + (index + 1) + " does not exist.");
        }
        tasks.get(index).markAsDone();
        storage.save(tasks.getTasks());
        ui.printTaskMarked(tasks.get(index));
    }

    private void handleUnmark(String input) throws IrriException {
        int index = Parser.parseTaskNumber(input, "unmark");
        if (index >= tasks.size()) {
            throw new IrriException("Task number " + (index + 1) + " does not exist.");
        }
        tasks.get(index).markAsNotDone();
        storage.save(tasks.getTasks());
        ui.printTaskUnmarked(tasks.get(index));
    }

    private void handleFind(String input) throws IrriException {
        String keyword = Parser.parseFind(input);
        ArrayList<Task> matchingTasks = tasks.find(keyword);
        ui.printMatchingTasks(matchingTasks);
    }
    public static void main(String[] args) {
        new Irri("./data/irri.txt").runCommandLoop();
    }
}