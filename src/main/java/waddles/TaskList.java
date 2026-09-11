package waddles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the task list and operations to add, delete, and retrieve tasks.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList containing the given tasks, e.g. loaded from disk.
     *
     * @param loadedTasks The tasks to populate the list with.
     */
    public TaskList(List<Task> loadedTasks) {
        assert loadedTasks != null : "loadedTasks should never be null; Storage#load always returns "
                + "an empty list rather than null, even when the data file is missing or corrupted";
        tasks = loadedTasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        assert task != null : "Cannot add a null task to the list";
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index Zero-indexed position of the task to remove.
     * @return The removed task.
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> getAll() {
        return tasks;
    }

    /**
     * Returns the tasks whose description contains the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return The list of matching tasks, in original order.
     */
    public List<Task> find(String keyword) {
        assert keyword != null : "Search keyword should never be null; "
                + "Parser#parseFind rejects empty input but always returns a non-null keyword";
        List<Task> matches = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matches.add(task);
            }
        }
        return matches;
    }

    /**
     * Returns the tasks scheduled to occur on the given date, for the
     * "schedule" command. A ToDo never matches, since it has no date; a
     * Deadline matches its own date, and an Event matches any date within
     * its (inclusive) span.
     *
     * @param date The date to view the schedule for.
     * @return The matching tasks, in original order.
     */
    public List<Task> findOnDate(LocalDate date) {
        List<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.occursOn(date)) {
                matches.add(task);
            }
        }
        return matches;
    }
}
