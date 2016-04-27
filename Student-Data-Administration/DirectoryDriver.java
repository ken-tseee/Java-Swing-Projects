import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * A Swing GUI to show Directory.
 */
public class DirectoryDriver extends JFrame {
    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Reference to desktop.
     */
    private JPanel desktop;
    /**
     * Reference to for adding panel.
     */
    private JPanel addPane;
    /**
     * Reference to deleting panel.
     */
    private JPanel deletePane;
    /**
     * Reference to searching panel.
     */
    private JPanel searchPane;
    /**
     * Reference to result panel.
     */
    private JPanel resultPane;
    /**
     * Reference to first name label.
     */
    private JLabel firstName;
    /**
     * Reference to last name label.
     */
    private JLabel lastName;
    /**
     * Reference to adding Andrew ID label.
     */
    private JLabel addAndrewId;
    /**
     * Reference to phone number label.
     */
    private JLabel phoneNumber;
    /**
     * Reference to deleting Andrew ID label.
     */
    private JLabel deleteAndrewId;
    /**
     * Reference to searching label.
     */
    private JLabel searchLabel;
    /**
     * Reference to button adding student.
     */
    private JButton addButton;
    /**
     * Reference to button deleting student.
     */
    private JButton deleteButton;
    /**
     * Reference to button searching by Andrew ID.
     */
    private JButton andrewIdButton;
    /**
     * Reference to button searching by first name.
     */
    private JButton firstButton;
    /**
     * Reference to button searching by last name.
     */
    private JButton lastButton;
    /**
     * Reference to text field for first name in adding panel.
     */
    private JTextField addFirstText;
    /**
     * Reference to text field for last name in adding panel.
     */
    private JTextField addLastText;
    /**
     * Reference to text field for Andrew ID in adding panel.
     */
    private JTextField addAndrewIdText;
    /**
     * Reference to text field for phone number in adding panel.
     */
    private JTextField addPhoneNumberText;
    /**
     * Reference to text field for Andrew ID in deleting panel.
     */
    private JTextField deleteAndrewIdText;
    /**
     * Reference to text field for searching key in searching panel.
     */
    private JTextField searchKeyText;
    /**
     * Reference to text area for result in result panel.
     */
    private JTextArea resultArea;
    /**
     * Reference to directory.
     */
    private Directory directory;

    /**
     * Constructor.
     * @param strings command line input
     */
    public DirectoryDriver(String[] strings) {
        super("Directory");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        if (strings.length == 0) {
            directory = new Directory();
        } else {
            String file = strings[0];
            LoadData loadData = new LoadData();
            directory = loadData.buildStudentObject(file);
        }

        desktop = new JPanel();
        addPane = new JPanel();
        deletePane = new JPanel();
        searchPane = new JPanel();
        resultPane = new JPanel();
        firstName = new JLabel("First Name:");
        lastName = new JLabel("Last Name:");
        addAndrewId = new JLabel("Andrew ID:");
        phoneNumber = new JLabel("Phone Number:");
        deleteAndrewId = new JLabel("Andrew ID:");
        searchLabel = new JLabel("Search Key:");
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        andrewIdButton = new JButton("By Andrew ID");
        firstButton = new JButton("By First Name");
        lastButton = new JButton("By Last Name");
        addFirstText = new JTextField(10);
        addLastText = new JTextField(10);
        addAndrewIdText = new JTextField(10);
        addPhoneNumberText = new JTextField(15);
        deleteAndrewIdText = new JTextField(10);
        searchKeyText = new JTextField(10);
        resultArea = new JTextArea(15, 80);

        TitledBorder addTitle = new TitledBorder("Add a new student");
        TitledBorder deleteTitle = new TitledBorder("Delete a student");
        TitledBorder searchTitle = new TitledBorder("Search student(s)");
        TitledBorder resultTitle = new TitledBorder("Results");
        Border border = BorderFactory.createLineBorder(Color.gray, 1);
        LayoutManager flowLayoutManager = new FlowLayout(FlowLayout.LEFT);
        flowLayoutManager.minimumLayoutSize(desktop);

        getContentPane().add(desktop);
        BoxLayout boxLayout = new BoxLayout(desktop, BoxLayout.Y_AXIS);
        desktop.setLayout(boxLayout);

        addPane.setBorder(addTitle);
        addFirstText.setBorder(border);
        addLastText.setBorder(border);
        addAndrewIdText.setBorder(border);
        addPhoneNumberText.setBorder(border);
        addPane.setLayout(flowLayoutManager);
        addPane.add(firstName);
        addPane.add(addFirstText);
        addPane.add(lastName);
        addPane.add(addLastText);
        addPane.add(addAndrewId);
        addPane.add(addAndrewIdText);
        addPane.add(phoneNumber);
        addPane.add(addPhoneNumberText);
        addPane.add(addButton);
        desktop.add(addPane, 0);

        deletePane.setBorder(deleteTitle);
        deleteAndrewIdText.setBorder(border);
        deletePane.setLayout(flowLayoutManager);
        deletePane.add(deleteAndrewId);
        deletePane.add(deleteAndrewIdText);
        deletePane.add(deleteButton);
        desktop.add(deletePane, 1);

        searchPane.setBorder(searchTitle);
        searchKeyText.setBorder(border);
        searchPane.setLayout(flowLayoutManager);
        searchPane.add(searchLabel);
        searchPane.add(searchKeyText);
        searchPane.add(andrewIdButton);
        searchPane.add(firstButton);
        searchPane.add(lastButton);
        desktop.add(searchPane, 2);

        resultPane.setBorder(resultTitle);
        resultArea.setBorder(border);
        resultPane.setLayout(flowLayoutManager);
        resultPane.add(resultArea);
        JScrollPane scroll = new JScrollPane(resultArea);
        resultPane.add(scroll);
        desktop.add(resultPane);

        /*
         * Anonymous class that put focus in the search key text field
         * when window opens
         */
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                searchKeyText.requestFocusInWindow();
            }
        });

        /**
         * Manage the event that search s student by Andrew ID.
         */
        @SuppressWarnings("serial")
        Action enterPressed = new AbstractAction() {
            /**
             * Invoke the method responding to the enter-pressing event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                enterPressed();
            }
        };

        addButton.addActionListener(new AddActionListener(resultArea));
        deleteButton.addActionListener(new DeleteActionListener(resultArea));
        andrewIdButton.addActionListener(new SearchActionListener(resultArea));
        firstButton.addActionListener(new SearchActionListener(resultArea));
        lastButton.addActionListener(new SearchActionListener(resultArea));
        searchKeyText.addActionListener(enterPressed);

        pack();
        setVisible(true);
    }

    /**
     * Private nested class to provide actionPerformed() method for
     * adding students.
     * @author Junjian Xie
     * Andrew ID: junjianx
     */
    private class AddActionListener implements ActionListener {
        /**
         * Instance variable for text area.
         */
        private JTextArea textArea;
        /**
         * Instance variable for input of first name.
         */
        private String firstName;
        /**
         * Instance variable for input of last name.
         */
        private String lastName;
        /**
         * Instance variable for input of Andrew ID.
         */
        private String andrewId;
        /**
         * Instance variable for input of phone number.
         */
        private String phoneNumber;

        /**
         * Constructor.
         * @param textArea TextArea instance
         */
        AddActionListener(JTextArea textArea) {
            super();
            this.textArea = textArea;
        }

        /**
         * Add a Student object by first name, last name, Andrew Id
         * and phone number.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            firstName = addFirstText.getText();
            lastName = addLastText.getText();
            andrewId = addAndrewIdText.getText();
            phoneNumber = addPhoneNumberText.getText();
            if (e.getSource() == addButton) {
                if (firstName.equals("")) {
                    textArea.append("Add a student unsuccessfully!\n"
                                    + "First Name is miss!\n\n");
                    return;
                } else if (lastName.equals("")) {
                    textArea.append("Add a student unsuccessfully!\n"
                                    + "Last Name is miss!\n\n");
                    return;
                } else if (andrewId.equals("")) {
                    textArea.append("Add a student unsuccessfully!\n"
                                    + "Andrew ID is miss!\n\n");
                    return;
                } else {
                    Student student = directory.searchByAndrewId(andrewId);
                    if (student != null) {
                        textArea.append("Add a student unsuccessfully!\n"
                                        + "Data already contains an entry for"
                                        + "this Andrew ID!\n"
                                        + "Please try again!\n\n");
                        return;
                    } else {
                        addFirstText.setText("");
                        addLastText.setText("");
                        addAndrewIdText.setText("");
                        addPhoneNumberText.setText("");
                        if (phoneNumber.equals("")) {
                            phoneNumber = "N/A";
                        }
                        Student newStudent = new Student(firstName, lastName,
                                                        andrewId, phoneNumber);
                        directory.addStudent(newStudent);
                        textArea.append("Add a student successfully!\nResult:"
                                      + "\n" + newStudent.toString() + "\n\n");
                        return;
                    }
                }
            }

            throw new AssertionError("Unknown event!");
        }
    }

    /**
     * Private nested class to provide actionPerformed() method for
     * deleting students.
     * @author Junjian Xie
     * Andrew ID: junjianx
     */
    private class DeleteActionListener implements ActionListener {
        /**
         * Instance variable for text area.
         */
        private JTextArea textArea;
        /**
         * Instance variable for input text.
         */
        private String text;

        /**
         * Constructor.
         * @param textArea TextArea instance
         */
        DeleteActionListener(JTextArea textArea) {
            super();
            this.textArea = textArea;
        }

        /**
         * Delete by Andrew ID.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            text = deleteAndrewIdText.getText();
            if (e.getSource() == deleteButton) {
                if (text.equals("")) {
                    textArea.append("Delete a student unsuccessfully!\n"
                                    + "Andrew ID cannot be empty!\n"
                                    + "Please try again!\n\n");
                    return;
                } else {
                    deleteAndrewIdText.setText("");
                    textArea.append("Delete by Andrew ID: " + text
                            + "\nResult:\n");
                    Student student = directory.searchByAndrewId(text);
                    if (student == null) {
                        textArea.append("Search students unsuccessfully!\n"
                                + "No items match!\n"
                                + "Please check the input and try again!\n"
                                + "\n");
                        return;
                    } else {
                        textArea.append(student.toString() + "\n");
                        directory.deleteStudent(text);
                        textArea.append("Delete successfully!\n\n");
                        return;
                    }
                }
            }

            throw new AssertionError("Unknown event!");
        }
    }

    /**
     * Private nested class to provide actionPerformed() method for
     * searching students by Andrew ID, first name and last name.
     * @author Junjian Xie
     * Andrew ID: junjianx
     */
    private class SearchActionListener implements ActionListener {
        /**
         * Instance variable for text area.
         */
        private JTextArea textArea;
        /**
         * Instance variable for input text.
         */
        private String text;

        /**
         * Constructor.
         * @param textArea TextArea instance
         */
        SearchActionListener(JTextArea textArea) {
            super();
            this.textArea = textArea;
        }

        /**
         * Search by Andrew ID, first name or last name.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            text = searchKeyText.getText();
            if (e.getSource() == andrewIdButton) {
                if (text.equals("")) {
                    textArea.append("Search students unsuccessfully!\n"
                            + "Search Key cannot be empty! Please try again!\n"
                            + "\n");
                    return;
                } else {
                    searchKeyText.setText("");
                    textArea.append("Search by Andrew ID: " + text
                            + "\nResult:\n");
                    Student student = directory.searchByAndrewId(text);
                    if (student == null) {
                        textArea.append("Search students unsuccessfully!\n");
                        textArea.append("No items match!\n"
                                + "Please check the input and try again!\n\n");
                        return;
                    } else {
                        textArea.append(student.toString() + "\n\n");
                        return;
                    }
                }
            }

            if (e.getSource() == firstButton) {
                if (text.equals("")) {
                    textArea.append("Search students unsuccessfully!\n"
                            + "Search Key cannot be empty! Please try again!\n"
                            + "\n");
                    return;
                } else {
                    searchKeyText.setText("");
                    textArea.append("Search by first name: " + text
                            + "\nResult:\n");
                    try {
                        List<Student> list = directory.searchByFirstName(text);
                        if (list.size() == 0) {
                            textArea.append("Search students unsuccessfully!\n"
                                          + "No items match!\n"
                                          + "Please check the input "
                                          + "and try again!\n\n");
                        } else {
                            for (Student student : list) {
                                textArea.append(student.toString() + "\n");
                            }
                            textArea.append("\n");
                        }
                    } catch (CloneNotSupportedException e1) {
                        e1.printStackTrace();
                    }
                    return;
                }
            }

            if (e.getSource() == lastButton) {
                if (text.equals("")) {
                    textArea.append("Search students unsuccessfully!\n"
                            + "Search Key cannot be empty! Please try again!\n"
                            + "\n");
                    return;
                } else {
                    searchKeyText.setText("");
                    textArea.append("Search by last name: " + text
                                    + "\nResult:\n");
                    try {
                        List<Student> list = directory.searchByLastName(text);
                        if (list.size() == 0) {
                            textArea.append("Search students unsuccessfully!"
                                            + "\n");
                            textArea.append("No items match!\n"
                                    + "Please check the input and try again!\n"
                                    + "\n");
                        } else {
                            for (Student student : list) {
                                textArea.append(student.toString() + "\n");
                            }
                            textArea.append("\n");
                        }
                    } catch (CloneNotSupportedException e1) {
                        e1.printStackTrace();
                    }
                    return;
                }
            }

            throw new AssertionError("Unknown event!");
        }
    }

    /**
     * Search a student by Andrew ID when enter is pressed.
     */
    private void enterPressed() {
        String text = searchKeyText.getText();
        if (text.equals("")) {
            resultArea.append("Search students unsuccessfully!\n"
                    + "Search Key cannot be empty! Please try again!\n\n");
            return;
        } else {
            searchKeyText.setText("");
            resultArea.append("Search by Andrew ID: " + text
                    + "\nResult:\n");
            Student student = directory.searchByAndrewId(text);
            if (student == null) {
                resultArea.append("Search students unsuccessfully!\n");
                resultArea.append("No items match!\n"
                        + "Please check the input and try again!\n\n");
                return;
            } else {
                resultArea.append(student.toString() + "\n\n");
                return;
            }
        }
    }

    /**
     * Main method to show Swing GUI.
     * @param args arguments
     */
    public static void main(String[] args) {
        new DirectoryDriver(args);
    }
}
