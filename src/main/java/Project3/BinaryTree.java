package Project3;

import java.util.Stack;

public class BinaryTree {

    private Node root;


// -------------------------------------------------------------

    public BinaryTree() {
        root = null;
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        /*tree.insert(1, 'A');
        tree.insert(2, 'B');
        tree.insert(3, 'C');
        tree.insert(4, 'D');
        tree.insert(5, 'E');
        tree.insert(6, 'F');
        tree.insert(7, 'G');
        tree.insert(8, 'H');
        tree.insert(9, 'I');
        tree.insert(10, 'J');
        tree.insert(11, 'K');
        tree.insert(12, 'L');
        tree.insert(13, 'M');
        tree.insert(14, 'N');
        tree.insert(15, 'O');
        tree.insert(16, 'P');
        tree.insert(17, 'Q');
        tree.insert(18, 'R');*/
        tree.parseTreeString("(A(G(j)(1))(z(5)))");
        tree.parseTreeString("(A(G(j)(1(ab))(z(5)))");
        tree.displayTree();


    }

    public Node find(int key) {
        Node current = root;
        while (current.iData != key) {
            if (key < current.iData) current = current.leftChild;
            else current = current.rightChild;
            if (current == null) return null;
        }
        return current;
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

    public boolean delete(int key) {
        Node current = root;
        Node parent = root;

        boolean isLeftChild = true;
        while (current.iData != key) {
            parent = current;
            if (key < current.iData) {
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) return false;
        }

        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) root = null;
            else if (isLeftChild) parent.leftChild = null;
            else parent.rightChild = null;
        } else if (current.rightChild == null) if (current == root) root = current.leftChild;
        else if (isLeftChild) parent.leftChild = current.leftChild;
        else parent.rightChild = current.leftChild;
        else if (current.leftChild == null) if (current == root) root = current.rightChild;
        else if (isLeftChild) parent.leftChild = current.rightChild;
        else parent.rightChild = current.rightChild;
        else {
            Node successor = getSuccessor(current);
            if (current == root) root = successor;
            else if (isLeftChild) parent.leftChild = successor;
            else parent.rightChild = successor;

            successor.leftChild = current.leftChild;
        }

        return true;

    }

    private Node getSuccessor(Node delNode) {

        Node successorParent = delNode;

        Node successor = delNode;

        Node current = delNode.rightChild;

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }

        return successor;
    }

    public void traverse(int traverseType) {

        switch (traverseType) {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.print("\nInorder traversal:  ");
                inOrder(root);
                break;
            case 3:
                System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
        }

        System.out.println();

    }

    private void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }

// -------------------------------------------------------------

    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }

    private void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }

    private int getRandomInt() {
        return (int) (Math.random() * 1000);
    }

    public void parseTreeString(String treeString) {
        Stack stack = new Stack();
        if (treeString.length() != 0) {
            for (char inString : treeString.toCharArray()) {
                if (inString == '(') {
                    this.insert(getRandomInt(), inString);
                    stack.push(inString);
                } else if (inString == ')') {
                    stack.pop();
                } else {
                    if (stack.isEmpty()) {
                        this.insert(getRandomInt(), inString);
                        stack.push(inString);
                    } else {
                        this.insert(Integer.parseInt((String) stack.pop()), inString);

                    }

                }
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
                    System.out.print(temp.iData);
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
