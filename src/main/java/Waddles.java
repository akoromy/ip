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

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (!input.equals("bye")) {
            System.out.println("___________________________________________________________<3");

            if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                tasks[index].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index]);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                tasks[index].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[index]);
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                tasks[taskCount] = new ToDo(description);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("deadline ")) {
                String rest = input.substring(9).trim();
                String[] parts = rest.split(" /by ", 2);
                String description = parts[0];
                String by = parts[1];
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("event ")) {
                String rest = input.substring(6).trim();
                String[] parts = rest.split(" /from ", 2);
                String description = parts[0];
                String[] fromTo = parts[1].split(" /to ", 2);
                String from = fromTo[0];
                String to = fromTo[1];
                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            }

            System.out.println("___________________________________________________________<3");
            input = scanner.nextLine();
        }

        System.out.println("___________________________________________________________<3");
        System.out.println("Byeee! See you again soon:)");
        System.out.println("___________________________________________________________<3");

        scanner.close();
    }
}
