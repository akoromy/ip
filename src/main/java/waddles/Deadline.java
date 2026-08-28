package waddles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task that must be completed by a specific date.
 * The date can optionally be given in yyyy-mm-dd format for structured
 * parsing and display; otherwise, the original text is kept as-is.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    private String by;
    private LocalDate byDate;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.byDate = parseDate(by);
    }

    /**
     * Attempts to parse the given text as a date in yyyy-mm-dd format.
     *
     * @param text The text to parse.
     * @return The parsed date, or null if the text is not a valid date.
     */
    private static LocalDate parseDate(String text) {
        try {
            return LocalDate.parse(text);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public String getBy() {
        return this.by;
    }

    @Override
    public String toString() {
        String displayBy = (byDate != null) ? byDate.format(OUTPUT_FORMAT) : by;
        return "[D]" + super.toString() + " (by: " + displayBy + ")";
    }
}