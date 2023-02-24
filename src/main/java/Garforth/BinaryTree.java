package Garforth; /**
 * Jake Garforth
 * 2/8/23
 * CMSC 350 6381
 * This is the BinaryTree class. The tree itself is actually made up of TreeNodes, a nested class
 * of nodes that have pointers to the left and to the right, as well as a data field. Within the
 * TreeNode is an append method that appends to the left first, then the right.
 * The tree is constructed from a string input, and then crafted using a stack and recursion. The input
 * string is split up into the two "child trees", and this is done over and over again until the strings contain
 * no children. The tree is effectively built from the bottom up. Every substring is compared against regular
 * expressions to ensure a valid input. If not, an exception is thrown. Finally, if the tree constructing operation
 * concluded before reaching the end of the string, an exception is thrown.
 * BinaryTree features a number of methods that utilize recursion to get information about the tree.
 * inOrderString calls inOrderTraversal to help construct a stringbuilder object of the tree in inOrder form. The left
 * side of the tree is traversed first, then added to the object, then the right side.
 * isBalanced uses the findHeight method on either side of the tree, and then verifies that the difference is no greater
 * than 1.
 * Is full finds the max number of possible nodes with the formula (2^h)-1, where h is the number of nodes, rather than
 * edges. To do this, 1 is simply added to the height found with findHeight. Then, that number is compared against the
 * number of nodes present in the tree, found with countNodes.
 * Is proper traverses down the tree to find any nodes with a left child and no right child. If any is found, it returns
 * false.
 * findHeight travels down the left and right sides of the tree, counting along the way, and returns the max number
 * counted. If parent==null returns -1 so counting for the root is accounted for.
 * countNodes simply calls itself on each nodes child, adding 1 as it goes along, until finally returning the number
 * counted.
 */

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;

public class BinaryTree{

    private final TreeNode<?> root;

    public BinaryTree(String treeRepresentation) throws InvalidTreeSyntaxException
    {
        treeRepresentation = spaceCheck(treeRepresentation);
        this.root = buildTree(treeRepresentation);
    }

    public TreeNode<?> getRoot()
    {
        return root;
    }

    private static class TreeNode<E>
    {
        private final E data;
        private TreeNode<E> left = null;
        private TreeNode<E> right = null;

        private TreeNode(E data)
        {
            this.data = data;
        }
        private void append(TreeNode<E> treeNode)
        {
            if(this.left==null)
            {
                this.left = treeNode;
            }
            else if (this.right==null)
            {
                this.right = treeNode;
            }
        }

    }
    private static String spaceCheck(String originalInput)
    {
        return originalInput.replaceAll("\\s", "");
    }

    private static TreeNode<Character> buildTree(String treeString) throws InvalidTreeSyntaxException {
        if(treeString.length() == 0)
        {
            throw new InvalidTreeSyntaxException("Input must not be empty.");
        }
        if(treeString.charAt(0)!= '(')
        {
            throw new InvalidTreeSyntaxException("Tree is not in valid syntax:\nPlease contain tree within parentheses.");

        }

        TreeNode<Character> newTreeNode = new TreeNode<>(treeString.charAt(1));
        if(treeString.length()==3)
        {
            return newTreeNode;
        }

        int i = 2;
        while(i<treeString.length()-1){
            if(treeString.charAt(i)=='(')
            {
                Stack<Character> bracketTracker = new Stack<>();
                StringBuilder child = new StringBuilder();

                bracketTracker.push(treeString.charAt(i));
                child.append(treeString.charAt(i));

                while(!bracketTracker.empty())
                {
                    i++;
                    try {
                        if (treeString.charAt(i) == '(') {
                            bracketTracker.push(treeString.charAt(i));
                        }
                    }
                    catch (StringIndexOutOfBoundsException exceptionReason)
                    {
                        throw new InvalidTreeSyntaxException("Tree is not in valid syntax.\nIndex out of bounds exception.");
                    }
                    if(treeString.charAt(i)==')')
                    {
                        bracketTracker.pop();
                    }
                    child.append(treeString.charAt(i));
                }
                validFormat(child.toString());
                newTreeNode.append(buildTree(child.toString()));
            }
            else
            {
                throw new InvalidTreeSyntaxException("All nodes must be contained within the same parentheses.");
            }
            i++;
        }
        if(i!=treeString.length()-1)
        {
            throw new InvalidTreeSyntaxException("All nodes must be contained within the same parentheses.");
        }
        return newTreeNode;

    }

    private static void validFormat(String userInput) throws InvalidTreeSyntaxException {
        //(char(String)) , pattern for non-leaf nodes
        String pattern = "^\\(\\w\\(([a-zA-Z0-9()]+?)\\)\\)$";
        //(char) , pattern for leaf nodes
        String leafPattern = "^\\(\\w\\)$";

        Pattern checkAgainst = Pattern.compile(pattern);
        Matcher match = checkAgainst.matcher(userInput);

        if(match.matches())
        {
            return;
        }
        checkAgainst = Pattern.compile(leafPattern);
        match = checkAgainst.matcher(userInput);
        if(!match.matches()) {
            throw new InvalidTreeSyntaxException("Tree is not in valid syntax: " + userInput);
        }
    }

    public String inOrderString(TreeNode<?> parent)
    {
        StringBuilder inOrderTree = new StringBuilder();
        inOrderTree.append("(");
        inOrderTraversal(parent, inOrderTree);
        inOrderTree.append(")");
        return(inOrderTree.toString());
    }

    private void inOrderTraversal(TreeNode<?> parent, StringBuilder inOrderTree)
    {
        if(parent.left!=null)
        {
            inOrderTree.append("(");
            inOrderTraversal(parent.left, inOrderTree);
            inOrderTree.append(")");
        }

        inOrderTree.append(' ');
        inOrderTree.append(parent.data);
        inOrderTree.append(' ');

        if(parent.right!=null)
        {
            inOrderTree.append("(");
            inOrderTraversal(parent.right, inOrderTree);
            inOrderTree.append(")");
        }
    }

    public boolean isBalanced(TreeNode<?> parent)
    {
        int leftHeight = findHeight(parent.left);
        int rightHeight = findHeight(parent.right);

        return max(leftHeight, rightHeight) - min(leftHeight, rightHeight) <= 1;
    }

    public boolean isFull(TreeNode<?> parent)
    {
        int height = findHeight(parent);
        int maxNodes = (int)(pow(2, height+1) -1);

        int nodesInTree = countNodes(parent);

        return maxNodes==nodesInTree;

    }

    public boolean isProper(TreeNode<?> parent)
    {
        if(parent.left == null)
        {
            return true;
        }
        if(parent.right==null)
        {
            return false;
        }
        else
        {
            boolean leftSide = isProper(parent.left);
            boolean rightSide = isProper(parent.right);
            return leftSide && rightSide;
        }
    }

    public int findHeight(TreeNode<?> parent)
    {
        if(parent==null)
        {
            return -1;
        }
        int leftHeight = findHeight(parent.left);
        int rightHeight = findHeight(parent.right);

        return max(leftHeight, rightHeight) + 1;
    }

    public int countNodes(TreeNode<?> parent)
    {
        if(parent==null)
        {
            return 0;
        }

        int nodesLeft = countNodes(parent.left);
        int nodesRight = countNodes(parent.right);

        return nodesLeft+nodesRight+1;
    }

}