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
                    System.out.println((i + 1) + ". " + tasks[i]);
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
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("added: " + input);
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