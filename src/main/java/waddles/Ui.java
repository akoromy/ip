package waddles;

import java.util.List;
import java.util.Scanner;

/**
 * Deals with all interactions with the user: reading input and
 * displaying messages.
 *
 * <p>Each message is built by a {@code format...} method that returns the
 * message as a String. The CLI-facing {@code show...} methods print that
 * String to the console, while the GUI (see {@link MainWindow}) calls the
 * {@code format...} methods directly so the exact same wording appears in
 * both interfaces without duplicating it.
 */
public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Formats the tasks that matched a find/search command.
     *
     * @param matches The matching tasks to display.
     * @return The formatted message.
     */
    public String formatFoundTasks(List<Task> matches) {
        if (matches.isEmpty()) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            sb.append("\n").append(i + 1).append(".").append(matches.get(i));
        }
        return sb.toString();
    }

    /**
     * Displays the welcome banner and greeting shown at startup.
     */
    public void showWelcome() {
        String logo = " (\\_/)\n"
                + "( •,• )\n"
                + "( \")_(\")  WADDLES";
        showLine();
        System.out.println(logo);
        System.out.println(formatWelcomeMessage());
        showLine();
    }

    /**
     * Formats the greeting shown at startup.
     *
     * @return The formatted message.
     */
    public String formatWelcomeMessage() {
        return "Hello! I'm Waddles.\nHow can I help you today?";
    }

    public void showLine() {
        System.out.println("___________________________________________________________<3");
    }

    /**
     * Displays the farewell message shown when the user exits.
     */
    public void showGoodbye() {
        showLine();
        System.out.println(formatGoodbyeMessage());
        showLine();
    }

    /**
     * Formats the farewell message shown when the user exits.
     *
     * @return The formatted message.
     */
    public String formatGoodbyeMessage() {
        return "Byeee! See you again soon:)";
    }

    /**
     * Reads a single line of input from the user.
     *
     * @return The line of input entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showLoadingError() {
        System.out.println(formatLoadingError());
    }

    /**
     * Formats the warning shown when saved data could not be read.
     *
     * @return The formatted message.
     */
    public String formatLoadingError() {
        return "Warning: could not read saved data. Starting with an empty list.";
    }

    public void showSavingError() {
        System.out.println(formatSavingError());
    }

    /**
     * Formats the warning shown when tasks could not be saved to disk.
     *
     * @return The formatted message.
     */
    public String formatSavingError() {
        return "Warning: could not save your tasks to disk.";
    }

    /**
     * Displays a confirmation that a task was added.
     *
     * @param task The task that was added.
     * @param size The new total number of tasks.
     */
    public void showAdded(Task task, int size) {
        System.out.println(formatAdded(task, size));
    }

    /**
     * Formats a confirmation that a task was added.
     *
     * @param task The task that was added.
     * @param size The new total number of tasks.
     * @return The formatted message.
     */
    public String formatAdded(Task task, int size) {
        return "Got it. I've added this task:\n  " + task + "\nNow you have " + size + " tasks in the list.";
    }

    public void showMarked(Task task) {
        System.out.println(formatMarked(task));
    }

    /**
     * Formats a confirmation that a task was marked as done.
     *
     * @param task The task that was marked.
     * @return The formatted message.
     */
    public String formatMarked(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    public void showUnmarked(Task task) {
        System.out.println(formatUnmarked(task));
    }

    /**
     * Formats a confirmation that a task was marked as not done.
     *
     * @param task The task that was unmarked.
     * @return The formatted message.
     */
    public String formatUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Displays a confirmation that a task was removed.
     *
     * @param task The task that was removed.
     * @param size The new total number of tasks.
     */
    public void showDeleted(Task task, int size) {
        System.out.println(formatDeleted(task, size));
    }

    /**
     * Formats a confirmation that a task was removed.
     *
     * @param task The task that was removed.
     * @param size The new total number of tasks.
     * @return The formatted message.
     */
    public String formatDeleted(Task task, int size) {
        return "Noted. I've removed this task:\n  " + task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Displays every task in the given list, numbered from 1.
     *
     * @param tasks The task list to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(formatTaskList(tasks));
    }

    /**
     * Formats every task in the given list, numbered from 1.
     *
     * @param tasks The task list to display.
     * @return The formatted message.
     */
    public String formatTaskList(TaskList tasks) {
        if (tasks.size() == 0) {
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(i + 1).append(".").append(tasks.get(i));
        }
        return sb.toString();
    }
}
