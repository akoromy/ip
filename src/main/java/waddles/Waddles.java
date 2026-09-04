package waddles;

import java.io.IOException;

/**
 * Entry point for the Waddles chatbot. Coordinates the Ui, Storage,
 * and TaskList to run the main command loop, and exposes
 * {@link #getResponse(String)} so a GUI (see {@link MainWindow}) can drive
 * the same command logic one line at a time.
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
            System.out.println(getResponse(input));
            ui.showLine();
            input = ui.readCommand();
        }

        ui.showGoodbye();
    }

    /**
     * Returns the greeting shown when a GUI session starts.
     *
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        return ui.formatWelcomeMessage();
    }

    /**
     * Executes a single line of user input and returns the resulting
     * message, without printing anything itself. This is the logic shared
     * by both the CLI ({@link #run()}) and the JavaFX GUI.
     *
     * @param input A full line of user input.
     * @return The message to show the user in response.
     */
    public String getResponse(String input) {
        try {
            String command = Parser.getCommandWord(input);

            if (command.equals("bye")) {
                return ui.formatGoodbyeMessage();
            } else if (command.equals("list")) {
                return ui.formatTaskList(tasks);
            } else if (command.equals("find")) {
                String keyword = Parser.parseFind(input);
                return ui.formatFoundTasks(tasks.find(keyword));
            } else if (command.equals("mark")) {
                int index = Parser.parseTaskIndex(input, "mark", tasks.size());
                tasks.get(index).markAsDone();
                String message = ui.formatMarked(tasks.get(index));
                saveTasks();
                return message;
            } else if (command.equals("unmark")) {
                int index = Parser.parseTaskIndex(input, "unmark", tasks.size());
                tasks.get(index).markAsNotDone();
                String message = ui.formatUnmarked(tasks.get(index));
                saveTasks();
                return message;
            } else if (command.equals("delete")) {
                int index = Parser.parseTaskIndex(input, "delete", tasks.size());
                Task removed = tasks.delete(index);
                String message = ui.formatDeleted(removed, tasks.size());
                saveTasks();
                return message;
            } else if (command.equals("todo")) {
                Task task = Parser.parseTodo(input);
                tasks.add(task);
                String message = ui.formatAdded(task, tasks.size());
                saveTasks();
                return message;
            } else if (command.equals("deadline")) {
                Task task = Parser.parseDeadline(input);
                tasks.add(task);
                String message = ui.formatAdded(task, tasks.size());
                saveTasks();
                return message;
            } else if (command.equals("event")) {
                Task task = Parser.parseEvent(input);
                tasks.add(task);
                String message = ui.formatAdded(task, tasks.size());
                saveTasks();
                return message;
            } else {
                throw new WaddlesException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (WaddlesException e) {
            return e.getMessage();
        }
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
