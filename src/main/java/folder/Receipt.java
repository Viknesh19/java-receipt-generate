/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package folder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Receipt {
    private List<Item> items;
    private List<Integer> quantities;
    private String current_date;
    private String current_time;

    public Receipt() {
        items = new ArrayList<>();
        quantities = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date currentDate = new Date();
        current_date = dateFormat.format(currentDate);
        current_time = timeFormat.format(currentDate);
    }

    public void addItem(Item item, int quantity) {
        items.add(item);
        quantities.add(quantity);
    }

    public void updateQuantity(int itemId, int newQuantity) {
        quantities.set(itemId - 1, newQuantity);
    }

    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int quantity = quantities.get(i);
            total += item.getPrice() * quantity;
        }
        return total;
    }

    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n7-Eleven Shop Receipt\n");
        sb.append("Date: ").append(current_date).append("\n");
        sb.append("Time: ").append(current_time).append("\n");
        sb.append("Item                       Quantity     Price       Total\n");
        sb.append("---------------------------------------------------------\n");

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int quantity = quantities.get(i);
            double itemTotal = item.getPrice() * quantity;

            sb.append(String.format("%2d. %-20s %8d %12.2f %12.2f\n",
                        i + 1, item.getName(), quantity, item.getPrice(), itemTotal));
        }

        sb.append("---------------------------------------------------------\n");
        sb.append(String.format("Total%55.2f\n", getTotalPrice()));
        sb.append("\nThank you for shopping at 7-Eleven Shop!");

        return sb.toString();
    }

    public void printReceipt() {
        String receiptMessage = generateReceipt();
        JOptionPane.showMessageDialog(null, receiptMessage, "Receipt", JOptionPane.PLAIN_MESSAGE);
    }

}

