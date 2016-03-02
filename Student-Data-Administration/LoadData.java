import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class to load data from a CSV file.
 * @author Junjian Xie
 * Andrew ID: junjianx
 */
public class LoadData {

    /**
     * Build a Directory object with Student objects from a file.
     * @param fileName file name
     * @return Directory object
     */
    public Directory buildStudentObject (String fileName) {
        File file = new File(fileName);
        try {
            return fileReader(file);
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            return null;
        }
    }

    /**
     * Read a file into memory.
     * @param file file object
     * @return Directory object
     * @throws IOException if unsuccessful to read a file.
     */
    public Directory fileReader(File file) throws IOException {
        FileReader fr  = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        int size = readFirstLine(line);
        Directory directory = new Directory();
        while ((line = br.readLine()) != null) {
            String[] properties = readOneLine(line, size);
            String firstName = properties[0];
            String lastName = properties[1];
            String andrewId = properties[2];
            String phoneNumber = properties[3];
            Student student = new Student(firstName, lastName,
                                                andrewId, phoneNumber);
            directory.addStudent(student);
        }
        br.close();
        return directory;
    }

    /**
     * Process the first and determine the number of properties
     * a Student object should have.
     * @param s input String
     * @return the number of properties
     */
    public int readFirstLine(String s) {
        String[] firstLine = s.split(",");
        if (firstLine.length != 4) {
            return 0;
        }
        int len = firstLine.length;
        return len;
    }

    /**
     * Deal with one line of properties.
     * @param s input String
     * @param size property size
     * @return properties object
     */
    public String[] readOneLine(String s, int size) {
        String[] properties = new String[size];
        properties = s.split(",");
        for (int i = 0; i < properties.length; ++i) {
            properties[i] = properties[i].replaceAll("\"", "");
        }
        return properties;
    }
}
