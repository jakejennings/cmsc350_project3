/****************************************************************************

Name: Nya Garnett
Date: 02/13/23
Assignment: Project 3

Class Description - 

Class should turn the string into an array based on parenthesis and Loops through using a stack and 
adds children that fall within parent parenthesis while determine if the absolute difference between branches is at most 1.
after doing the following steps method should calls recursive method, which also calls recursive height method.
determines if every node in a tree has either 2 or 0 children then prints out nodes level order
****************************************************************************/

import java.util.EmptyStackException;
import java.util.Stack;


public class BinaryTree {
Node parent, child; //After construction, parent is synonymous w/ root



//===============================================================================================
//BinaryTree constructor - Takes the string and turns it into an array based on parenthesis.
//Loops through using a stack and adds children that fall within parent parenthesis
//===============================================================================================

 public BinaryTree(String input) throws InvalidTreeSyntax {
	 
Stack<Node> nodeStack = new Stack<>();
String[] inputArray = input.substring(1, input.length()-1) // remove first & last parenthesis

//and split the String into a arr of strings, retain the parenthesis

.split("(?<=\\()|(?=\\()|(?<=\\))|(?=\\))");
parent = new Node(inputArray[0]); //setting the new first character to the root

//starts essentially on the third character of the string

for (int i = 1; i < inputArray.length - 1; i++){

//means there is another child. Child becomes parent if one exists
	
if (inputArray[i].equals("(")){ nodeStack.push(parent);
  if (child != null) { parent = child; }
  
//assign the current parent as the child of one on stack
  
}else if(inputArray[i].equals(")")){
  try { child = parent; parent = nodeStack.pop();
  
}catch (EmptyStackException emptyStack){ throw new InvalidTreeSyntax("Incorrect parenthesis!"); }
  
//if it gets here, it is a value to be assigned as child to parent.
  
}else{ child = new Node(inputArray[i]); 

  if (parent != null) { parent.addChild(child); }//addChild with throw InvalidTreeSyntax
  
}

  }//for every node, will have 2 parenthesis

if (this.recNodes(parent) * 3 != input.length()) throw new InvalidTreeSyntax("Incorrect Syntax");

}// TODO: Redo BinaryTree constructor recursively

//===============================================================================================
//checkBalanced - determine if the absolute difference between branches is at most 1.
//calls recursive method, which also calls recursive height method.
//===============================================================================================
 
public boolean isBalanced() { return recIsBalanced(this.parent); }

 private boolean recIsBalanced(Node root){
	 
//base case
if (root == null) {return true;}

//return true if the absolute difference is at most 1
return (Math.abs(recHeight(root.left) - recHeight(root.right)) <= 1) &&
(recIsBalanced(root.left) && recIsBalanced(root.right)); // and calls recursively

  }

//===============================================================================================
//checkFull - determines if a tree has the maximum nodes for the height or not.
//calls recursive method, which also calls recursive height method.
//===============================================================================================
 
public boolean isFull() {return recIsFull(this.parent, recHeight(this.parent), 0); }

//the index of of parent in this exercise is 0
private boolean recIsFull(Node root, int height, int index) {
	
//if it is empty, by BT logic: it is full
if (root == null){ return true; }

//check to see if height is same among leaves
  if (root.left == null && root.right == null) { return (height == index + 1); }

//one child empty
     if (root.left == null || root.right == null){ return false;}
     
//recursive call to both children
return recIsFull(root.left, height, index+1) && recIsFull(root.right, height, index+1);

}

//===============================================================================================
//isProper - determines if every node in a tree has either 2 or 0 children.
//Simply calls the recursive method
//===============================================================================================
public boolean isProper() { return recIsProper(this.parent); }

private boolean recIsProper(Node root) {
	
//base case
if(root == null) {return true;}

//returns true or false based on two or zero children

return ((root.left != null || root.right == null) && (root.left == null || root.right != null))
&& (recIsProper(root.left) && recIsProper(root.right)); // and calling recursively
}

//===============================================================================================
//height - finds the height of the binary tree, where the root node is 0.
//calls the recursive method, which adds one to the the larger of left or right
//===============================================================================================

public int height() {return recHeight(this.parent)-1; }

//subtracted one since in this exercise, root is 0
private int recHeight(Node root) {
	
//adds one to the greater of left and right, zero if null
	
return (root == null) ? 0 : 1 + Math.max(recHeight(root.left), recHeight(root.right));

//found every chance to use the conditional operator in this (had a lot of single if/else's)

}

//===============================================================================================
//nodes - finds the amount of nodes in a binary tree. Calls the recursive method,
//which adds one for every node of left and right subtree, 0 if null.
//===============================================================================================

public int nodes() { return recNodes(this.parent); }

private int recNodes(Node root) {
	
//similar to recHeight, but adds one for both left and right. If null, zero
	
return (root == null) ? 0 : 1 + recNodes(root.left) + recNodes(root.right);
}

//===============================================================================================
//inOrder - prints the info of the nodes in the binary tree in order. Calls the recursive
//method which uses the algorithm left -> root -> right
//===============================================================================================

public String inOrder() {
  return recInOrder(this.parent);
  
}

private String recInOrder(Node root) {
	
//basically copied the algorithm from my discussion post. Blank string for nulls
return (root == null) ? "" : "(" + recInOrder(root.left) + root.info + recInOrder(root.right) + ")";

}

//simply calls the the root node toString
@Override
public String toString() { return parent.toString(); }

//===============================================================================================
//Nested node class/ arguments: character info/ helper methods for nodes
//description: Creates nodes to be used in tree and methods to act on them
//===============================================================================================

public static class Node {
private String info;
private Node left;
private Node right;

public Node(String info) { this.info = info; }

private void addChild(Node child) throws InvalidTreeSyntax {
	
//simple conditions for nodes, can have at most 2 children
if (this.left == null){ this.setLeft(child); }
  else if (this.right == null){ this.setRight(child); }

else{ throw new InvalidTreeSyntax("Nodes can only have 2 children!");} }

//Simple setters for the nodes
private void setLeft(Node newLeft) { left = newLeft; }
private void setRight(Node newRight) { right = newRight; }

@Override //To call the recursive method
public String toString() { return toString(this); }

//recursively printing out the nodes
private static String toString(Node root) {
  return (root == null) ? "" : "(" + root.info + toString(root.left) + toString(root.right) + ")";
  
}
}
}
