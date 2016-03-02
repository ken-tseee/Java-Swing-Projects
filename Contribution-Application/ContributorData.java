import java.text.DecimalFormat;

/**
 * This class holds a contributor object. 
 * @author Junjian Xie
 * Andrew ID: junjianx
 */
public class ContributorData {
    private String lastName;
    private String firstName;
    private int amount;
    private String candidate;
    
    public ContributorData(String lastName, String firstName, int amount, String candidate) {
        super();
        this.lastName = lastName;
        this.firstName = firstName;
        this.amount = amount;
        this.candidate = candidate;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAmount() {
        return amount;
    }

    public String getCandidate() {
        return candidate;
    }

    @Override
    public String toString() {
        String lName = lastName.trim();
        String fName = firstName.trim();
        if (lName.length() > 15) {
            lName = lName.substring(0, 15);
        }
        if (fName.length() > 15) {
            fName = fName.substring(0, 15);
        }
        
        StringBuilder name = new StringBuilder();
        name.append(lName)
            .append(", ")
            .append(fName);
        
        int maxLength = 32;
        for (int lenName = name.length(); lenName < maxLength; lenName += 8) {
            name.append("\t");
        }
        
        DecimalFormat formatter = new DecimalFormat("#,###");
        StringBuilder moneyString = new StringBuilder();
        moneyString.append("$ ")
                   .append(formatter.format(amount));
        StringBuilder retStr = new StringBuilder();
        retStr.append(name)
              .append("\t\t")
              .append(moneyString)
              .append("\t\t");
        if (amount < 10000) {
            retStr.append("\t");
        }
        retStr.append(candidate)
              .append("\n");
        return retStr.toString();
    }
}
