package waddles;

import java.time.LocalDate;

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
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns whether this task falls on the given date, for the "schedule"
     * command. A plain Task has no date, so this is always false;
     * {@link Deadline} and {@link Event} override it.
     *
     * @param date The date to check against.
     * @return True if this task is scheduled on that date.
     */
    public boolean occursOn(LocalDate date) {
        return false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}