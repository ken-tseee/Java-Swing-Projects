/**
 * This is a class to model a single student including first name, last name,
 * Andrew ID, and phone number.
 * @author Junjian Xie
 * Andrew ID: junjianx
 */
public class Student implements Cloneable {
    /**
     * Instance variables for first name.
     */
    private String firstName;
    /**
     * Instance variables for last name.
     */
    private String lastName;
    /**
     * Instance variables for Andrew ID.
     */
    private String andrewId;
    /**
     * Instance variables for phone number.
     */
    private String phoneNumber;

    /**
     * Constructor with parameter Andrew ID.
     * @param andrewId Andrew ID
     */
    public Student(String andrewId) {
        super();
        this.andrewId = andrewId;
    }

    /**
     * Constructor with parameter Andrew ID, first name, last name
     * and phone number.
     * @param firstName first name
     * @param lastName last name
     * @param andrewId Andrew ID
     * @param phoneNumber phone number
     */
    public Student(String firstName, String lastName, String andrewId,
            String phoneNumber) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.andrewId = andrewId;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns first name value of a student object.
     * @return first name value in String.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name value of a student object.
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns last name value of a student object.
     * @return last name value in String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name value of a student object.
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns phone number value of a student object.
     * @return phone number value in String.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number value of a student object.
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns Andrew ID value of a student object.
     * @return Andrew ID in String
     */
    public String getAndrewId() {
        return andrewId;
    }

    /**
     * Returns String representation of a student object.
     * @return String representation of a student object
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " (Andrew ID: " + andrewId
                + ", Phone Number: " + phoneNumber + ")";
    }

    /**
     * Returns a cloned Object.
     * @throws CloneNotSupportedException If clone is not supported.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
