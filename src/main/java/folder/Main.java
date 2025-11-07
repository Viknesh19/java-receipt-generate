/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package folder;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReceiptGUI gui = new ReceiptGUI();
            gui.show();
        });
    }
}
