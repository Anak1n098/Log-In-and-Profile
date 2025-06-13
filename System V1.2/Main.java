import java.util.Scanner;

public class Main {
    private static UserManager userManager = new UserManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showWelcomeMessage();
        
        // Cadastra o admin se não existir
        if (userManager.authenticate("admin@system", "admin123") == null) {
            userManager.registerUser(scanner, true);
        }

        if (!AuthService.login(scanner, userManager)) {
            System.out.println("Acesso negado. Encerrando sistema...");
            return;
        }

        runMainMenu();
        scanner.close();
    }

    private static void showWelcomeMessage() {
        System.out.println("====================================");
        System.out.println("   SISTEMA DE GERENCIAMENTO DE USUÁRIOS");
        System.out.println("====================================");
    }

    private static void runMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Listar usuários");
            System.out.println("2. Buscar usuários");
            System.out.println("3. Editar usuário");
            System.out.println("4. Excluir usuário");
            System.out.println("5. Cadastrar novo usuário");
            System.out.println("6. Logout");
            System.out.println("7. Sair do sistema");
            System.out.print("Escolha uma opção: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        userManager.listUsers();
                        break;
                    case 2:
                        userManager.searchUsers(scanner);
                        break;
                    case 3:
                        userManager.editUser(scanner, AuthService.getCurrentUser());
                        break;
                    case 4:
                        userManager.deleteUser(scanner, AuthService.getCurrentUser());
                        break;
                    case 5:
                        userManager.registerUser(scanner, false);
                        break;
                    case 6:
                        AuthService.logout();
                        if (!AuthService.login(scanner, userManager)) {
                            running = false;
                        }
                        break;
                    case 7:
                        System.out.println("Encerrando sistema...");
                        running = false;
                        break;
                    default:
                        System.out.println("❌ Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido!");
            }
        }
    }
}
