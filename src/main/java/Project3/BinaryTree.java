/**********************************************************************************************************************
 *
 * Name: Jacob Jennings
 * Date: February 20, 2023
 * Class: CMSC 350
 * Project: Project 3
 * Professor: Dr. Romerl Elizes
 *
 * Class Description - BinaryTree represents a logical Binary Tree with functionality to determine the characteristics
 * such as whether the tree is balanced, full, or full. It also calculates the height and number of nodes of the tree.
 * Finally, it traverses the tree Inorder and returns as a parenthesized string.
 *
 *
 *********************************************************************************************************************/
package Project3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BinaryTree {

    private final static int MAX_STRING_LENGTH = 22;
    private Node root;
    private int leftSize;
    private int rightSize;
    private final boolean isProperBoolean;
    private String outputString = "";


// -------------------------------------------------------------

    public BinaryTree(String inputString) {
        root = null;
        leftSize = 0;
        rightSize = 0;
        isProperBoolean = true;
        String outputString = "";
        parseTreeString(validateInput(inputString));
    }


    /**
     * @param ch character to be checked
     * @return boolean true if the character is a valid character for a binary tree
     */
    private static boolean isAlphaNumeric(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9');
    }

    /**
     * @param str      String to be checked for characters
     * @param ch       character to be used for comparison
     * @param ch_index index of the character to be used for comparison used in recursion
     * @return int of the number of matched characters in the string
     */
    private static int countChar(String str, char ch, int ch_index) {
        if (ch_index == str.length()) return 0;
        int count = 0;
        if (str.charAt(ch_index) == ch) count++;
        return count + countChar(str, ch, ch_index + 1);
    }

    /**
     * @return number of nodes in the tree
     */
    public int getNodeCount() {
        return leftSize + rightSize + 1;
    }

    /**
     * @return height of the tree
     */
    public int getHeight() {
        if (leftSize == 3 || rightSize == 3) return 2;
        if (leftSize == 2 || rightSize == 2) return 2;
        if (leftSize == 1 || rightSize == 1) return 1;
        //if (leftSize == 1 || rightSize == 1) return 1;
        return 0;
    }

    /**
     * @return boolean true if the tree is full; otherwise, false
     */
    public boolean isFull() {
        return (leftSize == 0 && rightSize == 0) || (leftSize + rightSize + 1 == 7) || ((leftSize + rightSize + 1 == 3) && rightSize != 0);

    }

    /**
     * @return boolean true if the tree is balanced; otherwise, false
     */
    public boolean isBalanced() {
        return (Math.abs(leftSize - rightSize) <= 1 || (leftSize == 0 && rightSize == 0));
    }

    /**
     * @return boolean true if the tree is proper; otherwise, false
     */
    public boolean isProper() {
        Node localRoot = root;

        return isProper(localRoot);
    }

    /**
     * @param localRoot root binary tree node
     * @return boolean true if the tree is proper; otherwise, false
     */
    public boolean isProper(Node localRoot) {
        if (localRoot.leftChild == null) return true;
        if (localRoot.rightChild == null) return false;
        else {
            boolean leftProper = isProper(localRoot.leftChild);
            boolean rightProper = isProper(localRoot.rightChild);
            return leftProper && rightProper;
        }
    }

    /**
     * @param id key for node to be inserted
     * @param dd data to be inserted into node
     */
    private void insert(int id, char dd) {
        Node newNode = new Node();
        newNode.iData = id;
        newNode.dData = dd;
        if (root == null) root = newNode;
        else {
            Node current = root;
            Node parent;
            while (true) {
                parent = current;
                if (id < current.iData) {
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = newNode;
                        return;
                    }

                } else {
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = newNode;
                        return;
                    }

                }
            }
        }
    }

    /**
     * @return String representation of the tree Inorder
     */
    public String inOrder() {
        outputString = "";
        inOrder(root);

        StringBuilder leftBuilder;
        //try {
        leftBuilder = new StringBuilder(outputString.substring(0, leftSize + 1));
        //} catch (StringIndexOutOfBoundsException e) {
        //    throw new InvalidTreeSyntax("BRUH");
        // }

        if (leftSize == 3) {
            leftBuilder.insert(0, "(((");
            leftBuilder.insert(4, ")");
            leftBuilder.insert(6, "(");
            leftBuilder.insert(8, "))");
            if (rightSize == 0) {
                leftBuilder.insert(11, ")");
            }
        }
        if (leftSize == 2 && rightSize == 0) {
            leftBuilder.insert(0, "((");
            leftBuilder.insert(3, ")");
            leftBuilder.insert(5, ")");
            leftBuilder.insert(7, ")");
        } else if (leftSize == 2 && rightSize > 0) {
            leftBuilder.insert(0, "((");
            leftBuilder.insert(3, ")");
            leftBuilder.insert(5, ")");
        }
        if (leftSize == 1 && rightSize == 0) {
            leftBuilder.insert(0, "((");
            leftBuilder.insert(3, ")");
            leftBuilder.insert(5, ")");
        } else if (leftSize == 1 && rightSize > 0) {
            leftBuilder.insert(0, "((");
            leftBuilder.insert(3, ")");
        }
        if (leftSize == 0) {
            leftBuilder.insert(0, "(");
        }
        if (leftSize == 0 && rightSize == 0) {
            leftBuilder.insert(2, ")");
        }
        //System.out.println(outputString.substring(leftSize + 1));
        //System.out.println(rightSize);
        StringBuilder rightBuilder = new StringBuilder(outputString.substring(leftSize + 1));
        if (rightSize == 3) {
            rightBuilder.insert(0, "((");
            rightBuilder.insert(3, ")");
            rightBuilder.insert(5, "(");
            rightBuilder.insert(7, "))");
        }
        if (rightSize == 2) {
            rightBuilder.insert(0, "((");
            rightBuilder.insert(3, ")");
            rightBuilder.insert(5, "))");
        }
        if (rightSize == 1) {
            rightBuilder.insert(0, "(");
            rightBuilder.insert(2, "))");
        }
        return leftBuilder.append(rightBuilder).toString();
    }

    /**
     * @param localRoot root binary tree node
     */
    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            outputString = outputString + localRoot.dData;
            inOrder(localRoot.rightChild);
        }
    }

    /**
     * @return int of the number of nodes in the left tree
     */
    private int setLeftCount() {
        Node current = root.leftChild;
        getSideCount(current, true);
        return leftSize;
    }

    /**
     * @return int of the number of nodes in the right tree
     */
    private int setRightCount() {
        Node current = root.rightChild;
        getSideCount(current, false);
        return rightSize;
    }

    /**
     * @param localRoot root binary tree node
     * @param isLeft    boolean to check if the tree is a left or right tree
     */
    private void getSideCount(Node localRoot, Boolean isLeft) {
        if (localRoot != null) {
            if (isLeft) {
                leftSize++;
            } else {
                rightSize++;
            }
            getSideCount(localRoot.leftChild, isLeft);
            getSideCount(localRoot.rightChild, isLeft);
        }
    }

    /**
     * @param inputString String inputo be checked for valid syntax
     * @return String that is valid
     */
    private String validateInput(String inputString) {
        int alphaNumericCount = 0;
        int leftParenthesesCount = 0;
        boolean lastCharAlphaNumeric = false;
        boolean lastCharLeftParentheses = false;

        for (int i = 1; i < inputString.length() - 1; i++) {
            if (isAlphaNumeric(inputString.charAt(i))) {
                alphaNumericCount++;
                if (lastCharAlphaNumeric) {
                    throw new InvalidTreeSyntax("String format is wrong. Input only valid characters for a binary tree.");
                }
                lastCharAlphaNumeric = true;
                lastCharLeftParentheses = false;
            } else if (lastCharLeftParentheses && inputString.charAt(i) == ')') {
                throw new InvalidTreeSyntax("String format is wrong. Fix Parentheses or remove invalid characters");
            } else if (inputString.charAt(i) == '(') {
                lastCharLeftParentheses = true;
                lastCharAlphaNumeric = false;
                leftParenthesesCount++;
            } else {
                lastCharAlphaNumeric = false;
            }
        }
        if (inputString.length() == 0) {
            throw new InvalidTreeSyntax("Enter a proper string");
        }
        if (inputString.length() > MAX_STRING_LENGTH) {
            throw new InvalidTreeSyntax("String cannot be that long");
        }
        if (inputString.charAt(0) != '(') {
            throw new InvalidTreeSyntax("String must begin with a '('");
        }
        if (inputString.charAt(inputString.length() - 1) != ')') {
            throw new InvalidTreeSyntax("String must end with a ')'");
        }
        int leftParanthesesCount = countChar(inputString, '(', 0);
        int rightParanthesesCount = countChar(inputString, ')', 0);

        if (leftParanthesesCount != rightParanthesesCount || alphaNumericCount != rightParanthesesCount) {
            throw new InvalidTreeSyntax("String format is wrong. Make sure parentheses and characters are balanced");
        }
        if (inputString.substring(0, inputString.length() - 1).contains(")))")) {
            throw new InvalidTreeSyntax("String format is wrong. Fix Parentheses");

        }
        return inputString;
    }

    /**
     * Method creates nodes in tree from input string
     *
     * @param treeString String from constructor that has been validated.
     */
    private void parseTreeString(String treeString) {

        Stack<Character> stack = new Stack<>();
        List<Character> levelOne = new ArrayList<>(2);
        List<Character> levelTwoLeft = new ArrayList<>(2);
        List<Character> levelTwoRight = new ArrayList<>(2);

        final Pattern pattern = Pattern.compile("(\\([A-Za-z0-9]+\\([A-Za-z0-9]+\\))+\\(.*", Pattern.CASE_INSENSITIVE);
        boolean leftOne = false;
        final Matcher matcher = pattern.matcher(treeString);
        if (matcher.find()) {
            leftOne = true;
        }
        System.out.println(leftOne);

        int tier = 0;
        Boolean left = true;
        if (treeString.length() != 0 && isAlphaNumeric(treeString.charAt(1))) {
            for (char inString : treeString.toCharArray()) {
                if (stack.size() == 1 && isAlphaNumeric(inString)) {
                    this.insert(50, inString);
                } else if (stack.size() == 1 && inString == '(' && levelTwoLeft.size() != 0) {
                    left = false;
                    stack.push(inString);
                    tier++;
                } else if (inString == '(') {
                    stack.push(inString);
                    tier++;
                } else if (inString == ')') {
                    stack.pop();
                    tier--;
                } else if (tier == 2) {
                    levelOne.add(inString);
                } else if (tier == 3) {
                    System.out.println(leftOne);
                    if (left && !leftOne) levelTwoLeft.add(inString);
                    else levelTwoRight.add(inString);
                }
            }

            System.out.println(levelOne);
            int insertKey = 40;
            for (int i = 0; i < levelOne.size(); i++) {
                this.insert(insertKey, levelOne.get(i));
                insertKey = insertKey + 40;
            }

            insertKey = 35;
            for (int i = 0; i < levelTwoLeft.size(); i++) {
                System.out.println(levelTwoLeft.get(i) + " " + insertKey);
                this.insert(insertKey, levelTwoLeft.get(i));
                insertKey = insertKey + 5;
            }
            insertKey = 75;
            for (int i = 0; i < levelTwoRight.size(); i++) {
                this.insert(insertKey, levelTwoRight.get(i));
                insertKey = insertKey + 10;
            }
            setLeftCount();
            setRightCount();

            try {
                inOrder();
            } catch (StringIndexOutOfBoundsException e) {
                throw new InvalidTreeSyntax(e.getMessage());
            }
        }
    }

    /**
     * Node class for binary tree
     */
    private class Node {
        public int iData;              // data item (key)
        public char dData;           // data item
        public Node leftChild;         // this node's left child
        public Node rightChild;        // this node's right child
    }
}
