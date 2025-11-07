/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package folder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.text.*;


/**
 *
 * @author USER
 */
public class ReceiptGUI {
    private JFrame frame;
    private JPanel panel;
    private JButton addButton;
    private JButton printButton;
    private JTextArea receiptTextArea;
    private Receipt receipt;
    private JTable itemTable;
    private JScrollPane tableScrollPane;
    private JTextField quantityField; 
    private final String ITEMS_FILE_PATH = "C:\\Users\\USER\\OneDrive\\Desktop\\OOP Sem May\\ProjectOOP\\src\\main\\java\\folder\\item.txt";

    public ReceiptGUI() {
        receipt = new Receipt();
        frame = new JFrame("7-Eleven Shop");
        panel = new JPanel();
        addButton = new JButton("Add Item");
        printButton = new JButton("Print Receipt");
        receiptTextArea = new JTextArea(50, 50);
        receiptTextArea.setEditable(false);
       
        itemTable = new JTable(); // Initialize the itemTable here
        tableScrollPane = new JScrollPane(itemTable); // Initialize the scroll pane with the itemTable
        
        loadItemsFromFile();
        
        quantityField = new JTextField("0", 10); // Set default quantity to 0

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = itemTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String itemName = (String) itemTable.getValueAt(selectedRow, 0);
                    double itemPrice = (double) itemTable.getValueAt(selectedRow, 1);
                    String quantityStr = quantityField.getText();

                    // Check if the quantity is a valid integer
                    int quantity;
                    try {
                        quantity = Integer.parseInt(quantityStr);
                    } catch (NumberFormatException ex) {
                        // Show an error message if parsing fails
                        JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(frame, "Quantity should be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Item item = new Item(itemName, itemPrice);
                    if (item != null) {
                        receipt.addItem(item, quantity);
                        receiptTextArea.setText(receipt.generateReceipt());
                        quantityField.setText("0");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid item!");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select an item from the table!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 saveReceiptToFile();
            }
        });
    }
    
    private void saveReceiptToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int userChoice = fileChooser.showSaveDialog(frame);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(receipt.generateReceipt());
                writer.close();
                JOptionPane.showMessageDialog(frame, "Receipt saved to: " + filePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving receipt to file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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

            // Create the data and columnNames arrays to populate the JTable
            Object[][] data = new Object[itemList.size()][2];
            String[] columnNames = { "Item", "Price" };

            for (int i = 0; i < itemList.size(); i++) {
                Item item = itemList.get(i);
                data[i][0] = item.getName();
                data[i][1] = item.getPrice();
            }

            // Create a new DefaultTableModel and set it to the JTable
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            itemTable.setModel(model);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "File not found: " + ITEMS_FILE_PATH, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void show() {
         // Set the layout of the main panel to BorderLayout
        panel.setBounds(10, 10, 700, 700);

        // Create a new panel to hold the top section with the table and quantity input
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(tableScrollPane);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between the table and the next component
        topPanel.add(new JLabel("Enter Quantity:"));
        topPanel.add(quantityField);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between the quantity input and the next component
        topPanel.add(addButton);

        // Set the preferred size for the top panel (optional)
        int topPanelWidth = 400; // Set your desired width here
        int topPanelHeight = 300; // Set your desired height here
        topPanel.setPreferredSize(new Dimension(topPanelWidth, topPanelHeight));

        // Add the top panel to the main panel at the NORTH position
        panel.add(topPanel, BorderLayout.NORTH);

        // Create a new panel to hold the receipt text area and print button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        bottomPanel.add(new JScrollPane(receiptTextArea));
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between the receipt text area and the print button
        bottomPanel.add(printButton);

        // Set the preferred size for the bottom panel (optional)
        int bottomPanelWidth = 400; // Set your desired width here
        int bottomPanelHeight = 300; // Set your desired height here
        bottomPanel.setPreferredSize(new Dimension(bottomPanelWidth, bottomPanelHeight));

        // Add the bottom panel to the main panel at the CENTER position
        panel.add(bottomPanel, BorderLayout.CENTER);

        // Set the size of the main frame
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}