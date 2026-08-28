package waddles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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