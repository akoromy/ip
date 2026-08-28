package waddles;

import java.io.IOException;

/**
 * Entry point for the Waddles chatbot. Coordinates the Ui, Storage,
 * and TaskList to run the main command loop.
 */

public class Waddles {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a Waddles chatbot that persists tasks to the given file path.
     *
     * @param filePath Relative path to the data file.
     */
    public Waddles(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    /**
     * Runs the main command loop: greets the user, repeatedly reads and
     * executes commands until "bye" is entered, then says goodbye.
     */
    public void run() {
        ui.showWelcome();
        String input = ui.readCommand();

        while (!input.equals("bye")) {
            ui.showLine();

            try {
                String command = Parser.getCommandWord(input);

                if (command.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (command.equals("find")) {
                    String keyword = Parser.parseFind(input);
                    ui.showFoundTasks(tasks.find(keyword));
                } else if (command.equals("mark")) {
                    int index = Parser.parseTaskIndex(input, "mark", tasks.size());
                    tasks.get(index).markAsDone();
                    ui.showMarked(tasks.get(index));
                    saveTasks();
                } else if (command.equals("unmark")) {
                    int index = Parser.parseTaskIndex(input, "unmark", tasks.size());
                    tasks.get(index).markAsNotDone();
                    ui.showUnmarked(tasks.get(index));
                    saveTasks();
                } else if (command.equals("delete")) {
                    int index = Parser.parseTaskIndex(input, "delete", tasks.size());
                    Task removed = tasks.delete(index);
                    ui.showDeleted(removed, tasks.size());
                    saveTasks();
                } else if (command.equals("todo")) {
                    Task task = Parser.parseTodo(input);
                    tasks.add(task);
                    ui.showAdded(task, tasks.size());
                    saveTasks();
                } else if (command.equals("deadline")) {
                    Task task = Parser.parseDeadline(input);
                    tasks.add(task);
                    ui.showAdded(task, tasks.size());
                    saveTasks();
                } else if (command.equals("event")) {
                    Task task = Parser.parseEvent(input);
                    tasks.add(task);
                    ui.showAdded(task, tasks.size());
                    saveTasks();
                } else {
                    throw new WaddlesException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (WaddlesException e) {
                ui.showError(e.getMessage());
            }

            ui.showLine();
            input = ui.readCommand();
        }

        ui.showGoodbye();
    }
    /**
     * Saves the current task list to disk, showing an error if it fails.
     */
    private void saveTasks() {
        try {
            storage.save(tasks.getAll());
        } catch (IOException e) {
            ui.showSavingError();
        }
    }

    public static void main(String[] args) {
        new Waddles("./data/waddles.txt").run();
    }
}