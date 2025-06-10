import java.util.Scanner;
import java.util.ArrayList;
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
        System.out.println("    USER MANAGEMENT SYSTEM 1.0      ");
        System.out.println("====================================\n");
    }
    
    private static void runMainMenu() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Register new user");
            System.out.println("2. List all users");
            System.out.println("3. Search users");
            System.out.println("4. Edit user");
            System.out.println("5. Delete user");
            System.out.println("6. Exit");
            System.out.print("Select option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Consume newline
                
                switch (choice) {
                    case 1:
                        userManager.registerUser(scanner);
                        break;
                    case 2:
                        userManager.listUsers();
                        break;
                    case 3:
                        userManager.searchUsers(scanner);
                        break;
                    case 4:
                        userManager.editUser(scanner);
                        break;
                    case 5:
                        userManager.deleteUser(scanner);
                        break;
                    case 6:
                        System.out.println("\nExiting system. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine(); //Clear invalid input
            }
        }
    }
}

class UserManager {
    private ArrayList<User> users = new ArrayList<>();
    
    public void registerUser(Scanner scanner) {
        System.out.println("\n--- USER REGISTRATION ---");
        
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        int age = getValidAge(scanner);
        
        users.add(new User(name, email, age));
        System.out.println("\n✅ User registered successfully!");
    }
    
    public void listUsers() {
        if (users.isEmpty()) {
            System.out.println("\nNo users registered yet.");
            return;
        }
        
        System.out.println("\n--- REGISTERED USERS (" + users.size() + ") ---");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i));
        }
    }
    
    public void searchUsers(Scanner scanner) {
        System.out.print("\nEnter search term: ");
        String term = scanner.nextLine().toLowerCase();
        
        ArrayList<User> results = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(term) || 
                user.getEmail().toLowerCase().contains(term)) {
                results.add(user);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No users found matching '" + term + "'");
        } else {
            System.out.println("\n--- SEARCH RESULTS (" + results.size() + ") ---");
            results.forEach(System.out::println);
        }
    }
    
    public void editUser(Scanner scanner) {
        if (users.isEmpty()) {
            System.out.println("\nNo users available to edit.");
            return;
        }
        
        listUsers();
        System.out.print("\nEnter user number to edit: ");
        
        try {
            int userNumber = scanner.nextInt();
            scanner.nextLine(); //Consume newline
            
            if (userNumber < 1 || userNumber > users.size()) {
                System.out.println("Invalid user number!");
                return;
            }
            
            User userToEdit = users.get(userNumber - 1);
            System.out.println("\nEditing user: " + userToEdit);
            
            System.out.print("New name (leave blank to keep current): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                userToEdit.setName(newName);
            }
            
            System.out.print("New email (leave blank to keep current): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                userToEdit.setEmail(newEmail);
            }
            
            System.out.print("New age (enter 0 to keep current): ");
            int newAge = scanner.nextInt();
            scanner.nextLine(); //Consume newline
            if (newAge > 0) {
                userToEdit.setAge(newAge);
            }
            
            System.out.println("\n✅ User updated successfully!");
            System.out.println("Updated info: " + userToEdit);
            
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number!");
            scanner.nextLine(); //Clear invalid input
        }
    }
    
    public void deleteUser(Scanner scanner) {
        if (users.isEmpty()) {
            System.out.println("\nNo users available to delete.");
            return;
        }
        
        listUsers();
        System.out.print("\nEnter user number to delete: ");
        
        try {
            int userNumber = scanner.nextInt();
            scanner.nextLine(); //Consume newline
            
            if (userNumber < 1 || userNumber > users.size()) {
                System.out.println("Invalid user number!");
                return;
            }
            
            User deletedUser = users.remove(userNumber - 1);
            System.out.println("\n🗑️ User deleted: " + deletedUser);
            System.out.println("Remaining users: " + users.size());
            
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number!");
            scanner.nextLine(); //Clear invalid input
        }
    }
    
    private int getValidAge(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter age: ");
                int age = scanner.nextInt();
                scanner.nextLine(); //Consume newline
                
                if (age <= 0) {
                    System.out.println("Age must be positive!");
                } else {
                    return age;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number for age!");
                scanner.nextLine(); //Clear invalid input
            }
        }
    }
}

class User {
    private String name;
    private String email;
    private int age;
    
    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    //Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    
    //Setters for editing
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) { this.age = age; }
    
    @Override
    public String toString() {
        return String.format("%s (%d) - %s", name, age, email);
    }
}