/*
 * William Moss
 * CS-102, Fall 2020
 * Assignment 2
 * Last modified: Dec 5, 2020
 */

package TennisDatabase;

// Desc: Class (public) serving as a blueprint for tennis player objects
public class TennisPlayer implements TennisPlayerInterface {
   private String playerID;
   private String firstName;
   private String lastName;
   private int birthYear;
   private String playerCountry;
   private int numWin;
   private int numLoss;

   public TennisPlayer(String id, String first, String last, int year, String country) {
      this.playerID = id.toUpperCase();
      this.firstName = first.toUpperCase();
      this.lastName = last.toUpperCase();
      this.birthYear = year;
      this.playerCountry = country.toUpperCase();
   }

   // Sets number of wins to num
   public void setWin(int num) {
      this.numWin = num;
   }

   // Sets number of loss to num
   public void setLoss(int num) {
      this.numLoss = num;
   }

   // Accessor methods

   public String getId() {
      return this.playerID;
   }

   public String getFirstName() {
      return this.firstName;
   }

   public String getLastName() {
      return this.lastName;
   }

   public int getBirthYear() {
      return this.birthYear;
   }

   public String getCountry() {
      return this.playerCountry;
   }

   public int getNumWin() {
      return this.numWin;
   }

   public int getNumLoss() {
      return this.numLoss;
   }

   // Returns Win/Loss string
   public String getWinLossRecord() {
      return this.numWin + "/" + this.numLoss;
   }

   // Desc: Compares players based on their id
   @Override
   public int compareTo(TennisPlayer player2) {
      return this.playerID.compareTo(player2.getId());
   }
}