/*
 * William Moss
 * CS-102, Fall 2020
 * Assignment 2
 * Last modified: Dec 5, 2020
 */

package TennisDatabase;

import java.util.Iterator;
import java.util.LinkedList;

//Desc: Container for tennis matches
class TennisMatchContainer implements TennisMatchContainerInterface {
   private LinkedList<TennisMatch> listOfMatches;

   public TennisMatchContainer() {
      this.listOfMatches = new LinkedList<TennisMatch>();
   }

   // Desc.: Returns the number of matches in this container.
   // Output: The number of matches in this container as an integer.
   public int getNumMatches() {
      return this.listOfMatches.size();
   }

   // Desc.: Returns an iterator object ready to be used to iterate this container.
   // Output: The iterator object configured for this container.
   public Iterator<TennisMatch> iterator() {
      return this.listOfMatches.iterator();
   }

   // Desc.: Insert a tennis match into this container.
   // Input: A tennis match.
   // Output: Throws a checked (critical) exception if the container is full.
   public void insertMatch(TennisMatch m) throws TennisDatabaseException {
      // Finds the index where the match should go
      for (int i = 0; i < this.listOfMatches.size(); i++) {
         if (m.compareTo(this.listOfMatches.get(i)) > 0) {
            // Inserts at the index
            this.listOfMatches.add(i, m);
            return;
         }
      }

      // Since match wasn't inserted yet, insert at end
      this.listOfMatches.add(m);
   }

   // Desc.: Returns all matches in the database arranged in the output array
   // (sorted by date, most recent first).
   // Output: Throws an exception if there are no matches in this container.
   public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
      TennisMatch[] matches = this.listOfMatches.toArray(new TennisMatch[this.listOfMatches.size()]);

      // Checks if matches is empty
      if (matches.length == 0) {
         throw new TennisDatabaseRuntimeException("TennisMatchContainer: No matches in container");
      }

      return matches;
   }

   // Desc.: Returns all matches of input player (id) arranged in the output array
   // (sorted by date, most recent first).
   // Input: The id of the tennis player.
   // Output: Throws an unchecked (non-critical) exception if there are no tennis
   // matches in the list.
   public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
      int count = 0; // Count for the number of matches of player

      // Traverse the list to get a count of matches of player
      for (int i = 0; i < this.listOfMatches.size(); i++) {
         if (this.listOfMatches.get(i).hasPlayer(playerId)) {
            count++;
         }
      }

      if (count == 0) {
         throw new TennisDatabaseRuntimeException("TennisMatchContainer: No tennis match with id");
      }

      TennisMatch[] matches = new TennisMatch[count];
      int index = 0;

      // Re-traverse the list to get the matches of player
      for (int i = 0; i < this.listOfMatches.size(); i++) {
         if (this.listOfMatches.get(i).hasPlayer(playerId)) {
            matches[index] = this.listOfMatches.get(i);
            index++;
         }
      }

      return matches;
   }

   // Desc.: Delete all matches of a player by id (if found).
   // Output: Throws an unchecked (non-critical) exception if there is no match
   // with that input id.
   public void deleteMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
      int counter = 0; // To check if there was a match
      for (int i = 0; i < this.listOfMatches.size(); i++) {
         if (this.listOfMatches.get(i).hasPlayer(playerId)) {
            this.listOfMatches.remove(i);
            i--;
            counter++;
         }
      }
      // Checks if there was a match with the id
      if (counter == 0) {
         throw new TennisDatabaseRuntimeException("TennisMatch: No match with input id");
      }
   }

}