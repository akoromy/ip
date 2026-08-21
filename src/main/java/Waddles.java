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

        String[] tasks = new String[100];
        int taskCount = 0;

        while (!input.equals("bye")) {
            System.out.println("___________________________________________________________<3");

            if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = input;
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