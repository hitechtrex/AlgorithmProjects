/**
* CS5900-06 Algorithms
* Dr. Zhao
* Project 2
*
* Author: Steve Jia
* Date: 4/13/2017
* Filename: BinarySearchTree.java
* Description: class file that represents an Order Statistics Tree
*   data structure. An order statistics tree has the property of
*   keeping the nodes with key values less than that of the
*   parent node to the left of the parent node, and keeping
*   nodes with key values greater than that of the parent node
*   to the right of the parent node. It also has a select and a
*   rank functionalities.
**/

public class BinarySearchTree<T extends Comparable<T>>
{
   public TreeNode<T> root; //reference variable for the root node

   /**
   * Constructor: BinarySearchTree
   * @param none
   * Description: initializes the root node to null to start an empty
   *   Binary Search Tree
   **/
   public BinarySearchTree()
   {
      root = null;
   }

   /**
   * Method: insert()
   * @param: element
   *   a variable of generic type T
   * Description: Insert an element into the Binary Search Tree. The
   *   inserted node will always be a leaf in the Binary Search Tree,
   *   and this method will ignore duplicate keys
   **/
   public void insert(T element)
   {
      //create new TreeNode
      TreeNode<T> newNode = new TreeNode<T>(element);

      //check to see if the tree is empty
      if(root == null)
      {
         //tree is empty, assign new node as the root node
         root = newNode;
         return;
      }

      //start at the root of the tree, and walk the tree to find a proper
      //   locatin for the new node
      TreeNode<T> currentNode = this.root;
      TreeNode<T> parentNode = null;

      while(currentNode != null)
      {
         //remember the currentNode to keep track of the parent
         parentNode = currentNode;
         //walk the tree left or right depends on element's value
         // use compareTo() because of generics
         if(element.compareTo(currentNode.getKey()) < 0)
         {
            currentNode = currentNode.getLeftChild();
            //if the parentNode is a leaf, then the new node will be
            //  inserted after here
            if(currentNode == null)
            {
               //new node is the left child
               parentNode.setLeftChild(newNode);
               //new node's parent is the parentNode
               newNode.setParent(parentNode);
               return;
            }
         }
         else if(element.compareTo(currentNode.getKey()) > 0)
         {
            currentNode = currentNode.getRightChild();
            //if the parentNode is a leaf, then the new node will be
            //  inserted after here
            if(currentNode == null)
            {
               //new node is the right child
               parentNode.setRightChild(newNode);
               //new node's parent is the parentNode
               newNode.setParent(parentNode);
               return;
            }
         }
         else
         {
            //new element already exists in the tree, ignored
            return;
         }
      }
   }//end: insert()

   /**
   * Method: search()
   * @param: target
   *   a variable of type T that is to be searched in the tree
   * @return: reference to a TreeNode<T> or null
   * Description: Search this Binary Search Tree for the target, if the
   *   target is found, then method will return a reference to the target
   *   node, otherwise, the method will return null
   **/
   public TreeNode<T> search(T target)
   {
      //start at the root of the tree
      TreeNode<T> currentNode = this.root;

      //search as long as currentNode is not null and currentNode's key
      //   is not the target
      while((currentNode != null) && (currentNode.getKey() != target))
      {
         //compare the target with currentNode's key, and decide
         //  whether to go left or right into the subtree
         if(target.compareTo(currentNode.getKey()) < 0)
         {
            currentNode = currentNode.getLeftChild();
         }
         else
         {
            currentNode = currentNode.getRightChild();
         }
      }
      //return reference to the current node, if target is not found,
      //  currentNode would be null
      return currentNode;
   }//end: search()

   /**
   * Method: bstMin()
   * @param: node
   *   a reference to a TreeNode<T>
   * @return: reference to another TreeNode<T>
   * Description: Find and return the node with the smallest key value
   *   in a tree. This node is the left-most leaf of any given node.
   **/
   public TreeNode<T> bstMin(TreeNode<T> node)
   {
      //walk the tree until the left most leaf is reached
      while(node.getLeftChild() != null)
      {
         node = node.getLeftChild();
      }
      return node;
   }

   /**
   * Method: bstMax()
   * @param: node
   *   a reference to a TreeNode<T>
   * @return: reference to another TreeNode<T>
   * Description: Find and return the node with the largest key value
   *   in a tree. This node is the right-most leaf of any given node.
   **/
   public TreeNode<T> bstMax(TreeNode<T> currentNode)
   {
      //walk the tree until the right-most leaf is reached
      while(currentNode.getRightChild() != null)
      {
         currentNode = currentNode.getRightChild();
      }
      return currentNode;
   }

   /**
   * Method: predecessor()
   * @param: node
   *   a reference to a TreeNode<T> object
   * @return: reference to another TreeNode<T> object or null
   * Description: find and return the node that is the predecessor to the
   *   currentNode. If the currentNode has a left child, then its predecessor
   *   is the right-most leaf of the left child (max of left child). Otherwise,
   *   the predecessor is the ancestor of the currentNode which currentNode's
   *   parent is a right child of the ancestor. Also, if the currentNode is the
   *   min of the entire tree (rooted at this.root), then the predecessor should
   *   be null.
   **/
   public TreeNode<T> predecessor(TreeNode<T> node)
   {
      //if the current node has a left child, find the max of the subtree
      //   rooted at the left child
      if(node.getLeftChild() != null)
      {
         return (bstMax(node.getLeftChild()));
      }
      //otherwise, the predecessor is an ancestor in which the currentNode is
      //   a decendent of the ancestor's right child
      else
      {
         TreeNode<T> parentNode = node.getParent();
         while(parentNode != null && node == parentNode.getLeftChild())
         {
            node = parentNode;
            parentNode = parentNode.getParent();
         }
         return parentNode;
      }
   }//end: pedecessor()

   /**
   * Method: successor()
   * @param: node
   *   a reference to a TreeNode<T> object
   * @return: a reference to another TreeNode<T> object or null
   * Description: find and return the node that is the successor of the current
   *   node. If the current node has a right child, then the current node's
   *   successor is the min node of the subtree rooted at the right child.
   *   Otherwise, current node's successor is an ancestor node in which the
   *   current node's parent is a left decedent of the ancestor node. If the
   *   current node is the max node of the entire tree, then its successor is
   *   null.
   **/
   public TreeNode<T> successor(TreeNode<T> node)
   {
      //if the current node has a right child, then current node's
      //  successor is the left-most of leaf of the node's right child
      if(node.getRightChild() != null)
      {
         return(bstMin(node.getRightChild()));
      }
      //otherwise, successor of the current node is its lowest ancestor,
      //  whose left child is also an ancestor of the current node
      else
      {
         TreeNode<T> parentNode = node.getParent();
         while(parentNode != null && node == parentNode.getRightChild())
         {
            node = parentNode;
            parentNode = parentNode.getParent();
         }
         return parentNode;
      }
   }//end: successor()

   /**
   * Method: delete()
   * @param: deleteNode
   *   a reference to a TreeNode<T> object that will be removed from the tree
   * Description: delete the node that contains target from the current
   *   tree
   **/
   public void delete(TreeNode<T> deleteNode)
   {
      //find deleteNode's left child, right child, and successor
      TreeNode<T> leftNode = deleteNode.getLeftChild();
      TreeNode<T> rightNode = deleteNode.getRightChild();
      TreeNode<T> succNode = successor(deleteNode);

      //if deleteNode has only one child, then replace it with that child
      if(leftNode == null)
      {
         transplant(deleteNode, rightNode);
      }
      else if(rightNode == null)
      {
         transplant(deleteNode, leftNode);
      }
      else
      {
         /* if successor is not the right child, move successor's right child
         *  to successor's place, and assign deleteNode's children to the
         *  successor, and then do the transplant
         */
         if(succNode != rightNode)
         {
            transplant(succNode, succNode.getRightChild());
            succNode.setRightChild(rightNode);
            rightNode.setParent(succNode);
         }
         /* if successor is the right child, then assign deleteNode's left
         *   child as the left child of the successor and then do transplant
         *   with deleteNode and succNode */
         succNode.setLeftChild(leftNode);
         leftNode.setParent(succNode);
         transplant(deleteNode, succNode);
      }
   }//end: delete()


   /**
   * Method: transplant()
   * Description: set replaceNode as a child of deleteNode's parent, thus
   *   removing deleteNode from the current tree
   **/
   private void transplant(TreeNode<T> deleteNode, TreeNode<T> replaceNode)
   {
      //if deleteNode has no parent, then it must be the root node
      if(deleteNode.getParent() == null)
      {
         //assign the replacement node as the root node
         this.root = replaceNode;
      }
      //assign replaceNode as deleteNode's parent's left or right child
      else if(deleteNode == deleteNode.getParent().getLeftChild())
      {
         deleteNode.getParent().setLeftChild(replaceNode);
      }
      else
      {
         deleteNode.getParent().setRightChild(replaceNode);
      }

      //update replaceNode's parent reference
      if(replaceNode != null)
      {
         replaceNode.setParent(deleteNode.getParent());
      }
   }//end: transplant()


   /**
   * Method: findHeight()
   * @param: currentNode
   *   a reference to a TreeNode<T> object
   * @return: the height of a tree
   * Description: find the height of a tree rooted at a node by examining
   **/
   public int findHeight(TreeNode<T> currentNode)
   {
      if(currentNode == null)
      {
        return -1;
      }
      
      int leftHeight = findHeight(currentNode.getLeftChild());
      int rightHeight = findHeight(currentNode.getRightChild());
      
      if(leftHeight > rightHeight)
      {
        return (leftHeight + 1);
      }
      else
      {
        return (rightHeight + 1);
      }
      /*
      if(currentNode == null)
      {
        return 0;
      }
      else
      {
        return (Math.max(
                    findHeight(currentNode.getLeftChild()),
                    findHeight(currentNode.getRightChild())
                  )
               );
      }*/
   }//end findHeight()

   /**
   * Method: size()
   * @param: currentNode
   *   a reference to a TreeNode<T> object
   * @retun: number of nodes in a tree
   * Description: traverse the tree rooted at currentNode and count how many
   *   nodes are in the tree.
   **/
   public int size(TreeNode<T> currentNode)
   {
      int size = 0;
      if(currentNode != null)
      {
         size += size(currentNode.getLeftChild());
         size += 1; //node itself
         size += size(currentNode.getRightChild());
      }
      return size;
   }
   
   /**
   * Method: select()
   * @param: i
   *     the i'th order statistics
   * @return: the i'th smallest node
   * Description: find the i'th smallest element stored in this tree
   *     this would be zero-indexed
   **/
   public TreeNode<T> select(TreeNode<T> currentNode, int i)
         throws Exception
   {
      if(currentNode == null){throw new Exception("Not Found");}
      int r = 0;
      if(currentNode.getLeftChild() != null)
      {
         r = size(currentNode.getLeftChild());
      }

      if (i <= r)
      {
         return select(currentNode.getLeftChild(), i);
      }
      else if(i == r+1)
      {
         return currentNode;
      }
      else if(i > r+1)
      {
         return select(currentNode.getRightChild(), i-r-1);
      }
      else{return null;}
   }//end select()
   
   /**
   * Method: rank()
   * @param: key
   *     the value of the tree node
   * @return: rank of the element
   * Description: find the rank of element key in the tree, returns
   *     its index in the sorted list of elements in this tree
   **/
   public int rank(TreeNode<T> currentNode, T key)
         throws Exception
   {
      if(currentNode==null){throw new Exception("Not Found");}
      else if(key.compareTo(currentNode.getKey()) == -1)
      {
         return rank(currentNode.getLeftChild(), key);
      }
      else if(key.compareTo(currentNode.getKey()) == 0)
      {
         if(currentNode.getLeftChild() != null)
         {
            return size(currentNode.getLeftChild())+1;
         }
         else
         {
            return 1;
         }
      }
      else if(key.compareTo(currentNode.getKey()) == 1)
      {
         if(currentNode.getLeftChild() != null)
         {
            return (size(currentNode.getLeftChild()) 
                        + 1 + rank(currentNode.getRightChild(), key));
         }
         else
         {
            return (1 + rank(currentNode.getRightChild(), key));
         }
      }
      else{return 0;}
   }//end rank()

   /**
   * Method: walk()
   * @param: node
   *   a reference to a TreeNode<T> object
   * @return: a string of all the keys in a substree
   * Description: Traverse the Binary Search Tree and return all the keys
   *   in a string use the return string for the toString method
   **/
   public String walk(TreeNode<T> node)
   {
      String result = "";

      //in-order tree walk
      if(node != null)
      {
         result += walk(node.getLeftChild()) + " ";
         result += node.getKey().toString() + " ";
         result += walk(node.getRightChild());
      }

      return result;
   }//end: walk()

   /**
   * Method: toString()
   * Description: use the walk() method to return a string that
   *   contains all the keys in the entire tree. Best not to use
   *   this method when the tree is large.
   **/
   public String toString()
   {
      String printOut = "{";
      printOut += walk(this.root);
      printOut += "}";
      return printOut;
   }
}
