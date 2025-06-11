import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<User> users = new ArrayList<>();

    
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    
    public void registerUser(Scanner scanner) {
        System.out.println("\n--- CADASTRO DE USUÁRIO ---");

        String name;
        do {
            System.out.print("Nome completo: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("❌ Nome não pode ser vazio!");
            }
        } while (name.isEmpty());

        String email;
        do {
    System.out.print("E-mail: ");
    email = scanner.nextLine().trim();
    if (!isValidEmail(email)) {
        System.out.println("❌ E-mail inválido! Deve conter '@' e '.'");
    } else {
        final String finalEmail = email;
            if (users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(finalEmail))) {
                System.out.println("❌ E-mail já cadastrado!");
                email = "";
            }
        }
    } while (!isValidEmail(email));

        int age = 0;
        while (age <= 0) {
            try {
                System.out.print("Idade: ");
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0) {
                    System.out.println("❌ Idade deve ser positiva!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido!");
            }
        }

        users.add(new User(name, email, age));
        System.out.println("✅ Usuário cadastrado com sucesso!");
    }

    
    public void listUsers() {
        if (users.isEmpty()) {
            System.out.println("\nNenhum usuário cadastrado.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int page = 0;
        int pageSize = 5;
        int totalPages = (int) Math.ceil((double) users.size() / pageSize);

        do {
            System.out.println("\n--- USUÁRIOS (Página " + (page + 1) + "/" + totalPages + ") ---");
            for (int i = page * pageSize; i < (page + 1) * pageSize && i < users.size(); i++) {
                System.out.println((i + 1) + ". " + users.get(i));
            }

            if (totalPages > 1) {
                System.out.print("\nPróxima página (n), Anterior (p), Voltar (v): ");
                String input = sc.nextLine().toLowerCase();
                if (input.equals("n") && page < totalPages - 1) page++;
                else if (input.equals("p") && page > 0) page--;
                else if (input.equals("v")) break;
            }
        } while (totalPages > 1);
    }

    
    public void searchUsers(Scanner scanner) {
        System.out.print("\nDigite o termo de busca: ");
        String term = scanner.nextLine().toLowerCase();

        List<User> results = users.stream()
            .filter(u -> u.getName().toLowerCase().contains(term) || 
                         u.getEmail().toLowerCase().contains(term))
            .toList();

        if (results.isEmpty()) {
            System.out.println("Nenhum usuário encontrado com '" + term + "'");
        } else {
            System.out.println("\n--- RESULTADOS (" + results.size() + ") ---");
            results.forEach(System.out::println);
        }
    }

    
    public void editUser(Scanner scanner) {
        if (users.isEmpty()) {
            System.out.println("\nNenhum usuário cadastrado para editar.");
            return;
        }

        listUsers();
        System.out.print("\nDigite o número do usuário para editar: ");

        try {
            int userNumber = Integer.parseInt(scanner.nextLine());
            if (userNumber < 1 || userNumber > users.size()) {
                System.out.println("❌ Número inválido!");
                return;
            }

            User user = users.get(userNumber - 1);
            System.out.println("\nEditando: " + user);

            
            System.out.print("Novo nome (Enter para manter atual): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                user.setName(newName);
            }

            
            String newEmail;
            do {
                System.out.print("Novo e-mail (Enter para manter atual): ");
                    newEmail = scanner.nextLine();
                if (!newEmail.isEmpty()) {
                    if (!isValidEmail(newEmail)) {
                        System.out.println("❌ E-mail inválido!");
                    newEmail = "";
            } else {
            final String finalNewEmail = newEmail;
            if (users.stream()
                  .anyMatch(u -> u.getEmail().equalsIgnoreCase(finalNewEmail) && 
                               !u.equals(user))) {
                System.out.println("❌ E-mail já está em uso!");
                newEmail = "";
            } else {
                user.setEmail(newEmail);
            }
        }
    }
} while (!newEmail.isEmpty() && !isValidEmail(newEmail));

            
            System.out.print("Nova idade (0 para manter atual): ");
            String ageInput = scanner.nextLine();
            if (!ageInput.isEmpty()) {
                try {
                    int newAge = Integer.parseInt(ageInput);
                    if (newAge > 0) {
                        user.setAge(newAge);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Idade não alterada (valor inválido)");
                }
            }

            System.out.println("✅ Usuário atualizado:\n" + user);

        } catch (NumberFormatException e) {
            System.out.println("❌ Digite um número válido!");
        }
    }

    
    public void deleteUser(Scanner scanner) {
        if (users.isEmpty()) {
            System.out.println("\nNenhum usuário cadastrado para excluir.");
            return;
        }

        listUsers();
        System.out.print("\nDigite o número do usuário para excluir: ");

        try {
            int userNumber = Integer.parseInt(scanner.nextLine());
            if (userNumber < 1 || userNumber > users.size()) {
                System.out.println("❌ Número inválido!");
                return;
            }

            User removedUser = users.remove(userNumber - 1);
            System.out.println("🗑️ Usuário removido:\n" + removedUser);

        } catch (NumberFormatException e) {
            System.out.println("❌ Digite um número válido!");
        }
    }

    public void sortUsersByName() {
        users.sort(Comparator.comparing(User::getName));
        System.out.println("🔠 Usuários ordenados por nome.");
    }
}
