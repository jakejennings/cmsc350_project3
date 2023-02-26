package Project3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreeOld {

    private Node root;
    private int leftSize;
    private int rightSize;
    private boolean isProperBoolean;
    private String outputString = "";


// -------------------------------------------------------------

    public BinaryTreeOld(String inputString) {
        root = null;
        leftSize = 0;
        rightSize = 0;
        isProperBoolean = true;
        String outputString = "";
        parseTreeString(inputString);
    }

    public static void main(String[] args) {
        //BinaryTree tree = new BinaryTree("(A(G(j)(1))(z(5)(B)))");
        //BinaryTree tree = new BinaryTree("(A(G(j)(1))(z(5)))");
        //BinaryTree tree = new BinaryTree("(A(G(j)(1))(z))");
        //BinaryTree tree = new BinaryTree("(A)");
        //BinaryTree tree = new BinaryTree("(A(B))");
        //BinaryTree tree = new BinaryTree("(A(B(C)(D)))");
        BinaryTreeOld tree = new BinaryTreeOld("(A))A(");
        //BinaryTree tree = new BinaryTree("(A(B(C)(D))(E))");
        //BinaryTree tree = new BinaryTree("(A(B(C)))");
        //tree.parseTreeString("(A(G(j)(1))(z(5)))");
        //tree.parseTreeString("(A(G(j)(1))(z(5)(B)))");
        //tree.parseTreeString("(A(B(C)))");
        //tree.parseTreeString("(A(G(j)(1))(z(5)))");
        tree.displayTree();
        //System.out.println(tree.getLeftCount());
        //System.out.println(tree.getRightCount());
        //System.out.println(tree.isFull() + " Full");
        //System.out.println(tree.isBalanced() + " Balanced");
        //System.out.println(tree.isProper() + " Proper");
        //tree.preOrder();
        System.out.println(tree.inOrder() + " Inorder");
        //tree.inOrder();
        //String test = tree.inOrder();


        //System.out.println(test.length() + " TEST");
        //System.out.println(tree.getHeight());
        //System.out.println(tree.getString());

    }

    private static boolean isAlphaNumeric(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9');
    }

    private static int countChar(String str, char ch, int ind) {
        if (ind == str.length()) return 0;
        int count = 0;
        if (str.charAt(ind) == ch) count++;
        return count + countChar(str, ch, ind + 1);
    }

    public int getNodeCount() {
        return leftSize + rightSize + 1;
    }

    public int getHeight() {
        if (leftSize == 3 || rightSize == 3) return 2;
        if (leftSize == 2 || rightSize == 2) return 1;
        return 0;
    }

    public boolean isFull() {
        return (leftSize == 0 && rightSize == 0) || (leftSize + rightSize + 1 == 7) || (leftSize + rightSize + 1 == 3);

    }

    public boolean isBalanced() {
        return (Math.abs(leftSize - rightSize) <= 1 || (leftSize == 0 && rightSize == 0));
    }

    public boolean isProper() {
        Node localRoot = root;

        return isProper(localRoot, true);
    }

    public boolean isProper(Node localRoot, Boolean testBool) {
        if (localRoot != null) {
            if (localRoot.leftChild == null && localRoot.rightChild == null && testBool) testBool = true;
            else if (localRoot.leftChild != null && localRoot.rightChild != null && testBool) testBool = true;
            else {
                testBool = false;
                isProperBoolean = false;
            }
            isProper(localRoot.leftChild, testBool);
            isProper(localRoot.rightChild, testBool);
        }
        return isProperBoolean;
    }

    public void insert(int id, char dd) {
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

    public String inOrder() {
        outputString = "";
        inOrder(root);
        StringBuilder leftBuilder;
        try {
            leftBuilder = new StringBuilder(outputString.substring(0, leftSize + 1));
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidTreeSyntax("BRUH");
        }

        if (leftSize == 3) {
            leftBuilder.insert(0, "(((");
            leftBuilder.insert(4, ")");
            leftBuilder.insert(6, "(");
            leftBuilder.insert(8, "))");
            if (rightSize == 0) {
                leftBuilder.insert(11, ")");
            }
        }
        if (leftSize == 2) {
            leftBuilder.insert(0, "((");
            leftBuilder.insert(3, ")");
            leftBuilder.insert(5, ")");
            leftBuilder.insert(7, ")");
        }
        if (leftSize == 1) {
            leftBuilder.insert(0, "((");
            leftBuilder.insert(3, ")");
            leftBuilder.insert(5, ")");
        }
        if (leftSize == 0) {
            leftBuilder.insert(0, "(");
        }
        if (leftSize == 0 && rightSize == 0) {
            leftBuilder.insert(2, ")");
        }

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

    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            outputString = outputString + localRoot.dData;
            inOrder(localRoot.rightChild);
        }
    }

    public int setLeftCount() {
        Node current = root.leftChild;
        getSideCount(current, true);
        return leftSize;
    }

    public int setRightCount() {
        Node current = root.rightChild;
        getSideCount(current, false);
        return rightSize;
    }

    private void getSideCount(Node localRoot, Boolean isLeft) {
        if (localRoot != null) {
            int tempInt = 0;
            tempInt = isLeft ? leftSize++ : rightSize++;
            getSideCount(localRoot.leftChild, isLeft);
            getSideCount(localRoot.rightChild, isLeft);
        }
    }

    private boolean validateInput(String inputString) {
        System.out.println(inputString + "DOG");
        int alphaNumericCount = 0;
        int leftParenthesesCount = 0;
        boolean lastCharAlphaNumeric = false;
        boolean lastCharLeftParentheses = false;

        for (int i = 1; i < inputString.length() - 1; i++) {
            if (isAlphaNumeric(inputString.charAt(i))) {
                alphaNumericCount++;
                if (lastCharAlphaNumeric) {
                    throw new InvalidTreeSyntax("Wrong Format. Elements must be separated by parentheses.");
                }
                lastCharAlphaNumeric = true;
                lastCharLeftParentheses = false;
            } else if (lastCharLeftParentheses && inputString.charAt(i) == ')') {
                throw new InvalidTreeSyntax("Wrong Format Bro!");
            } else if (inputString.charAt(i) == '(') {
                lastCharLeftParentheses = true;
                lastCharAlphaNumeric = false;
                leftParenthesesCount++;
                System.out.println(inputString.charAt(i) + "LOOK");
            } else {
                lastCharAlphaNumeric = false;
            }
        }
        if (inputString.length() == 0) {
            return false;
        }
        if (inputString.charAt(0) != '(') {
            return false;
        }
        if (inputString.charAt(inputString.length() - 1) != ')') {
            return false;
        }
        int leftParanthesesCount = countChar(inputString, '(', 0);
        int rightParanthesesCount = countChar(inputString, ')', 0);

        if (leftParanthesesCount != rightParanthesesCount || alphaNumericCount != rightParanthesesCount) {
            throw new InvalidTreeSyntax("String format wrong");
        }
        if (inputString.substring(0, inputString.length() - 1).contains(")))")) {
            throw new InvalidTreeSyntax("String format wrong. Fix Parantheses");

        }
        return true;
    }

    public void parseTreeString(String treeString) {

        try {
            if (!validateInput(treeString)) {
                System.out.println("Invalid Input");
                return;
            }
        } catch (InvalidTreeSyntax e) {
            throw new InvalidTreeSyntax(e.getMessage());
        }


        Stack<Character> stack = new Stack<>();

        List<Character> levelOne = new ArrayList<>(2);
        List<Character> levelTwoLeft = new ArrayList<>(2);
        List<Character> levelTwoRight = new ArrayList<>(2);
        int tier = 0;
        Boolean left = true;

        if (treeString.length() != 0 && isAlphaNumeric(treeString.charAt(1))) {
            //boolean lastCharIsAlphaNumeric = false;
            for (char inString : treeString.toCharArray()) {
                //if (isAlphaNumeric(inString) && lastCharIsAlphaNumeric) {
                //System.out.println(inString);
                // throw new InvalidTreeSyntax("Check input for errors");

                //}
                if (stack.size() == 1 && isAlphaNumeric(inString)) {
                    this.insert(50, inString);
                    //lastCharIsAlphaNumeric = true;
                } else if (stack.size() == 1 && inString == '(' && levelTwoLeft.size() != 0) {
                    left = false;
                    stack.push(inString);
                    tier++;
                    //lastCharIsAlphaNumeric = false;
                } else if (inString == '(') {
                    stack.push(inString);
                    tier++;
                    //lastCharIsAlphaNumeric = false;
                } else if (inString == ')') {
                    stack.pop();
                    tier--;
                    //lastCharIsAlphaNumeric = false;
                } else if (tier == 2) {
                    levelOne.add(inString);
                    //lastCharIsAlphaNumeric = true;
                } else if (tier == 3) {
                    //lastCharIsAlphaNumeric = true;
                    if (left) levelTwoLeft.add(inString);
                    else levelTwoRight.add(inString);
                }
            }
            int insertKey = 40;
            for (int i = 0; i < levelOne.size(); i++) {
                this.insert(insertKey, levelOne.get(i));
                insertKey = insertKey + 40;
            }

            insertKey = 35;
            for (int i = 0; i < levelTwoLeft.size(); i++) {
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


    public void displayTree() {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println("......................................................");
        while (!isRowEmpty) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int j = 0; j < nBlanks; j++)
                System.out.print(' ');
            while (!globalStack.isEmpty()) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.iData + " " + temp.dData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);
                    if (temp.leftChild != null || temp.rightChild != null) isRowEmpty = false;
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            nBlanks /= 2;
            while (!localStack.isEmpty()) globalStack.push(localStack.pop());
        }
        System.out.println("......................................................");
    }

    public class Node {
        public int iData;              // data item (key)
        public char dData;           // data item
        public Node leftChild;         // this node's left child
        public Node rightChild;        // this node's right child
    }
}
