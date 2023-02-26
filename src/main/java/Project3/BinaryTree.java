/**********************************************************************************************************************
 *
 * Name: Jacob Jennings
 * Date: February 25, 2023
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

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {

    private Node root;
    private String outputString = "";


// -------------------------------------------------------------

    public BinaryTree(String inputString) {
        root = null;
        String outputString = "";

        if (isValidBinaryTree(inputString)) {
            root = stringToTree(inputString);
        } else {
            throw new InvalidTreeSyntax("Enter valid binary tree");
        }
    }

    private static boolean isValidBinaryTree(String s) {
        if (!s.startsWith("(") || !s.endsWith(")")) {
            throw new InvalidTreeSyntax("Binary Tree input must start and end with parentheses");
        }
        if (countLeftParentheses(s) != countRightParentheses(s) ||
                countAlphanumeric(s) != countLeftParentheses(s) ||
                countAlphanumeric(s) != countRightParentheses(s)) {
            throw new InvalidTreeSyntax("Incorrect number of parentheses or characters");
        }

        s = s.substring(1, s.length() - 1);
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty() || stack.peek() != '(') {
                    throw new InvalidTreeSyntax("You might have missed an opening parenthesis");
                }
                stack.pop();
            } else if (!Character.isLetterOrDigit(c)) {
                throw new InvalidTreeSyntax("Invalid character in binary tree");
            }
        }
        return stack.isEmpty();
    }

    public static String inorderTraversal(Node root) {
        if (root == null) {
            return "";
        }
        if (root.leftChild == null && root.rightChild == null) {
            return "(" + root.dData + ")";
        } else if (root.leftChild == null) {
            return "(" + root.dData + "()" + inorderTraversal(root.rightChild) + ")";
        } else if (root.rightChild == null) {
            return "(" + inorderTraversal(root.leftChild) + root.dData + "" + ")";
        } else {
            return "(" + inorderTraversal(root.leftChild) + root.dData + inorderTraversal(root.rightChild) + ")";
        }
    }

    public static int countAlphanumeric(String inputString) {
        Stack<Character> stack = new Stack<>();
        int count = 0;

        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                stack.pop();
            } else if (Character.isLetterOrDigit(c) && !stack.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public static int countLeftParentheses(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                count++;
            }
        }
        return count;
    }

    public static int countRightParentheses(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ')') {
                count++;
            }
        }
        return count;
    }

    public int getHeight() {
        Node currentRoot = root;
        return getHeight(currentRoot) - 1;
    }

    private int getHeight(Node root) {
        if (root == null) {
            return 0;
        }

        return Math.max(getHeight(root.leftChild), getHeight(root.rightChild)) + 1;
    }

    public int getNodeCount() {
        Node currentRoot = root;
        return getNodeCount(currentRoot);
    }

    private int getNodeCount(Node root) {
        if (root == null) {
            return 0;
        }
        return getNodeCount(root.leftChild) + getNodeCount(root.rightChild) + 1;
    }

    public boolean isFull() {
        Node currentRoot = root;
        return isFull(currentRoot);
    }

    private boolean isFull(Node root) {
        if (root == null) {
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        int maxWidth = 1;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            if (levelSize > maxWidth) {
                maxWidth = levelSize;
            }
            for (int i = 0; i < levelSize; i++) {
                Node node = queue.poll();
                if (node.leftChild != null) {
                    queue.offer(node.leftChild);
                }
                if (node.rightChild != null) {
                    queue.offer(node.rightChild);
                }
            }
        }
        int expectedWidth = (int) Math.pow(2, getHeight(root) - 1);
        return maxWidth == expectedWidth;
    }

    public boolean isBalanced() {
        Node currentRoot = root;
        return isBalanced(root);
    }

    public boolean isBalanced(Node root) {
        if (root == null) {
            return true;
        }

        int leftHeight = getHeight(root.leftChild);
        int rightHeight = getHeight(root.rightChild);

        return Math.abs(leftHeight - rightHeight) <= 1 && isBalanced(root.leftChild) && isBalanced(root.rightChild);
    }

    public boolean isProper() {
        Node localRoot = root;

        return isProper(localRoot);
    }
    public boolean isProper(Node localRoot) {
        if (localRoot.leftChild == null) return true;
        if (localRoot.rightChild == null) return false;
        else {
            boolean leftProper = isProper(localRoot.leftChild);
            boolean rightProper = isProper(localRoot.rightChild);
            return leftProper && rightProper;
        }
    }
    public Node stringToTree(String str) {
        if (str.isEmpty()) {
            return null;
        }
        if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
            str = str.substring(1, str.length() - 1);
        }
        int firstParen = str.indexOf("(");
        char val = str.charAt(0);
        Node root = new Node(val);
        if (firstParen == -1) {
            return root;
        }
        int start = firstParen, leftParenCount = 0;
        for (int i = start; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                leftParenCount++;
            } else if (str.charAt(i) == ')') {
                leftParenCount--;
            }
            if (leftParenCount == 0 && start == firstParen) {
                root.leftChild = stringToTree(str.substring(start + 1, i));
                start = i + 1;
            } else if (leftParenCount == 0 && !str.substring(start, i).equals(")")) {
                root.rightChild = stringToTree(str.substring(start + 1, i));
            }
        }
        return root;
    }

    public String inorderTraversal() {
        Node currentRoot = root;
        StringBuilder sb = new StringBuilder();
        return inorderTraversal(currentRoot);
    }

    static class Node {
        public char dData;           // data item
        public Node leftChild;         // this node's left child
        public Node rightChild;        // this node's right child

        Node(char key) {
            dData = key;
        }
    }
}
