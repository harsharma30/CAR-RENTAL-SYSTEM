package models;

public class Admin extends Person {
    public Admin(int id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public String toString() {
        return "Admin ID: " + id + ", Username: " + username;
    }
}
