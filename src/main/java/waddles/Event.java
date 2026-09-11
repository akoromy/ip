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

    // Not final: recur() advances from/to/fromDate/toDate in place to the next occurrence.
    private String from;
    private String to;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Recurrence recurrence = Recurrence.NONE;

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

    /**
     * Returns whether both this event's dates were understood as structured
     * yyyy-mm-dd dates, which is required for recurrence to be able to
     * compute a next occurrence.
     *
     * @return True if both dates were parsed successfully.
     */
    public boolean hasStructuredDate() {
        return fromDate != null && toDate != null;
    }

    /**
     * Sets how often this event repeats.
     *
     * @param recurrence The recurrence period; use {@link Recurrence#NONE} for a one-off event.
     */
    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    @Override
    public boolean isRecurring() {
        return recurrence != Recurrence.NONE;
    }

    @Override
    public void recur() {
        if (!isRecurring()) {
            throw new UnsupportedOperationException("This event does not recur");
        }
        fromDate = recurrence.advance(fromDate);
        toDate = recurrence.advance(toDate);
        from = fromDate.toString();
        to = toDate.toString();
        markAsNotDone();
    }

    @Override
    public String toString() {
        String displayFrom = (fromDate != null) ? fromDate.format(OUTPUT_FORMAT) : from;
        String displayTo = (toDate != null) ? toDate.format(OUTPUT_FORMAT) : to;
        String recurrenceSuffix = isRecurring() ? " (every " + recurrence.displayLabel() + ")" : "";
        return "[E]" + super.toString() + " (from: " + displayFrom + " to: " + displayTo + ")" + recurrenceSuffix;
    }
}