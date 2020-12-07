/*
 * William Moss
 * CS-102, Fall 2020
 * Assignment 2
 * Last modified: Dec 5, 2020
 */

package TennisDatabase;

// Desc: Class (public) serving as a blueprint for tennis player objects
class TennisPlayerContainerNode implements TennisPlayerContainerNodeInterface {

   private TennisPlayer player;
   private TennisPlayerContainerNode childLeft; // Link to the left child node of this node
   private TennisPlayerContainerNode childRight; // Link to the right child node of this node
   private SortedLinkedList<TennisMatch> listOfMatches;

   public TennisPlayerContainerNode(TennisPlayer player) {
      this.player = player;
      this.listOfMatches = new SortedLinkedList<TennisMatch>();
      this.childLeft = null;
      this.childRight = null;
   }

   // Accessors (getters).
   public TennisPlayer getPlayer() {
      return this.player;
   }

   public SortedLinkedList<TennisMatch> getMatchList() {
      return this.listOfMatches;
   }

   public TennisPlayerContainerNode getLeftChild() {
      return this.childLeft;
   }

   public TennisPlayerContainerNode getRightChild() {
      return this.childRight;
   }

   // Modifiers (setters).
   public void setPlayer(TennisPlayer p) {
      this.player = p;
   }

   public void setMatchList(SortedLinkedList<TennisMatch> n) {
      this.listOfMatches = n;
   }

   public void setLeftChild(TennisPlayerContainerNode lc) {
      this.childLeft = lc;
   }

   public void setRightChild(TennisPlayerContainerNode rc) {
      this.childRight = rc;
   }

   // Desc.: Insert a TennisMatch object (reference) into this node.
   // Input: A TennisMatch object (reference).
   // Output: Throws a checked (critical) exception if match cannot be inserted in
   // this player list.
   public void insertMatch(TennisMatch m) throws TennisDatabaseException {
      try {
         this.listOfMatches.insert(m);
      } catch (Exception e) {
         throw new TennisDatabaseException("Match cannot be inserted in this player list");
      }
   }

   // Desc.: Returns all matches of this player arranged in the output array
   // (sorted by date, most recent first).
   // Output: Throws an unchecked (non-critical) exception if there are no matches
   // for this player.
   public TennisMatch[] getMatches() throws TennisDatabaseRuntimeException {
      // Checks if there are matches
      if (this.listOfMatches.size() == 0) {
         throw new TennisDatabaseRuntimeException("No matches for this player");
      }

      TennisMatch[] matches = new TennisMatch[this.listOfMatches.size()];

      for (int i = 0; i < this.listOfMatches.size(); i++) {
         matches[i] = this.listOfMatches.get(i);
      }

      return matches;
   }

   // Desc.: Deletes all matches of input player (id) from this node.
   // Input: The id of the tennis player.
   // Output: Throws an unchecked (non-critical) exception if no matches are
   // deleted.
   public void deleteMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
      int counter = 0; // Used to check if any match was removed
      for (int i = 0; i < this.listOfMatches.size(); i++) {
         // Checks if the player is in the match
         TennisMatch m = this.listOfMatches.get(i);
         if (m.hasPlayer(playerId)) {
            this.listOfMatches.delete(i); // Deletes the match at index i

            // Changes the player's win/loss record
            if (m.getIdPlayer1().equals(this.player.getId())) {
               // This player is the player 1
               if (m.getWinner() == 1) {
                  // This player won so we can decrement it
                  this.player.setWin(this.player.getNumWin() - 1);
               } else {
                  // This player lost
                  this.player.setLoss(this.player.getNumLoss() - 1);
               }
            } else {
               // This player is the player 2
               if (m.getWinner() == 1) {
                  // This player lost
                  this.player.setLoss(this.player.getNumLoss() - 1);
               } else {
                  // This player won
                  this.player.setWin(this.player.getNumWin() - 1);
               }
            }

            counter++;
            i--;
         }
      }

      // Checks if any match was deleted
      if (counter == 0) {
         throw new TennisDatabaseRuntimeException("TennisPlayerContainerNode: No match was deleted");
      }
   }
}