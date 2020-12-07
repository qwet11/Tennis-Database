/*
 * William Moss
 * CS-102, Fall 2020
 * Assignment 2
 * Last modified: Dec 5, 2020
 */

package TennisDatabase;

public class TennisMatch implements TennisMatchInterface {
   private String IdPlayer1;
   private String IdPlayer2;
   private int dateYear;
   private int dateMonth;
   private int dateDay;
   private String tournamentName;
   private String matchScore;
   private int winner;

   public TennisMatch(String id1, String id2, int year, int month, int day, String tournament, String score) {
      this.IdPlayer1 = id1;
      this.IdPlayer2 = id2;
      this.dateYear = year;
      this.dateMonth = month;
      this.dateDay = day;
      this.tournamentName = tournament;
      this.matchScore = score;
      this.winner = TennisMatchInterface.processMatchScore(score);
   }

   // Desc: Returns true if a player with the playerId given played in the match
   public boolean hasPlayer(String playerId) {
      if (this.IdPlayer1.equals(playerId) || this.IdPlayer2.equals(playerId)) {
         return true;
      } else {
         return false;
      }
   }

   // Accessor Methods
   public String getIdPlayer1() {
      return this.IdPlayer1;
   }

   public String getIdPlayer2() {
      return this.IdPlayer2;
   }

   public int getDateYear() {
      return this.dateYear;
   }

   public int getDateMonth() {
      return this.dateMonth;
   }

   public int getDateDay() {
      return this.dateDay;
   }

   public String getTournament() {
      return this.tournamentName;
   }

   public String getMatchScore() {
      return this.matchScore;
   }

   public int getWinner() {
      return this.winner;
   }

   // Desc: Compares matches by date (most recent is greater than less recent)
   @Override
   public int compareTo(TennisMatch match2) {
      // Checks if match2 is null
      if (match2 == null) {
         return 1;
      }

      // Compares years of match
      if (this.dateYear > match2.getDateYear()) {
         return 1;
      } else if (this.dateYear < match2.getDateYear()) {
         return -1;
      } else {
         // Compares months of match
         if (this.dateMonth > match2.getDateMonth()) {
            return 1;
         } else if (this.dateMonth < match2.getDateMonth()) {
            return -1;
         } else {
            // Compares days of match
            if (this.dateDay > match2.getDateDay()) {
               return 1;
            } else if (this.dateDay < match2.getDateDay()) {
               return -1;
            } else {
               return 0;
            }
         }
      }
   }
}