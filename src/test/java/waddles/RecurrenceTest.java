package waddles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class RecurrenceTest {

    @Test
    public void deadlineRecur_weekly_advancesByDateAndResetsDoneStatus() {
        Deadline deadline = new Deadline("pay rent", "2024-01-01");
        deadline.setRecurrence(Recurrence.WEEKLY);
        deadline.markAsDone();

        deadline.recur();

        assertEquals("2024-01-08", deadline.getBy());
        assertFalse(deadline.isDone());
    }

    @Test
    public void eventRecur_daily_advancesBothDates() {
        Event event = new Event("standup", "2024-01-01", "2024-01-01");
        event.setRecurrence(Recurrence.DAILY);
        event.markAsDone();

        event.recur();

        assertEquals("2024-01-02", event.getFrom());
        assertEquals("2024-01-02", event.getTo());
        assertFalse(event.isDone());
    }

    @Test
    public void deadline_withoutRecurrence_isNotRecurring() {
        Deadline deadline = new Deadline("pay rent", "2024-01-01");
        assertFalse(deadline.isRecurring());
    }
}
