package waddles;

import java.util.List;
import java.util.Scanner;

/**
 * Deals with all interactions with the user: reading input and
 * displaying messages.
 */
public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

  /**
     * Displays the tasks that matched a find/search command.
     *
     * @param matches The matching tasks to display.
     */
    public void showFoundTasks(List<Task> matches) {
        if (matches.isEmpty()) {
            System.out.println("No matching tasks found.");
            return;
        }
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + "." + matches.get(i));
        }
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
        System.out.println("Hello! I'm Waddles.");
        System.out.println("How can I help you today?");
        showLine();
    }

    public void showLine() {
        System.out.println("___________________________________________________________<3");
    }

    /**
     * Displays the farewell message shown when the user exits.
     */
    public void showGoodbye() {
        showLine();
        System.out.println("Byeee! See you again soon:)");
        showLine();
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
        System.out.println("Warning: could not read saved data. Starting with an empty list.");
    }

    public void showSavingError() {
        System.out.println("Warning: could not save your tasks to disk.");
    }

    /**
     * Displays a confirmation that a task was added.
     *
     * @param task The task that was added.
     * @param size The new total number of tasks.
     */
    public void showAdded(Task task, int size) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Displays a confirmation that a task was removed.
     *
     * @param task The task that was removed.
     * @param size The new total number of tasks.
     */
    public void showDeleted(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays every task in the given list, numbered from 1.
     *
     * @param tasks The task list to display.
     */
    public void showTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }
}