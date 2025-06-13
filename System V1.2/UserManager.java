import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<User> users = new ArrayList<>();

    public void registerUser(Scanner scanner, boolean isAdminRegistration) {
        System.out.println("\n--- CADASTRO DE USUÁRIO ---");

        System.out.print("Nome completo: ");
        String name = scanner.nextLine();

        String email;
        while (true) {
            System.out.print("E-mail: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                if (!emailExists(email)) break;
                System.out.println("❌ E-mail já cadastrado!");
            } else {
                System.out.println("❌ E-mail inválido!");
            }
        }

        int age = getValidAge(scanner);

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        boolean isAdmin = isAdminRegistration;
        
        users.add(new User(name, email, age, password, isAdmin));
        System.out.println("✅ Usuário cadastrado com sucesso!");
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean emailExists(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private int getValidAge(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Idade: ");
                int age = Integer.parseInt(scanner.nextLine());
                if (age > 0) return age;
                System.out.println("❌ Idade deve ser positiva!");
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido!");
            }
        }
    }

    public User authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void listUsers() {
        if (users.isEmpty()) {
            System.out.println("\nNenhum usuário cadastrado.");
            return;
        }

        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i));
        }
    }

    public void searchUsers(Scanner scanner) {
        System.out.print("\nDigite o termo de busca: ");
        String term = scanner.nextLine().toLowerCase();

        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(term) || 
                user.getEmail().toLowerCase().contains(term)) {
                results.add(user);
            }
        }

        if (results.isEmpty()) {
            System.out.println("Nenhum usuário encontrado com '" + term + "'");
        } else {
            System.out.println("\n--- RESULTADOS (" + results.size() + ") ---");
            for (User user : results) {
                System.out.println(user);
            }
        }
    }

    public void editUser(Scanner scanner, User currentUser) {
        listUsers();
        System.out.print("\nDigite o número do usuário para editar: ");

        try {
            int userNumber = Integer.parseInt(scanner.nextLine());
            if (userNumber < 1 || userNumber > users.size()) {
                System.out.println("❌ Número inválido!");
                return;
            }

            User userToEdit = users.get(userNumber - 1);
            
            
            if (!currentUser.isAdmin() && !currentUser.getEmail().equalsIgnoreCase(userToEdit.getEmail())) {
                System.out.println("❌ Você só pode editar seu próprio perfil!");
                return;
            }

            System.out.println("\nEditando: " + userToEdit);

            System.out.print("Novo nome (Enter para manter): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) userToEdit.setName(newName);

            System.out.print("Nova idade (0 para manter): ");
            String ageInput = scanner.nextLine();
            if (!ageInput.isEmpty()) {
                try {
                    int newAge = Integer.parseInt(ageInput);
                    if (newAge > 0) userToEdit.setAge(newAge);
                } catch (NumberFormatException e) {
                    System.out.println("❌ Idade não alterada (valor inválido)");
                }
            }

            System.out.println("✅ Usuário atualizado!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Digite um número válido!");
        }
    }

    public void deleteUser(Scanner scanner, User currentUser) {
        listUsers();
        System.out.print("\nDigite o número do usuário para excluir: ");

        try {
            int userNumber = Integer.parseInt(scanner.nextLine());
            if (userNumber < 1 || userNumber > users.size()) {
                System.out.println("❌ Número inválido!");
                return;
            }

            User userToDelete = users.get(userNumber - 1);
            
            
            if (!currentUser.isAdmin()) {
                System.out.println("❌ Apenas administradores podem excluir usuários!");
                return;
            }

            System.out.print("Tem certeza que deseja excluir " + userToDelete.getName() + "? (s/n): ");
            String confirmation = scanner.nextLine();
            
            if (confirmation.equalsIgnoreCase("s")) {
                users.remove(userNumber - 1);
                System.out.println("✅ Usuário excluído com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Digite um número válido!");
        }
    }
}
