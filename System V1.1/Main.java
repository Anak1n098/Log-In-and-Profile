import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    private static UserManager userManager = new UserManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showWelcomeMessage();
        runMainMenu();
        scanner.close();
    }

    private static void showWelcomeMessage() {
        System.out.println("====================================");
        System.out.println("    GERENCIADOR DE USUÁRIOS CLI     ");
        System.out.println("====================================");
    }

    private static void runMainMenu() {
        while (true) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Listar usuários");
            System.out.println("3. Buscar usuário");
            System.out.println("4. Editar usuário");
            System.out.println("5. Excluir usuário");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        userManager.registerUser(scanner);
                        break;
                    case 2:
                        userManager.listUsers();
                        break;
                    case 3:
                        searchUserMenu();
                        break;
                    case 4:
                        editUserMenu();
                        break;
                    case 5:
                        deleteUserMenu();
                        break;
                    case 6:
                        System.out.println("\nSaindo do sistema... Até logo!");
                        return;
                    default:
                        System.out.println("❌ Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada inválida! Digite um número.");
                scanner.nextLine(); // Limpa entrada incorreta
            }
        }
    }

  
    private static void searchUserMenu() {
        System.out.println("\n--- BUSCAR USUÁRIO ---");
        userManager.searchUsers(scanner);
    }

    
    private static void editUserMenu() {
        System.out.println("\n--- EDITAR USUÁRIO ---");
        userManager.editUser(scanner);
    }

    
    private static void deleteUserMenu() {
        System.out.println("\n--- EXCLUIR USUÁRIO ---");
        userManager.deleteUser(scanner);
    }
}
