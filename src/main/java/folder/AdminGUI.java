/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package folder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author USER
 */
public class AdminGUI {
    private JFrame frame;
    private JPanel panel;
    private JButton addItemButton;
    private JTable itemTable;
    private JScrollPane tableScrollPane;
    private JTextField itemNameField;
    private JTextField itemPriceField;
    private DefaultTableModel tableModel;

    private final String ITEMS_FILE_PATH = "C:\\Users\\USER\\OneDrive\\Desktop\\ProjectOOP\\src\\main\\java\\folder\\item.txt";

    public AdminGUI() {
        frame = new JFrame("Admin Panel");
        panel = new JPanel();
        addItemButton = new JButton("Add Item");
        itemTable = new JTable();
        tableScrollPane = new JScrollPane(itemTable);
        itemNameField = new JTextField(20);
        itemPriceField = new JTextField(10);

        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Item", "Price" });
        itemTable.setModel(tableModel);

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = itemNameField.getText();
                String itemPriceStr = itemPriceField.getText();

                // Check if the item price is a valid double
                double itemPrice;
                try {
                    itemPrice = Double.parseDouble(itemPriceStr);
                } catch (NumberFormatException ex) {
                    // Show an error message if parsing fails
                    JOptionPane.showMessageDialog(frame, "Invalid item price. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (itemName.trim().isEmpty() || itemPrice <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid item name and price.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                tableModel.addRow(new Object[] { itemName, itemPrice });
                itemNameField.setText("");
                itemPriceField.setText("");
                saveItemsToFile();
            }
        });

        loadItemsFromFile();
    }

    private void saveItemsToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE_PATH));
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                String itemName = (String) tableModel.getValueAt(row, 0);
                double itemPrice = (double) tableModel.getValueAt(row, 1);
                writer.write(itemName + "," + itemPrice + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving items to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadItemsFromFile() {
        try {
            List<Item> itemList = new ArrayList<>();
            try (Scanner scanner = new Scanner(new File(ITEMS_FILE_PATH))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String itemName = parts[0].trim();
                        double itemPrice = Double.parseDouble(parts[1].trim());
                        Item item = new Item(itemName, itemPrice);
                        itemList.add(item);
                    }
                }
            }

            for (Item item : itemList) {
                tableModel.addRow(new Object[] { item.getName(), item.getPrice() });
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "File not found: " + ITEMS_FILE_PATH, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void show() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(tableScrollPane);
        panel.add(new JLabel("Item Name:"));
        panel.add(itemNameField);
        panel.add(new JLabel("Item Price:"));
        panel.add(itemPriceField);
        panel.add(addItemButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
