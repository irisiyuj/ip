import task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            FileWriter writer = new FileWriter(file);
            for (Task task: tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(" Warning: Unable to save tasks to file.");
        }
    }

    public ArrayList<Task> load() throws IrriException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new IrriException(" Warning: Unable to load tasks from file.");
        }

        return tasks;
    }

    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new ToDo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    return null;
                }
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < 5) {
                    return null;
                }
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
