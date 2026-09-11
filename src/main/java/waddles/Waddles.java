package waddles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * A snapshot of the task list taken just before the most recent
     * mutating command, restored by "undo". Null means there is nothing to
     * undo (either no mutating command has run yet, or the last one was
     * already undone). This supports undoing only the single most recent
     * command, by design.
     */
    private List<Task> undoSnapshot;

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
            assert command != null : "Parser#getCommandWord splits on a fixed pattern and should "
                    + "always return at least an empty string, never null";

            if (command.equals("bye")) {
                return ui.formatGoodbyeMessage();
            } else if (command.equals("list")) {
                return ui.formatTaskList(tasks);
            } else if (command.equals("find")) {
                String keyword = Parser.parseFind(input);
                return ui.formatFoundTasks(tasks.find(keyword));
            } else if (command.equals("undo")) {
                return undoLastCommand();
            } else if (command.equals("mark")) {
                int index = Parser.parseTaskIndex(input, "mark", tasks.size());
                saveUndoSnapshot();
                tasks.get(index).markAsDone();
                String message = ui.formatMarked(tasks.get(index));
                saveTasks();
                return message;
            } else if (command.equals("unmark")) {
                int index = Parser.parseTaskIndex(input, "unmark", tasks.size());
                saveUndoSnapshot();
                tasks.get(index).markAsNotDone();
                String message = ui.formatUnmarked(tasks.get(index));
                saveTasks();
                return message;
            } else if (command.equals("delete")) {
                int index = Parser.parseTaskIndex(input, "delete", tasks.size());
                saveUndoSnapshot();
                Task removed = tasks.delete(index);
                String message = ui.formatDeleted(removed, tasks.size());
                saveTasks();
                return message;
            } else if (command.equals("todo")) {
                Task task = Parser.parseTodo(input);
                saveUndoSnapshot();
                tasks.add(task);
                String message = ui.formatAdded(task, tasks.size());
                saveTasks();
                return message;
            } else if (command.equals("deadline")) {
                Task task = Parser.parseDeadline(input);
                saveUndoSnapshot();
                tasks.add(task);
                String message = ui.formatAdded(task, tasks.size());
                saveTasks();
                return message;
            } else if (command.equals("event")) {
                Task task = Parser.parseEvent(input);
                saveUndoSnapshot();
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
     * Records a deep copy of the current task list as the one "undo" will
     * restore. Called right before a mutating command takes effect. Uses
     * Task#copy() rather than a reference copy of the list, since the same
     * Task objects would otherwise still be affected by later mutations
     * (e.g. a later markAsDone() would retroactively "undo" as done too).
     */
    private void saveUndoSnapshot() {
        List<Task> snapshot = new ArrayList<>();
        for (Task task : tasks.getAll()) {
            snapshot.add(task.copy());
        }
        undoSnapshot = snapshot;
    }

    /**
     * Restores the task list to how it was just before the most recent
     * mutating command (add, delete, mark, or unmark), i.e. a single-level
     * undo. Calling this again immediately after has nothing left to undo.
     *
     * @return The message to show the user.
     */
    private String undoLastCommand() {
        if (undoSnapshot == null) {
            return "OOPS!!! There's nothing to undo yet.";
        }
        tasks.setAll(undoSnapshot);
        undoSnapshot = null;
        saveTasks();
        return "Done! I've undone your last change:\n" + ui.formatTaskList(tasks);
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
