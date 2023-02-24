package Garforth; /**
 * Jake Garforth
 * 2/10/23
 * CMSC 350 6381
 * This is the main file. Creates the window for the user to make and interact with the tree.
 * Main makes the frame by making the Project3Main object. In order to prevent errors in creating and accessing the
 * tree object created by the user, the tree is created via a reference object, then the referenced tree is passed to
 * all the listener methods. The make tree button listener is a lambda expression, to reduce code complexity given the
 * already somewhat convoluted way in which the program interacts with the object.
 */

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Project3Main extends JFrame {

    public static void main(String[] args) {
        Project3Main frame = new Project3Main();
        frame.pack();
        frame.setTitle("Binary Tree Categorizer");
        frame.setSize(720, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private Project3Main() {

        JPanel superPanel = new JPanel();
        superPanel.setLayout(new BoxLayout(superPanel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel entryLabel = new JLabel("Enter Tree: ");
        JTextField entryField = new JTextField(30);
        topPanel.add(entryLabel);
        topPanel.add(entryField);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());
        JButton makeTreeButton = new JButton("Make Tree");
        JButton isBalancedButton = new JButton("Is Balanced?");
        JButton isFullButton = new JButton("Is Full?");
        JButton isProperButton = new JButton("Is Proper?");
        JButton heightButton = new JButton("Height");
        JButton nodesButton = new JButton("Nodes");
        JButton inOrderButton = new JButton("Inorder");

        middlePanel.add(makeTreeButton);
        middlePanel.add(isBalancedButton);
        middlePanel.add(isFullButton);
        middlePanel.add(isProperButton);
        middlePanel.add(heightButton);
        middlePanel.add(nodesButton);
        middlePanel.add(inOrderButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        JLabel outputLabel = new JLabel("Output: ");
        JTextField outputField = new JTextField(30);
        outputField.setEditable(false);
        bottomPanel.add(outputLabel);
        bottomPanel.add(outputField);

        // Creates reference object to prevent errors when accessing tree
        var treeReference = new Object() {
            BinaryTree userTree = null;
        };


        makeTreeButton.addActionListener((ActionEvent e) -> {
            try {
                treeReference.userTree = new BinaryTree(entryField.getText());
                outputField.setText("The tree has been made!");
            } catch (InvalidTreeSyntaxException exceptionReason) {
                JOptionPane.showMessageDialog(this, exceptionReason.getMessage(), "CONVERSION ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        isBalancedButton.addActionListener((ActionEvent e) -> isBalancedButtonListener(outputField, treeReference.userTree));

        isFullButton.addActionListener((ActionEvent e) -> isFullButtonListener(outputField, treeReference.userTree));

        isProperButton.addActionListener((ActionEvent e) -> isProperButtonListener(outputField, treeReference.userTree));

        heightButton.addActionListener((ActionEvent e) -> heightButtonListener(outputField, treeReference.userTree));

        nodesButton.addActionListener((ActionEvent e) -> nodesButtonListener(outputField, treeReference.userTree));

        inOrderButton.addActionListener((ActionEvent e) -> inOrderButtonListener(outputField, treeReference.userTree));

        superPanel.add(topPanel);
        superPanel.add(middlePanel);
        superPanel.add(bottomPanel);

        add(superPanel);
    }

    private void isBalancedButtonListener(JTextField outputField, BinaryTree userTree) {
        try {
            if (userTree.isBalanced(userTree.getRoot())) {
                outputField.setText("The Tree is Balanced.");
            } else {
                outputField.setText("The Tree is not Balanced.");
            }
        } catch (NullPointerException exceptionReason) {
            JOptionPane.showMessageDialog(this, "No tree has been made!", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }
    private void isFullButtonListener(JTextField outputField, BinaryTree userTree) {
        try {
            if (userTree.isFull(userTree.getRoot())) {
                outputField.setText("The Tree is Full.");
            } else {
                outputField.setText("The Tree is not Full.");
            }
        } catch (NullPointerException exceptionReason) {
            JOptionPane.showMessageDialog(this, "No tree has been made!", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void isProperButtonListener(JTextField outputField, BinaryTree userTree) {
        try {
            if (userTree.isProper(userTree.getRoot())) {
                outputField.setText("The Tree is Proper.");
            } else {
                outputField.setText("The Tree is not Proper.");
            }
        } catch (
                NullPointerException exceptionReason) {
            JOptionPane.showMessageDialog(this, "No tree has been made!", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void heightButtonListener(JTextField outputField, BinaryTree userTree) {
        try {
            outputField.setText(String.format("The height is %d.", userTree.findHeight(userTree.getRoot())));
        } catch (NullPointerException exceptionReason) {
            JOptionPane.showMessageDialog(this, "No tree has been made!", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void nodesButtonListener(JTextField outputField, BinaryTree userTree) {
        try {
            outputField.setText(String.format("There are %d nodes.", userTree.countNodes(userTree.getRoot())));
        } catch (NullPointerException exceptionReason) {
            JOptionPane.showMessageDialog(this, "No tree has been made!", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void inOrderButtonListener(JTextField outputField, BinaryTree userTree) {
        try {
            outputField.setText(userTree.inOrderString(userTree.getRoot()));
        } catch (NullPointerException exceptionReason) {
            JOptionPane.showMessageDialog(this, "No tree has been made!", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }
}