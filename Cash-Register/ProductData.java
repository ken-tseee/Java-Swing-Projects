/**
 * A class to hold information about products that can be purchased at a store. 
 * @author Junjian Xie
 * Andrew ID: junjianx
 */
public class ProductData {
    private String item;
    private double price;
    
    public ProductData(String item, double price) {
        super();
        this.item = item;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        String priceStr = String.format("%.2f", price);
        StringBuilder sb = new StringBuilder();
        sb.append("New item for sale: ")
          .append(item)
          .append(" at $ ")
          .append(priceStr)
          .append("\n");
        return sb.toString();
    }
}
