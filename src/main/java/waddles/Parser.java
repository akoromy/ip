package waddles;

public class Parser {
    public static String getCommandWord(String input) {
        return input.split(" ", 2)[0];
    }

    public static int parseTaskIndex(String input, String command, int taskCount) throws WaddlesException {
        String numberPart = input.length() > command.length() ? input.substring(command.length()).trim() : "";
        int index;
        try {
            index = Integer.parseInt(numberPart) - 1;
        } catch (NumberFormatException e) {
            throw new WaddlesException("OOPS!!! Please provide a valid task number, e.g. " + command + " 2");
        }
        if (index < 0 || index >= taskCount) {
            throw new WaddlesException("OOPS!!! That task number doesn't exist.");
        }
        return index;
    }

    public static Task parseTodo(String input) throws WaddlesException {
        String description = input.length() > 4 ? input.substring(4).trim() : "";
        if (description.isEmpty()) {
            throw new WaddlesException("OOPS!!! The description of a todo cannot be empty.");
        }
        return new ToDo(description);
    }

    public static Task parseDeadline(String input) throws WaddlesException {
        String rest = input.length() > 8 ? input.substring(8).trim() : "";
        if (!rest.contains(" /by ")) {
            throw new WaddlesException(
                    "OOPS!!! A deadline needs a description and a /by date, "
                            + "e.g. deadline return book /by Sunday");
        }
        String[] parts = rest.split(" /by ", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new WaddlesException("OOPS!!! A deadline needs both a description and a /by date.");
        }
        return new Deadline(description, by);
    }

    public static Task parseEvent(String input) throws WaddlesException {
        String rest = input.length() > 5 ? input.substring(5).trim() : "";
        if (!rest.contains(" /from ") || !rest.contains(" /to ")) {
            throw new WaddlesException(
                    "OOPS!!! An event needs a description, a /from time, and a /to time, "
                            + "e.g. event meeting /from Mon 2pm /to 4pm");
        }
        String[] parts = rest.split(" /from ", 2);
        String description = parts[0].trim();
        String[] fromTo = parts[1].split(" /to ", 2);
        String from = fromTo[0].trim();
        String to = fromTo[1].trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new WaddlesException("OOPS!!! An event needs a description, a /from time, and a /to time.");
        }
        return new Event(description, from, to);
    }
}
