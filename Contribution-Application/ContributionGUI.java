import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class shows a contribution application UI.
 */
public class ContributionGUI {
    private JFrame frame;
    private JPanel main;
    private JPanel pane1;
    private JPanel pane2;
    private JPanel pane3;
    private JScrollPane pane4;
    private JLabel lastNameLabel;
    private JLabel firstNameLabel;
    private JLabel amountLabel;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField amountField;
    private JButton cont2ObamaBtn;
    private JButton cont2RomneyBtn;
    private JButton listObama;
    private JButton listRomney;
    private JTextArea resultArea;
    private List<ContributorData> obamaList;
    private List<ContributorData> romneyList;
    private boolean displayingList;
    private int totalAmount = 0;
    
    public ContributionGUI() {
        obamaList = new ArrayList<ContributorData>();
        romneyList = new ArrayList<ContributorData>();
        initGUI();
    }
    
    private void initGUI() {
        frame = new JFrame("Midterm Campaign Contribution Application");
        frame.setSize(720, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main = new JPanel();
        
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        BoxLayout bLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        FlowLayout fLayout = new FlowLayout(FlowLayout.CENTER, 20, 0);
        main.setLayout(bLayout);
        
        pane1 = new JPanel();
        lastNameLabel = new JLabel("Contributor Last Name:");
        pane1.add(lastNameLabel);
        lastNameField = new JTextField(8);
        pane1.add(lastNameField);
        firstNameLabel = new JLabel("First Name:");
        pane1.add(firstNameLabel);
        firstNameField = new JTextField(8);
        pane1.add(firstNameField);
        amountLabel = new JLabel("Amount:");
        pane1.add(amountLabel);
        amountField = new JTextField(8);
        pane1.add(amountField);
        main.add(pane1);
        
        pane2 = new JPanel();
        pane2.setLayout(fLayout);
        cont2ObamaBtn = new JButton("Contribute to Obama");
        cont2ObamaBtn.addActionListener(e -> {
           contBtnClicked(Candidate.OBAMA); 
        });
        pane2.add(cont2ObamaBtn);
        cont2RomneyBtn = new JButton("Contribute to Romney");
        cont2RomneyBtn.addActionListener(e -> {
            contBtnClicked(Candidate.ROMNEY);
        });
        pane2.add(cont2RomneyBtn);
        main.add(pane2);
        
        pane3 = new JPanel();
        pane3.setLayout(fLayout);
        listObama = new JButton("List Obama's Contributors");
        listObama.addActionListener(e -> {
            listBtnClicked(Candidate.OBAMA);
        });
        pane3.add(listObama);
        listRomney = new JButton("List Romney's Contributors");
        listRomney.addActionListener(e -> {
            listBtnClicked(Candidate.ROMNEY);
        });
        pane3.add(listRomney);
        main.add(pane3);
        
        resultArea = new JTextArea(20, 60);
        resultArea.setFont(font);
        resultArea.setEditable(false);
        pane4 = new JScrollPane(resultArea);
        main.add(pane4);
        
        frame.setContentPane(main);
        frame.pack();
        frame.setVisible(true);
    }

    private static enum Candidate {
        OBAMA, ROMNEY;
        
        public String toString() {
            switch (this) {
            case OBAMA:
                return "Obama";
            case ROMNEY:
                return "Romney";
            default:
                return "Unknown";
            }
        }
    }
    
    private static enum ErrorType {
        AMOUNT_NOT_INTEGER, GREAT_AMOUNT, MINUS_AMOUNT, LAST_NAME_MISSING,
        ILLEGAL_LAST_NAME, FIRST_NAME_MISSING, ILLEGAL_FIRST_NAME,
        AMOUNT_MISSING
    }
    
    private void listBtnClicked(Candidate candidate) {
        displayingList = true;
        clearResultArea();
        String str = "Contributors for " 
                   + candidate.toString() 
                   + " are shown as follows:\n\n";
        resultArea.append(str);
        printSortedList(candidate);
    }
    
    private void printSortedList(Candidate candidate) {
        List<ContributorData> list = new ArrayList<ContributorData>();
        if (candidate == Candidate.OBAMA) {
            list = obamaList;
        }
        if (candidate == Candidate.ROMNEY) {
            list = romneyList;
        }
        sortList(list);
        for (ContributorData object : list) {
            resultArea.append(object.toString());
            totalAmount += object.getAmount();
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        String str = "\nTotal contributions for "
                   + candidate.toString()
                   + ": $ "
                   + formatter.format(totalAmount)
                   + "\n";
        resultArea.append(str);
        totalAmount = 0;
    }
    
    private void sortList(List<ContributorData> list) {
        Collections.sort(list, (object1, object2) -> {
            return object1.getFirstName().compareTo(object2.getFirstName());
        });
        Collections.sort(list, (object1, object2) -> {
            return object1.getLastName().compareTo(object2.getLastName());
        });
        Collections.sort(list, (object1, object2) -> {
            return object2.getAmount() - object1.getAmount();
        });
    }
    
    private void contBtnClicked(Candidate candidate) {
        if (displayingList) {
            clearResultArea();
            displayingList = false;
        }
        
        if (noError()) {
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            String amountStr = amountField.getText();
            int amount = Integer.parseInt(amountStr);
            String candidateStr = candidate.toString();
            ContributorData object = new ContributorData(lastName, firstName, amount, candidateStr);
            
            List<ContributorData> list = new ArrayList<ContributorData>();
            if (candidate == Candidate.OBAMA) {
                list = obamaList;
            }
            if (candidate == Candidate.ROMNEY) {
                list = romneyList;
            }
            
            list.add(object);
            resultArea.append(object.toString());
            clearTextFields();
        }
    }
    
    private boolean noError() {
        try {
            long amountInt = Long.parseLong(amountField.getText());
            if (lastNameField.getText().equals("")) {
                errorDisplay(ErrorType.LAST_NAME_MISSING);
                return false;
            }
            if (illegalName(lastNameField)) {
                errorDisplay(ErrorType.ILLEGAL_LAST_NAME);
                return false;
            }
            if (firstNameField.getText().equals("")) {
                errorDisplay(ErrorType.FIRST_NAME_MISSING);
                return false;
            }
            if (illegalName(firstNameField)) {
                errorDisplay(ErrorType.ILLEGAL_FIRST_NAME);
                return false;
            }
            if (amountField.getText().equals("")) {
                errorDisplay(ErrorType.AMOUNT_MISSING);
                return false;
            }
            if (amountInt > 10000000) {
                errorDisplay(ErrorType.GREAT_AMOUNT);
                return false;
            }
            if (amountInt <= 0) {
                errorDisplay(ErrorType.MINUS_AMOUNT);
                return false;
            }
        } catch (NumberFormatException e) {
            errorDisplay(ErrorType.AMOUNT_NOT_INTEGER);
            return false;
        }
        return true;
    }
    
    private void errorDisplay(ErrorType errorType) {
        String errStr;
        switch (errorType) {
            case LAST_NAME_MISSING:
                errStr = "*** Last name should not be empty!\n";
                break;
            case ILLEGAL_LAST_NAME:
                errStr = "*** Last name contains illegal character!\n";
                break;
            case FIRST_NAME_MISSING:
                errStr = "*** First name should not be empty!\n";
                break;
            case ILLEGAL_FIRST_NAME:
                errStr = "*** First name contains illegal character!\n";
                break;
            case AMOUNT_MISSING:
                errStr = "*** Amount should not be empty!\n";
                break;
            case AMOUNT_NOT_INTEGER:
                errStr = "*** Amount should only be integer!\n";
                break;
            case GREAT_AMOUNT:
                errStr = "*** Amount should be less than or equal to $10,000,000!\n";
                break;
            case MINUS_AMOUNT:
                errStr = "*** Amount should be greater than zero!\n";
                break;
            default:
                errStr = "*** Unknown error!\n";
        }
        resultArea.append(errStr);
    }
    
    private boolean illegalName(JTextField jTextField) {
        String s = jTextField.getText().trim();
        if (s.length() == 0) {
            return true;
        }
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c < 'A' || c > 'Z' && c < 'a' || c > 'z') {
                return true;
            }
        }
        return false;
    }
    
    private void clearTextFields() {
        lastNameField.setText("");
        firstNameField.setText("");
        amountField.setText("");
    }
    
    private void clearResultArea() {
        resultArea.setText("");
    }
    
    public static void main(String[] args) {
        new ContributionGUI();
    }
}
