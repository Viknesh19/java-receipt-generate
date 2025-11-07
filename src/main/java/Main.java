/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReceiptGUI gui = new ReceiptGUI();
            gui.show();
        });
    }
}

class Item {
    private int id;
    private String name;
    private double price;

    public Item(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Receipt {
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

    public void printReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n7-Eleven Shop Receipt\n");
        sb.append("Date: ").append(current_date).append("\n");
        sb.append("Time: ").append(current_time).append("\n");
        sb.append("Item               Quantity     Price       Total\n");
        sb.append("-----------------------------------------------\n");

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int quantity = quantities.get(i);
            double itemTotal = item.getPrice() * quantity;

            sb.append(String.format("%2d.%-16s %11d %11.2f %11.2f\n",
                    i + 1, item.getName(), quantity, item.getPrice(), itemTotal));
        }

        sb.append("-----------------------------------------------\n");
        sb.append(String.format("Total%38.2f\n", getTotalPrice()));
        sb.append("\nThank you for shopping at 7-Eleven Shop!");

        System.out.println(sb.toString());
    }
}

class ReceiptGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JComboBox<String> itemComboBox;
    private JTextField quantityField;
    private JButton addButton;
    private JButton printButton;
    private Receipt receipt;

    public ReceiptGUI() {
        receipt = new Receipt();
        frame = new JFrame("7-Eleven Shop");
        panel = new JPanel();
        label = new JLabel("Select an item:");
        itemComboBox = new JComboBox<>(new String[]{
                "Bread", "Candy", "Water", "Ice Cream", "Chocolate",
                "Soft Drink", "Chips", "Biscuits", "Energy Drink", "Coffee"
        });
        quantityField = new JTextField(10);
        addButton = new JButton("Add Item");
        printButton = new JButton("Print Receipt");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) itemComboBox.getSelectedItem();
                int quantity = Integer.parseInt(quantityField.getText());
                Item item = getItemByName(selectedItem);
                if (item != null) {
                    receipt.addItem(item, quantity);
                    JOptionPane.showMessageDialog(frame, "Item added successfully!");
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid item!");
                }
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receipt.printReceipt();
            }
        });
    }

    public void show() {
        panel.add(label);
        panel.add(itemComboBox);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(addButton);
        panel.add(printButton);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private Item getItemByName(String name) {
        Item[] items = {
                new Item(1, "Bread", 2.50),
                new Item(2, "Candy", 1.00),
                new Item(3, "Water", 1.50),
                new Item(4, "Ice Cream", 2.00),
                new Item(5, "Chocolate", 3.50),
                new Item(6, "Soft Drink", 3.00),
                new Item(7, "Chips", 2.00),
                new Item(8, "Biscuits", 1.50),
                new Item(9, "Energy Drink", 4.00),
                new Item(10, "Coffee", 3.50)
        };

        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}
