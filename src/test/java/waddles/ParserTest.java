package waddles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parseTodo_validDescription_returnsToDoWithDescription() throws WaddlesException {
        Task task = Parser.parseTodo("todo read book");

        assertTrue(task instanceof ToDo);
        assertEquals("read book", task.getDescription());
        assertEquals(false, task.isDone());
    }

    @Test
    public void parseTodo_emptyDescription_exceptionThrown() {
        assertThrows(WaddlesException.class, () -> Parser.parseTodo("todo"));
        assertThrows(WaddlesException.class, () -> Parser.parseTodo("todo   "));
    }

    @Test
    public void parseDeadline_validInput_returnsDeadlineWithCorrectFields() throws WaddlesException {
        Task task = Parser.parseDeadline("deadline return book /by 2019-12-01");

        assertTrue(task instanceof Deadline);
        assertEquals("return book", task.getDescription());

        Deadline deadline = (Deadline) task;
        assertEquals("2019-12-01", deadline.getBy());
    }

    @Test
    public void parseDeadline_missingByKeyword_exceptionThrown() {
        assertThrows(WaddlesException.class, () -> Parser.parseDeadline("deadline return book"));
    }

    @Test
    public void parseDeadline_emptyDescriptionOrDate_exceptionThrown() {
        assertThrows(WaddlesException.class, () -> Parser.parseDeadline("deadline /by 2019-12-01"));
        assertThrows(WaddlesException.class, () -> Parser.parseDeadline("deadline return book /by "));
    }

    @Test
    public void parseSchedule_validDate_returnsThatDate() throws WaddlesException {
        LocalDate date = Parser.parseSchedule("schedule 2024-12-01");
        assertEquals(LocalDate.of(2024, 12, 1), date);
    }

    @Test
    public void parseSchedule_today_returnsCurrentDate() throws WaddlesException {
        assertEquals(LocalDate.now(), Parser.parseSchedule("schedule today"));
    }

    @Test
    public void parseSchedule_missingDate_exceptionThrown() {
        assertThrows(WaddlesException.class, () -> Parser.parseSchedule("schedule"));
        assertThrows(WaddlesException.class, () -> Parser.parseSchedule("schedule   "));
    }

    @Test
    public void parseSchedule_invalidDateFormat_exceptionThrown() {
        assertThrows(WaddlesException.class, () -> Parser.parseSchedule("schedule next monday"));
    }

    @Test
    public void parseTaskIndex_validNumber_returnsZeroIndexedValue() throws WaddlesException {
        int index = Parser.parseTaskIndex("mark 2", "mark", 5);
        assertEquals(1, index);
    }

    @Test
    public void parseTaskIndex_outOfRange_exceptionThrown() {
        assertThrows(WaddlesException.class, () -> Parser.parseTaskIndex("mark 99", "mark", 5));
    }

    @Test
    public void parseTaskIndex_notANumber_exceptionThrown() {
        assertThrows(WaddlesException.class, () -> Parser.parseTaskIndex("mark abc", "mark", 5));
    }
}