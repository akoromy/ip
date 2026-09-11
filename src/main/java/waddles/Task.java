package waddles;

/**
 * Represents a task with a description and a done/not-done status.
 * Serves as the base class for more specific task types.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a new, not-yet-done task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        assert description != null : "Task description should never be null; "
                + "callers must validate user input before constructing a Task";
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
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Creates an independent copy of this task, with the same description
     * and done status. Used by the "undo" command to snapshot the task list
     * before a mutating command, since Task objects are mutable (e.g.
     * markAsDone()) and a plain reference copy of the list would still let
     * later mutations affect the snapshot. Overridden by subclasses that
     * carry extra fields (e.g. a Deadline's date).
     *
     * @return A new Task equivalent to this one.
     */
    public Task copy() {
        Task copy = new Task(description);
        if (isDone) {
            copy.markAsDone();
        }
        return copy;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}