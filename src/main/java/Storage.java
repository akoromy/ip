import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH = "./data/waddles.txt";

    /**
     * Saves the given tasks to the data file, creating the folder/file if needed.
     */
    public static void save(Task[] tasks, int taskCount) throws IOException {
        File file = new File(FILE_PATH);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        FileWriter writer = new FileWriter(file);
        for (int i = 0; i < taskCount; i++) {
            writer.write(taskToFileFormat(tasks[i]) + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Loads tasks from the data file into the given array, skipping any
     * corrupted lines. Returns the number of tasks successfully loaded.
     * If the file doesn't exist yet, returns 0 (fresh start).
     */
    public static int load(Task[] tasks) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return 0;
        }

        int count = 0;
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                Task task = parseLine(line);
                if (task != null) {
                    tasks[count] = task;
                    count++;
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Warning: could not read saved data. Starting with an empty list.");
            return 0;
        }
        return count;
    }

    private static String taskToFileFormat(Task task) {
        String doneFlag = task.isDone() ? "1" : "0";
        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + doneFlag + " | " + task.getDescription() + " | " + d.getBy();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + doneFlag + " | " + task.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        } else {
            return "T | " + doneFlag + " | " + task.getDescription();
        }
    }

    private static Task parseLine(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("1");
            String description = parts[2].trim();

            Task task;
            if (type.equals("T")) {
                task = new ToDo(description);
            } else if (type.equals("D")) {
                String by = parts[3].trim();
                task = new Deadline(description, by);
            } else if (type.equals("E")) {
                String from = parts[3].trim();
                String to = parts[4].trim();
                task = new Event(description, from, to);
            } else {
                return null;
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            // Corrupted line (wrong format, missing fields, etc.) — skip it.
            return null;
        }
    }
}