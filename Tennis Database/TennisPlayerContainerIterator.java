/*
 * William Moss
 * CS-102, Fall 2020
 * Assignment 2
 * Last modified: Dec 5, 2020
 */

package TennisDatabase;

import java.util.Iterator;

class TennisPlayerContainerIterator implements Iterator<TennisPlayer> {
   private TennisPlayerQueue queue;
   private TennisPlayerContainerNode root;

   public TennisPlayerContainerIterator(TennisPlayerContainerNode rootNode) {
      this.root = rootNode;
      this.queue = new TennisPlayerQueue();
   }

   // Desc: Returns true if the iteration has more elements
   public boolean hasNext() {
      return !this.queue.isEmpty();
   }

   // Desc: Returns the next element in the iteration
   // Output: Throws a checked (critical) exception if the player (id) does not
   // exists.
   public TennisPlayer next() {
      try {
         return this.queue.dequeue();
      } catch (TennisDatabaseException e) {
         return null;
      }
   }

   // Desc: Set the iterator to inorder traversal
   public void inorder() {
      inorderRec(this.root);
   }

   // Desc: Recursive implementation of inorder()
   private void inorderRec(TennisPlayerContainerNode currNode) {
      // Checks if array is empty
      if (currNode == null) {
         return; // Do nothing
      }

      inorderRec(currNode.getLeftChild()); // Checks the left side
      this.queue.enqueue(currNode.getPlayer());
      inorderRec(currNode.getRightChild()); // Checks the right side
   }

   // Desc: Sets the iterator to preorder traversal
   public void preorder() {
      preorderRec(this.root);
   }

   // Desc: Recursive implementation of preorder()
   private void preorderRec(TennisPlayerContainerNode currNode) {
      // Checks if array is empty
      if (currNode == null) {
         return; // Do nothing
      }

      this.queue.enqueue(currNode.getPlayer()); // enqueue currNode
      preorderRec(currNode.getLeftChild()); // enqueue left side
      preorderRec(currNode.getRightChild()); // enqueue right side
   }

   // Desc: Sets the iterator to postorder traversal
   public void postorder() {
      postorderRec(this.root);
   }

   // Desc: Recursive implementation of postorder()
   private void postorderRec(TennisPlayerContainerNode currNode) {
      // Checks if array is empty
      if (currNode == null) {
         return; // Do nothing
      }

      postorderRec(currNode.getLeftChild()); // enqueue left side
      postorderRec(currNode.getRightChild()); // enqueue right side
      this.queue.enqueue(currNode.getPlayer()); // enqueue currNode
   }

   // Desc: Sets the iterator to reverse inorder traversal
   public void reverseInorder() {
      reverseInorderRec(this.root);
   }

   // Desc: Recursive implementation of reverseInorder()
   private void reverseInorderRec(TennisPlayerContainerNode currNode) {
      // Checks if array is empty
      if (currNode == null) {
         return; // Do nothing
      }

      reverseInorderRec(currNode.getRightChild()); // enqueue right side
      this.queue.enqueue(currNode.getPlayer()); // enqueue currNode
      reverseInorderRec(currNode.getLeftChild()); // enqueue left side
   }

}
