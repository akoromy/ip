import java.util.Scanner;

public class Waddles {
    public static void main(String[] args) {
        String logo = " (\\_/)\n"
                + "( •,• )\n"
                + "( \")_(\")  WADDLES";
        System.out.println("___________________________________________________________<3");
        System.out.println(logo);
        System.out.println("Hello! I'm Waddles.");
        System.out.println("How can I help you today?");
        System.out.println("___________________________________________________________<3");

        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            System.out.println("___________________________________________________________<3");

            try {
                if (input.equals("list")) {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                } else if (input.startsWith("mark ") || input.equals("mark")) {
                    int index = parseTaskIndex(input, "mark", taskCount);
                    tasks[index].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[index]);
                } else if (input.startsWith("unmark ") || input.equals("unmark")) {
                    int index = parseTaskIndex(input, "unmark", taskCount);
                    tasks[index].markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[index]);
                } else if (input.startsWith("delete ") || input.equals("delete")) {
                    int index = parseTaskIndex(input, "delete", taskCount);
                    Task removed = tasks[index];

                    for (int i = index; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                    }
                    tasks[taskCount - 1] = null;
                    taskCount--;

                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                } else if (input.startsWith("todo") ) {
                    String description = input.length() > 4 ? input.substring(4).trim() : "";
                    if (description.isEmpty()) {
                        throw new WaddlesException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    tasks[taskCount] = new ToDo(description);
                    taskCount++;
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                } else if (input.startsWith("deadline")) {
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
                        throw new WaddlesException(
                                "OOPS!!! A deadline needs both a description and a /by date.");
                    }
                    tasks[taskCount] = new Deadline(description, by);
                    taskCount++;
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                } else if (input.startsWith("event")) {
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
                        throw new WaddlesException(
                                "OOPS!!! An event needs a description, a /from time, and a /to time.");
                    }
                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                } else {
                    throw new WaddlesException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (WaddlesException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("___________________________________________________________<3");
            input = scanner.nextLine();
        }

        System.out.println("___________________________________________________________<3");
        System.out.println("Byeee! See you again soon:)");
        System.out.println("___________________________________________________________<3");
        scanner.close();
    }

    private static int parseTaskIndex(String input, String command, int taskCount) throws WaddlesException {
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
}