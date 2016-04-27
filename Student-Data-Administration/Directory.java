import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A class to model a collection of students using Maps.
 */
public class Directory {
    /**
     * Instance map for Andrew IDs as keys and Student objects as values.
     */
    private HashMap<String, Student> andrewIdMap;
    /**
     * Instance map for first names as keys and Student objects as values.
     */
    private HashMap<String, List<Student>> firstNameMap;
    /**
     * Instance map for last names as keys and Student objects as values.
     */
    private HashMap<String, List<Student>> lastNameMap;

    /**
     * Constructor without arguments.
     */
    public Directory() {
        andrewIdMap = new HashMap<String, Student>();
        firstNameMap = new HashMap<String, List<Student>>();
        lastNameMap = new HashMap<String, List<Student>>();
    }

    /**
     * Adds a student object into the directory.
     * @param student Student object
     * @throws IllegalArgumentException If the student's Andrew ID is present
     */
    public void addStudent(Student student) throws IllegalArgumentException {
        if (student == null) {
            throw new IllegalArgumentException();
        }
        String andrewId = student.getAndrewId();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String phoneNumber = student.getPhoneNumber();

        Student copyStudent =
                        new Student(firstName, lastName, andrewId, phoneNumber);

        andrewIdMap.put(andrewId, copyStudent);

        if (!firstNameMap.containsKey(firstName)) {
            List<Student> firstNameList = new LinkedList<Student>();
            firstNameList.add(copyStudent);
            firstNameMap.put(firstName, firstNameList);
        } else {
            firstNameMap.get(firstName).add(copyStudent);
        }

        if (!lastNameMap.containsKey(lastName)) {
            List<Student> lastNameList = new LinkedList<Student>();
            lastNameList.add(copyStudent);
            lastNameMap.put(lastName, lastNameList);
        } else {
            lastNameMap.get(lastName).add(copyStudent);
        }
    }

    /**
     * Removes the corresponding student object from the directory.
     * @param andrewId andrewId Andrew ID
     * @throws IllegalArgumentException If no andrew id matches.
     */
    public void deleteStudent(String andrewId) throws IllegalArgumentException {
        if (andrewId == null) {
            throw new IllegalArgumentException();
        }

        if (!andrewIdMap.containsKey(andrewId)) {
            throw new IllegalArgumentException();
        } else {
            Student student = andrewIdMap.get(andrewId);
            String firstName = student.getFirstName();
            String lastName = student.getLastName();

            List<Student> firstNameList = firstNameMap.get(firstName);
            for (Student student1 : firstNameList) {
                if (student1.getAndrewId().equals(andrewId)) {
                    firstNameList.remove(student1);
                }
            }

            List<Student> lastNameList = lastNameMap.get(lastName);
            for (Student student2 : lastNameList) {
                if (student2.getAndrewId().equals(andrewId)) {
                    lastNameList.remove(student2);
                }
            }

            andrewIdMap.remove(andrewId);
        }
    }

    /**
     * Returns a list containing the student in the directory.
     * @param andrewId Andrew ID
     * @return Student object
     */
    public Student searchByAndrewId(String andrewId) {
        if (andrewId == null) {
            throw new IllegalArgumentException();
        }
        if (andrewIdMap.containsKey(andrewId)) {
            Student student = andrewIdMap.get(andrewId);
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            String phoneNumber = student.getPhoneNumber();
            Student copyStudent = new Student(firstName, lastName,
                                                        andrewId, phoneNumber);
            return copyStudent;
        } else {
            return null;
        }
    }

    /**
     * Returns a list containing all students that match the given first name.
     * @param firstName first name
     * @return a list of Student objects
     * @throws CloneNotSupportedException If clone is not supported.
     */
    public List<Student> searchByFirstName(String firstName)
                                            throws CloneNotSupportedException {
        if (firstName == null) {
            throw new IllegalArgumentException();
        }
        if (firstNameMap.containsKey(firstName)) {
            List<Student> list = firstNameMap.get(firstName);
            List<Student> copyList = new LinkedList<Student>();
            for (Student student : list) {
                copyList.add((Student) student.clone());
            }
            return copyList;
        } else {
            return new LinkedList<Student>();
        }
    }

    /**
     * Returns a list containing all students that match the given last name.
     * @param lastName last name
     * @return a list of Student objects
     * @throws CloneNotSupportedException If clone is not supported.
     */
    public List<Student> searchByLastName(String lastName)
                                            throws CloneNotSupportedException {
        if (lastName == null) {
            throw new IllegalArgumentException();
        }
        if (lastNameMap.containsKey(lastName)) {
            List<Student> list = lastNameMap.get(lastName);
            List<Student> copyList = new LinkedList<Student>();
            for (Student student : list) {
                copyList.add((Student) student.clone());
            }
            return copyList;
        } else {
            return new LinkedList<Student>();
        }
    }

    /**
     * Returns the number of students in the directory.
     * @return size value in integer
     */
    public int size() {
        return andrewIdMap.size();
    }
}
