package models;

public class User extends Person {
    public User(int id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public String toString() {
        return "User ID: " + id + ", Username: " + username;
    }
}
