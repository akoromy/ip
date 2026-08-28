package waddles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(List<Task> tasks) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        FileWriter writer = new FileWriter(file);
        for (Task task : tasks) {
            writer.write(taskToFileFormat(task) + System.lineSeparator());
        }
        writer.close();
    }

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                Task task = parseLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return tasks;
    }

    private String taskToFileFormat(Task task) {
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

    private Task parseLine(String line) {
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
            return null;
        }
    }
}