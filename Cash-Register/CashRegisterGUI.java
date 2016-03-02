import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A Swing GUI to show cash register.
 * @author Junjian Xie
 * Andrew ID: junjianx
 */
public class CashRegisterGUI {
    private JFrame frame;
    private JPanel main;
    private JPanel pane1;
    private JPanel pane2;
    private JPanel pane3;
    private JScrollPane pane4;
    private JLabel newLabel;
    private JLabel priceLabel;
    private JLabel quantityLabel;
    private JLabel itemNameLabel;
    private JLabel cashLabel;
    private JTextField newField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField itemNameField;
    private JTextField cashField;
    private JButton add2StoreBtn;
    private JButton add2BillBtn;
    private JButton payBtn;
    private JButton clearBtn;
    private JTextArea resultArea;
    private Map<String, ProductData> itemMap;
    private double total = 0;
    
    private static enum ErrorType {
        QUANTITY_NOT_INTEGER, PRICE_NOT_FLOAT, MINUS_PRICE,
        CASH_TENDERED_NOT_FLOAT, ITEM_EXIST, NO_SUCH_ITEM
    }
    
    public CashRegisterGUI() {
        itemMap = new HashMap<String, ProductData>();
        initGUI();
    }
    
    private void initGUI() {
        frame = new JFrame("Java Cash Register");
        frame.setSize(720, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main = new JPanel();
        
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        BoxLayout bLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(bLayout);
        
        pane1 = new JPanel();
        newLabel = new JLabel("Name of New Item to Sell:");
        pane1.add(newLabel);
        newField = new JTextField(8);
        pane1.add(newField);
        priceLabel = new JLabel("Price:");
        pane1.add(priceLabel);
        priceField = new JTextField(8);
        pane1.add(priceField);
        add2StoreBtn = new JButton("Add Item to Store");
        add2StoreBtn.addActionListener(e -> {
            add2StoreBtnClicked();
        });
        pane1.add(add2StoreBtn);
        main.add(pane1);
        
        pane2 = new JPanel();
        quantityLabel = new JLabel("Quantity Being Purchased:");
        pane2.add(quantityLabel);
        quantityField = new JTextField(8);
        pane2.add(quantityField);
        itemNameLabel = new JLabel("Item Name:");
        pane2.add(itemNameLabel);
        itemNameField = new JTextField(8);
        pane2.add(itemNameField);
        add2BillBtn = new JButton("Add Item to Customer Bill");
        add2BillBtn.addActionListener(e -> {
            add2BillBtnClicked();
        });
        pane2.add(add2BillBtn);
        main.add(pane2);
        
        pane3 = new JPanel();
        cashLabel = new JLabel("Cash Tendered");
        pane3.add(cashLabel);
        cashField = new JTextField(8);
        pane3.add(cashField);
        payBtn = new JButton("Pay");
        payBtn.addActionListener(e -> {
            payBtnClicked();
        });
        pane3.add(payBtn);
        clearBtn = new JButton("Clear Text Area");
        clearBtn.addActionListener(e -> {
            clearResultArea();
        });
        pane3.add(clearBtn);
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
    
    private void add2StoreBtnClicked() {
        String item = newField.getText();
        if (item.trim().equals("")) {
            resultArea.append("*** New Item Field should not be empty! ***\n");
            return;
        }
        String priceStr = priceField.getText();
        if (priceStr.trim().equals("")) {
            resultArea.append("*** Price Field should not be empty! ***\n");
            return;
        }
        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                errorDisplay(ErrorType.MINUS_PRICE);
                return;
            }
            if (!itemMap.containsKey(item)) {
                newField.setText("");
                priceField.setText("");
                ProductData object = new ProductData(item, price);
                itemMap.put(item, object);
                resultArea.append(object.toString());
            } else {
                errorDisplay(ErrorType.ITEM_EXIST);
                return;
            }
        } catch (NumberFormatException e) {
            errorDisplay(ErrorType.PRICE_NOT_FLOAT);
            return;
        }
    }
    
    private void add2BillBtnClicked() {
        String itemName = itemNameField.getText();
        if (itemName.trim().equals("")) {
            resultArea.append("*** Item Name Field should not be empty! ***"
                            + "\n");
            return;
        }
        String quantityStr = quantityField.getText();
        if (quantityStr.trim().equals("")) {
            resultArea.append("*** Quantity Field should not be empty! ***\n");
            return;
        }
        try {
            int quantity = Integer.parseInt(quantityStr);
            if (itemMap.containsKey(itemName)) {
                quantityField.setText("");
                itemNameField.setText("");
                ProductData object = itemMap.get(itemName);
                double price = object.getPrice();
                double subtotal = price * quantity;
                total += subtotal;
                String priceStr = String.format("%.2f", price);
                String subtotalStr = String.format("%.2f", subtotal);
                String result = "\t\t\t\t"
                              + quantityStr
                              + "  "
                              + itemName
                              + "\t"
                              + " at $ "
                              + priceStr
                              + " each"
                              + "\t $ "
                              + subtotalStr;
                resultArea.append(result + "\n");
            } else {
                errorDisplay(ErrorType.NO_SUCH_ITEM);
                return;
            }
        } catch (NumberFormatException e) {
            errorDisplay(ErrorType.CASH_TENDERED_NOT_FLOAT);
            return;
        }
    }
    
    private void payBtnClicked() {
        if (total == 0) {
            resultArea.append("*** Payment is invalid because nothing is "
                            + "purchased! ***\n");
            return;
        }
        String cashStr = cashField.getText();
        if (cashStr.trim().equals("")) {
            resultArea.append("*** Cash Tendered Field should not be empty! "
                            + "***\n");
            return;
        }
        try {
            double cash = Double.parseDouble(cashStr);
            if (cash < total) {
                notEnoughMoneyError(total);
                return;
            }
            cashField.setText("");
            double change = cash - total;
            String totalStr = String.format("%.2f", total);
            String cashString = String.format("%.2f", cash);
            String changeStr = String.format("%.2f", change);
            resultArea.append("\nTotal\t\t$ "
                            + totalStr
                            + "\nCash Tendered\t$ "
                            + cashString
                            + "\nChange\t\t$ "
                            + changeStr
                            + "\n");
            total = 0;
        } catch (NumberFormatException e) {
            errorDisplay(ErrorType.CASH_TENDERED_NOT_FLOAT);
            return;
        }
    }
    
    private void errorDisplay(ErrorType errorType) {
        String errStr;
        switch (errorType) {
        case QUANTITY_NOT_INTEGER:
            errStr = "*** The quantity should be an integer! ***\n";
            break;
        case PRICE_NOT_FLOAT:
            errStr = "*** The price should be a floating point number! ***\n";
            break;
        case MINUS_PRICE:
            errStr = "*** The price should be greater than zero! ***\n";
            break;
        case CASH_TENDERED_NOT_FLOAT:
            errStr = "*** The cash tendered should be a floating point! ***\n";
            break;
        case NO_SUCH_ITEM:
            errStr = "*** No such item in the store! ***\n";
            break;
        case ITEM_EXIST:
            errStr = "*** This item is already existing! ***\n";
            break;
        default:
            errStr = "*** Unknown error! ***\n";
        }
        resultArea.append(errStr);
    }
    
    private void notEnoughMoneyError(double total) {
        String totalStr = String.format("%.2f", total);
        String errStr = "*** Not enough cash tendered. Must be at least $ "
                      + totalStr + " ***\n";
        resultArea.append(errStr);
    }
    
    private void clearResultArea() {
        resultArea.setText("");
    }
    
    public static void main(String[] args) {
        new CashRegisterGUI();
    }
}
