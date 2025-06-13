import java.util.Scanner;

public class AuthService {
    private static User currentUser;
    private static final User ADMIN = new User("Admin", "admin@system", 0, "admin123", true);

    public static boolean login(Scanner scanner, UserManager userManager) {
        System.out.println("\n=== LOGIN ===");
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        
        System.out.print("Senha: ");
        String password = scanner.nextLine();

      
        if (email.equalsIgnoreCase(ADMIN.getEmail())) {
            if (password.equals(ADMIN.getPassword())) {
                currentUser = ADMIN;
                System.out.println("\nBem-vindo, Administrador!");
                return true;
            }
        }

        
        User user = userManager.authenticate(email, password);
        if (user != null) {
            currentUser = user;
            System.out.println("\nBem-vindo, " + user.getName() + "!");
            return true;
        }

        System.out.println("❌ E-mail ou senha incorretos!");
        return false;
    }

    public static void logout() {
        currentUser = null;
        System.out.println("\nLogout realizado com sucesso!");
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }
}
