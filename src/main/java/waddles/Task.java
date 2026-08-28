package waddles;

/**
 * Represents a task with a description and a done/not-done status.
 * Serves as the base class for more specific task types.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Creates a new, not-yet-done task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a single-character icon representing the task's done status.
     *
     * @return "X" if the task is done, or " " (a blank space) otherwise.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}