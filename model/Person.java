package model;

/**
 * Abstract superclass for all persons in the system.
 * Both Customer and Admin inherit from this class.
 */
public abstract class Person {

    // -------------------- Attributes --------------------
    private int personId;
    private String name;        // Acts as Username
    private String fullName;    // Added Full Name attribute
    private String phoneNumber;

    // -------------------- Constructor --------------------
    public Person(int personId, String name, String phoneNumber) {
        this.personId = personId;
        this.name = name;
        this.fullName = name; // Default full name to username initially
        this.phoneNumber = phoneNumber;
    }

    // -------------------- Getters --------------------

    public int getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // -------------------- Setters --------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // -------------------- Abstract Method --------------------

    /**
     * Displays profile information.
     * Implemented differently by Customer and Admin.
     */
    public abstract void displayProfile();
}