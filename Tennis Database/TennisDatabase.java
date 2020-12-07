/*
 * William Moss
 * CS-102, Fall 2020
 * Assignment 2
 * Last modified: Dec 5, 2020
 */

package TennisDatabase;

import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

// Desc: Manager class implementing the tennis database to store players and matches
public class TennisDatabase implements TennisDatabaseInterface {
   private TennisMatchContainer matchContainer;
   private TennisPlayerContainer playerContainer;

   public TennisDatabase() {
      this.matchContainer = new TennisMatchContainer();
      this.playerContainer = new TennisPlayerContainer();
   }

   // Desc.: Loads data from file following the format described in the
   // specifications.
   // Output: Throws an unchecked (non-critical) exception if the loading is not
   // fully successful.
   // Throws a checked (critical) exception if the file (file name) does not exist.
   public void loadFromFile(String fileName) throws TennisDatabaseException, TennisDatabaseRuntimeException {
      Scanner input; // Declared here so the scope is for the entire method
      try {
         input = new Scanner(new File(fileName));
      } catch (Exception e) {
         // If file doesn't exist
         throw new TennisDatabaseException("File does not exist");
      }
      try {
         boolean finishedPlayers = false;
         boolean finishedMatches = false;
         String line = input.nextLine();

         // Inputs all players from file
         while (!finishedPlayers) {
            String[] playerInfo = line.split("/");

            // Checks if the input is valid
            if (playerInfo.length != 6) {
               throw new TennisDatabaseRuntimeException("Loading is not successful: invalid input file");
            }

            // Checks if I should stop entering players
            if (playerInfo[0].compareTo("PLAYER") != 0) {
               finishedPlayers = true;
            } else {
               // Adds player to the TennisPlayerContainer
               String id = playerInfo[1];
               String firstName = playerInfo[2];
               String lastName = playerInfo[3];
               int year = Integer.parseInt(playerInfo[4]);
               String country = playerInfo[5];
               insertPlayer(id, firstName, lastName, year, country);

               line = input.nextLine();
            }
         }

         // Inputs all matches from file
         while (!finishedMatches) {
            String[] matchInfo = line.split("/");

            // Checks if I should stop entering matches
            if (matchInfo[0] == null) {
               return;
            }

            // Checks if the input is valid
            if (matchInfo.length != 6 || matchInfo[0].compareTo("MATCH") != 0) {
               throw new TennisDatabaseRuntimeException("Loading is not successful: invalid input file");
            } else {
               // Adds match to TennisMatchContainer
               String idPlayer1 = matchInfo[1];
               String idPlayer2 = matchInfo[2];
               String fullDate = matchInfo[3];
               String tournament = matchInfo[4];
               String score = matchInfo[5];

               // Separates the full date into year, month, and day
               int year = Integer.parseInt(fullDate.substring(0, 4));
               int month = Integer.parseInt(fullDate.substring(4, 6));
               int day = Integer.parseInt(fullDate.substring(6, 8));

               // Adds match to TennisMatchContainer
               insertMatch(idPlayer1, idPlayer2, year, month, day, tournament, score);

               if (input.hasNextLine()) {
                  line = input.nextLine();
               } else {
                  return;
               }
            }
         }
      } catch (TennisDatabaseException | TennisDatabaseRuntimeException | NumberFormatException
            | IndexOutOfBoundsException e) {
         // To catch all errors thrown from the methods used
         throw new TennisDatabaseRuntimeException("Loading is not successful: invalid input file");
      } finally {
         input.close(); // Closes the scanner
      }
   }

   // Desc.: Saves data to file following the format described in the
   // specifications.
   // Output: Throws a checked (critical) exception if the file open for writing
   // fails.
   public void saveToFile(String fileName) throws TennisDatabaseException {
      // Checks if the fileName is valid (has the extension .txt)
      if (!fileName.endsWith(".txt")) {
         throw new TennisDatabaseException("TennisDatabase: Invalid file extension");
      }

      try {
         FileWriter writer = new FileWriter(fileName);
         TennisPlayerContainerIterator playerIterator = this.playerContainer.iterator();
         playerIterator.preorder(); // Preorder so that the internal data structure is preserved

         while (playerIterator.hasNext()) {
            TennisPlayer player = playerIterator.next();
            writer.write(
                  String.format("PLAYER/%s/%s/%s/%d/%s\n", player.getId(), player.getFirstName(), player.getLastName(),
                        player.getBirthYear(), player.getCountry(), player.getNumWin(), player.getNumLoss()));
         }

         Iterator<TennisMatch> matchIterator = this.matchContainer.iterator();

         while (matchIterator.hasNext()) {
            TennisMatch match = matchIterator.next();
            writer.write(String.format("MATCH/%s/%s/%d/%s/%s\n", match.getIdPlayer1(), match.getIdPlayer2(),
                  match.getDateYear() * 10000 + match.getDateMonth() * 100 + match.getDateDay(), match.getTournament(),
                  match.getMatchScore()));
         }

         writer.close();

      } catch (Exception e) {
         // Removes the corrupt file completely
         File file = new File(fileName);
         file.delete();

         throw new TennisDatabaseException("TennisDatabase: File writing failed");
      }
   }

   // Desc.: Resets the database, making it empty.
   public void reset() {
      this.matchContainer = new TennisMatchContainer();
      this.playerContainer = new TennisPlayerContainer();
   }

   // Desc.: Search for a player in the database by input id, and returns a copy of
   // that player (if found).
   // Output: Throws an unchecked (non-critical) exception if there is no player
   // with that input id.
   public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException {
      return this.playerContainer.getPlayer(id);
   }

   // Desc.: Returns copies (deep copies) of all players in the database arranged
   // in the output array (sorted by id, alphabetically).
   // Output: Throws an unchecked (non-critical) exception if there are no players
   // in the database.
   public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
      return this.playerContainer.getAllPlayers();
   }

   // Desc.: Returns copies (deep copies) of all matches of input player (id)
   // arranged in the output array (sorted by date, most recent first).
   // Input: The id of a player.
   // Output: Throws a checked (critical) exception if the player (id) does not
   // exists.
   // Throws an unchecked (non-critical) exception if there are no matches (but the
   // player id exists).
   public TennisMatch[] getMatchesOfPlayer(String playerId)
         throws TennisDatabaseException, TennisDatabaseRuntimeException {
      return this.matchContainer.getMatchesOfPlayer(playerId);
   }

   // Desc.: Returns copies (deep copies) of all matches in the database arranged
   // in the output array (sorted by date, most recent first).
   // Output: Throws an unchecked (non-critical) exception if there are no matches
   // in the database.
   public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
      return this.matchContainer.getAllMatches();
   }

   // Desc.: Insert a player into the database.
   // Input: All the data required for a player.
   // Output: Throws a checked (critical) exception if player id is already in the
   // database.
   public void insertPlayer(String id, String firstName, String lastName, int year, String country)
         throws TennisDatabaseException {
      this.playerContainer.insertPlayer(new TennisPlayer(id, firstName, lastName, year, country));
   }

   // Desc.: Search for a player in the database by id, and delete it with all his
   // matches (if found).
   // Output: Throws a checked (critical) exception if there is no player
   // with that input id.
   // Throws an unchecked (non-critical) exception if there is no match with the
   // player
   public void deletePlayer(String playerId) throws TennisDatabaseRuntimeException, TennisDatabaseException {
      this.playerContainer.deletePlayer(playerId);
      this.matchContainer.deleteMatchesOfPlayer(playerId);
   }

   // Desc.: Insert a match into the database.
   // Input: All the data required for a match.
   // Output: Throws a checked (critical) exception if a player does not exist in
   // the database.
   // Throws a checked (critical) exception if the match score is not valid.
   public void insertMatch(String idPlayer1, String idPlayer2, int year, int month, int day, String tournament,
         String score) throws TennisDatabaseException {
      TennisMatch m = new TennisMatch(idPlayer1, idPlayer2, year, month, day, tournament, score);
      this.playerContainer.insertMatch(m);
      this.matchContainer.insertMatch(m);
   }

}