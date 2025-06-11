import java.util.Objects;

public class User {
    private String name;
    private String email;
    private int age;

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return String.format("%s (%d anos) - %s", name, age, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email); //Um e-mail é único por usuário
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
