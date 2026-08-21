package model;

/**
 * Admin class inherits from Person.
 * Represents the restaurant administrator.
 */
public class Admin extends Person {

    // ===========================
    // Attributes
    // ===========================

    private String username;
    private String password;

    // ===========================
    // Constructor
    // ===========================

    public Admin(int personId,
                 String name,
                 String phoneNumber,
                 String username,
                 String password) {

        super(personId, name, phoneNumber);

        this.username = username;
        this.password = password;

    }

    /**
     * Overloaded constructor if you want to initialize with a specific full name immediately.
     */
    public Admin(int personId,
                 String name,
                 String fullName,
                 String phoneNumber,
                 String username,
                 String password) {

        super(personId, name, phoneNumber);
        setFullName(fullName);

        this.username = username;
        this.password = password;

    }

    // ===========================
    // Getters
    // ===========================

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // ===========================
    // Setters
    // ===========================

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ===========================
    // Display Profile
    // ===========================

    @Override
    public void displayProfile() {

        System.out.println("\n========== ADMIN ==========");
        System.out.println("ID        : " + getPersonId());
        System.out.println("Name      : " + getName());
        System.out.println("Full Name : " + getFullName());
        System.out.println("Phone     : " + getPhoneNumber());
        System.out.println("Username  : " + username);

    }

}