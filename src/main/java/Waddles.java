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

        while (!input.equals("bye")) {
            System.out.println("___________________________________________________________<3");
            System.out.println(input);
            System.out.println("___________________________________________________________<3");
            input = scanner.nextLine();
        }

        System.out.println("___________________________________________________________<3");
        System.out.println("Byeee! See you again soon:)");
        System.out.println("___________________________________________________________<3");

        scanner.close();
    }
}