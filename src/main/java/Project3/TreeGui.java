/**********************************************************************************************************************
 *
 * Name: Jacob Jennings
 * Date: February 2, 2023
 * Class: CMSC 350
 * Project: Project 2
 * Professor: Dr. Romerl Elizes
 *
 * Class Description - Main creates the GUI for the Polynomial application. It collects the input from users and
 * displays the results of the Polynomial logic such as syntax, Strong Order, and Weak Order.
 *
 * The logic for computing the polynomial weak order is also included.
 *
 *********************************************************************************************************************/
package Project3;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jacob Jennings
 */
public class TreeGui extends JPanel {
    private static JTextField treeInputField;
    private static JTextField treeOutputField;
    BinaryTree binaryTree;

    public TreeGui() {
        JLabel enterTreeLabel = new JLabel("Enter Tree:");
        treeInputField = new JTextField();
        JButton makeTreeButton = new JButton("Make Tree");
        JButton isBalancedButton = new JButton("Is Balanced?");
        JButton isFullButton = new JButton("Is Full?");
        JButton isProperButton = new JButton("Is Proper?");
        JButton getHeightButton = new JButton("Height");
        JButton getNodesButton = new JButton("Nodes");
        JButton getInorderButton = new JButton("Inorder");
        JLabel resultLabelText = new JLabel("Output: ");
        treeOutputField = new JTextField();

        setPreferredSize(new Dimension(1000, 200));
        setLayout(null);

        add(enterTreeLabel);
        add(treeInputField);
        add(makeTreeButton);
        add(isBalancedButton);
        add(isFullButton);
        add(isProperButton);
        add(getHeightButton);
        add(getNodesButton);
        add(getInorderButton);
        add(resultLabelText);
        add(treeOutputField);
        enterTreeLabel.setBounds(320, 15, 120, 25);
        treeInputField.setBounds(405, 15, 190, 25);
        makeTreeButton.setBounds(20, 70, 130, 35);
        isBalancedButton.setBounds(155, 70, 130, 35);
        isFullButton.setBounds(290, 70, 130, 35);
        isProperButton.setBounds(425, 70, 130, 35);
        getHeightButton.setBounds(560, 70, 130, 35);
        getNodesButton.setBounds(695, 70, 130, 35);
        getInorderButton.setBounds(830, 70, 130, 35);
        resultLabelText.setBounds(340, 160, 100, 25);
        treeOutputField.setBounds(405, 160, 270, 25);

        makeTreeButton.addActionListener(e -> {
            String expression = treeInputField.getText();
            try {
                binaryTree = new BinaryTree(expression);
            } catch (InvalidTreeSyntax ex) {
                popException(ex);
            }

        });
        isBalancedButton.addActionListener(e -> {
            popMessage("Tree is balanced: " + binaryTree.isBalanced());
        });
        isFullButton.addActionListener(e -> {
            popMessage("Tree is full: " + binaryTree.isFull());
        });

        isProperButton.addActionListener(e -> {
            popMessage("Tree is proper: " + binaryTree.isProper());
        });
        getHeightButton.addActionListener(e -> {
            popMessage("Tree height: " + binaryTree.getHeight());
        });
        getNodesButton.addActionListener(e -> {
            popMessage("Tree nodes: " + binaryTree.getNodeCount());
        });
        getInorderButton.addActionListener(e -> {
            String expression = binaryTree.inOrder();
            treeOutputField.setText("");
            treeOutputField.setText(expression);
        });

    }

    /**
     * @param args
     */
    public static void main(String[] args){
        JFrame frame = new JFrame("Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TreeGui());
        frame.pack();
        frame.setVisible(true);
    }
    /**
     * Method popException displays polynomial syntax errors to user.
     *
     * @param e
     */
    private static void popException(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage() + "\nInput is probably invalid.");
    }
    private static void popMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}

