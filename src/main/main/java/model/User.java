package main.main.java.model;

public class User {
    private int id;
    private String name;
    private String DNI;
    private String email;

    public User(){

    }
    public User(int id, String name, String DNI, String email) {
        this.DNI = DNI;
        this.email = email;
        this.id = id;
        this.name = name;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "DNI='" + DNI + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
