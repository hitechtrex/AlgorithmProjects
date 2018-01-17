/**
* CS5900-06 Algorithms
* Dr. Zhao
* Project 2
*
* Author: Steve Jia
* Date: 4/13/2017
* Filename: TreeNode.java
* Description: the TreeNode class is a simple class that
*  represents a node in a Binary Search Tree data structure.
*  The node has three links to other nodes, parent, left
*  child, and right child. The node also contains a field
*  for its key value.
**/

//generic class, type variable T needs to extend the Comparable
//  class in order for two T type variables to be compared
public class TreeNode<T extends Comparable<T>>
{
   private TreeNode<T> parent;     //reference to its parent node
   private TreeNode<T> leftChild;  //ref to its left child
   private TreeNode<T> rightChild; //ref to its right child
   private T key;                  //ref to its key value
   
   /**
   * Constructor: TreeNode()
   * @param: element
   *   an element with generic type T
   * Description: create a new TreeNode object with all three
   *   links set to null and its key value set to element
   **/
   public TreeNode(T element)
   {
      this.parent = null;
      this.leftChild = null;
      this.rightChild = null;
      this.key = element;
   }

   /**
   * Method: setParent()
   * @param: newParent
   *    a reference to another TreeNode<T>
   * Description: sets the parent node reference of this node
   *   to newParent
   **/
   public void setParent(TreeNode<T> newParent)
   {
      this.parent = newParent;
   }

   /**
   * Method: setLeftChild()
   * @param: newLeft
   *   a reference to another TreeNode<T>
   * Description: sets the left child reference of this node
   *   to newLeft
   **/
   public void setLeftChild(TreeNode<T> newLeft)
   {
      this.leftChild = newLeft;
   }

   /**
   * Method: setRightChild()
   * @param: newRight
   *   a reference to another TreeNode<T>
   * Description: sets the right child reference of this node
   *   to newRight
   **/
   public void setRightChild(TreeNode<T> newRight)
   {
      this.rightChild = newRight;
   }

   /**
   * Method: setKey()
   * @param: newKey
   *   a reference to a T type variable
   * Description: updates the key value of this node to newKey
   **/
   public void setKey(T newKey)
   {
      this.key = newKey;
   }

   /**
   * Method: getParent()
   * @param: none
   * @return: TreeNode<T> parent
   * Description: returns a reference of this node's parent node
   **/
   public TreeNode<T> getParent()
   {
      return (this.parent);
   }

   /**
   * Method: getLeftChild()
   * @param: none
   * @return: TreeNode<T> leftChild
   * Description: returns a reference of this node's left child node
   **/
   public TreeNode<T> getLeftChild()
   {
      return (this.leftChild);
   }

   /**
   * Method: getRightChild()
   * @param: none
   * @return: TreeNode<T> rightChild
   * Description: returns a reference of this node's right child node
   **/
   public TreeNode<T> getRightChild()
   {
      return (this.rightChild);
   }

   /**
   * Method: getKey()
   * @param: none
   * @return: T key
   * Description: returns this node's key value
   **/
   public T getKey()
   {
      return (this.key);
   }
}
