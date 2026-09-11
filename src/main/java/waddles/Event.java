package waddles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task that occurs over a time span, from a start
 * point to an end point. Dates can optionally be given in yyyy-mm-dd
 * format for structured parsing and display; otherwise, the original
 * text is kept as-is.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    private final String from;
    private final String to;
    private final LocalDate fromDate;
    private final LocalDate toDate;

    /**
     * Creates an Event task with the given description and time span.
     *
     * @param description Description of the task.
     * @param from Start of the event, ideally in yyyy-mm-dd format.
     * @param to End of the event, ideally in yyyy-mm-dd format.
     */
    public Event(String description, String from, String to) {
        super(description);
        assert from != null && !from.isEmpty() : "Event 'from' text should never be null/empty; "
                + "callers must validate user input before constructing an Event";
        assert to != null && !to.isEmpty() : "Event 'to' text should never be null/empty; "
                + "callers must validate user input before constructing an Event";
        this.from = from;
        this.to = to;
        this.fromDate = parseDate(from);
        this.toDate = parseDate(to);
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

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return fromDate != null && toDate != null && !date.isBefore(fromDate) && !date.isAfter(toDate);
    }

    @Override
    public String toString() {
        String displayFrom = (fromDate != null) ? fromDate.format(OUTPUT_FORMAT) : from;
        String displayTo = (toDate != null) ? toDate.format(OUTPUT_FORMAT) : to;
        return "[E]" + super.toString() + " (from: " + displayFrom + " to: " + displayTo + ")";
    }
}