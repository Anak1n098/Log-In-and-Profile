public class User {
    private String name;
    private String email;
    private int age;
    private String password;
    private boolean isAdmin;

    public User(String name, String email, int age, String password, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.isAdmin = isAdmin;
    }

   
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }

  
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) { this.age = age; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return String.format("%s (%d anos) - %s%s", name, age, email, isAdmin ? " [ADMIN]" : "");
    }
}
