/*
 * William Moss
 * CS-102, Fall 2020
 * Assignment 2
 * Last modified: Dec 5, 2020
 */

import TennisDatabase.TennisDatabase;
import TennisDatabase.TennisDatabaseException;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisPlayer;
import TennisDatabase.TennisMatch;
import java.util.InputMismatchException;
import java.util.Scanner;

// Desc: Manager for console-based menu for tennis database project
public class Assignment2 {

   // Desc: Prints the match in the appropriate format
   // NOTE: This method is made to avoid retyping this code multiple times
   public static String printMatch(TennisDatabase tdb, TennisMatch match) {
      String tempDay;
      String tempMonth;

      int dateDay = match.getDateDay();
      int dateMonth = match.getDateMonth();

      // Fixes the days less than 10 to 0X (where X is a number)
      if (dateDay < 10) {
         tempDay = "0" + dateDay;
      } else {
         tempDay = Integer.toString(dateDay);
      }

      // Fixes the months less than 10 to 0X (where X is a number)
      if (dateMonth < 10) {
         tempMonth = "0" + dateMonth;
      } else {
         tempMonth = Integer.toString(dateMonth);
      }

      // To get names of id
      TennisPlayer tempPlayer1 = tdb.getPlayer(match.getIdPlayer1());
      TennisPlayer tempPlayer2 = tdb.getPlayer(match.getIdPlayer2());

      String firstName1 = tempPlayer1.getFirstName();
      String lastName1 = tempPlayer1.getLastName();
      String firstName2 = tempPlayer2.getFirstName();
      String lastName2 = tempPlayer2.getLastName();

      return String.format("%s/%s/%s, %s.%s - %s.%s, %s, %s", match.getDateYear(), tempMonth, tempDay,
            firstName1.substring(0, 1), lastName1, firstName2.substring(0, 1), lastName2, match.getTournament(),
            match.getMatchScore());
   }

   // Main function (entry point) for the tennis database project
   public static void main(String[] args) throws TennisDatabaseException, TennisDatabaseRuntimeException {
      System.out.println("\n"); // To make this look a bit better

      // Creates a tennis database object
      TennisDatabase tdb = new TennisDatabase();

      // Read input file name from command line arguments
      try {
         String fileName = args[0]; // Use 1st command line argument (run argument) as the input file
         tdb.loadFromFile(fileName); // Load file into tennis database

      } catch (TennisDatabaseException e) {
         System.out.println("File with given name does not exist. Loading unsuccessful!");
         // Since the loading was unsuccessful, we want to discard all the information on
         // the input file
         tdb = new TennisDatabase();

      } catch (TennisDatabaseRuntimeException e) {
         System.out.println("Input given is not valid. Loading unsuccessful!");
         tdb = new TennisDatabase(); // Same as above

      } catch (Exception e) {
         System.out.println("Loading unsuccessful!");
         tdb = new TennisDatabase(); // Same as above
      }

      // Start the console-based menu (loop)
      boolean noErrors; // To easily handle any error
      int command = 0; // To make the scope of the variable for all of the database interactions
      boolean runDatabase = true;
      Scanner input = new Scanner(System.in);
      while (runDatabase) {
         noErrors = false; // Resets the term

         // Shows commands
         System.out.println("\n");
         System.out.println("CS-102 Tennis Manager - Available commands:");
         System.out.println("1 --> Print All Players");
         System.out.println("2 --> Print All Matches of a Player");
         System.out.println("3 --> Print All Matches");
         System.out.println("4 --> Insert New Player");
         System.out.println("5 --> Insert New Match");
         System.out.println("6 --> Exit");
         System.out.println("7 --> Print A Player");
         System.out.println("8 --> Delete A Player");
         System.out.println("9 --> Reset The Database");
         System.out.println("10 --> Import From File");
         System.out.println("11 --> Export From File");

         while (!noErrors) {
            try {
               // Gets command
               System.out.print("Your Choice? ");
               command = input.nextInt();
               input.nextLine(); // Throws away the \n because of the nextInt
               System.out.println("\n");

               noErrors = true; // Only happens if no error
            } catch (InputMismatchException e) {
               System.out.println("\nPlease only input a valid number for the command\n");
               input.nextLine(); // Gets rid of the bad input from the scanner. Without this, there will be an
                                 // infinite loop
            }
         }
         // Execute command
         // I used a switch since it's a bit easier to read than a bunch of if/else
         // statements
         // Note: To make my life easier, I added the case number at the end of common
         // variables names (e.g players_1)

         noErrors = false; // Resets the noErrors
         switch (command) {
            case 1:
               // Choice: Prints all players
               try {
                  TennisPlayer[] players_1 = tdb.getAllPlayers();

                  for (TennisPlayer player_1 : players_1) {
                     System.out.println(String.format("%s: %s %s, %d, %s, %d/%d", player_1.getId(),
                           player_1.getFirstName(), player_1.getLastName(), player_1.getBirthYear(),
                           player_1.getCountry(), player_1.getNumWin(), player_1.getNumLoss()));
                  }

               } catch (TennisDatabaseRuntimeException e) {
                  System.out.println("No players registered yet!");
               }

               break;
            case 2:
               // Choice: Prints All Matches of a Player
               while (!noErrors) {
                  try {
                     System.out.print("Enter Player ID (Enter 'exit' to return to main menu): ");
                     String id_2 = input.nextLine().toUpperCase().replaceAll(" ", ""); // Auto-caps and removes white
                                                                                       // space

                     System.out.println("\n"); // Just to be a bit neater

                     if (id_2.compareTo("EXIT") == 0) {
                        noErrors = true; // To exit loop
                     } else {
                        TennisMatch[] matches_2 = tdb.getMatchesOfPlayer(id_2);
                        for (TennisMatch match_2 : matches_2) {
                           System.out.println(printMatch(tdb, match_2));
                        }
                     }

                     noErrors = true; // Only will happen if no errors
                  } catch (TennisDatabaseException e) {
                     System.out.println("Given ID doesn't exist. Please enter a valid ID\n");
                  } catch (TennisDatabaseRuntimeException e) {
                     System.out.println("There are no matches for this player\n");
                  }
               }

               break;
            case 3:
               try {
                  // Choice: Print All Matches
                  TennisMatch[] matches_3 = tdb.getAllMatches();

                  for (TennisMatch match_3 : matches_3) {
                     System.out.println(printMatch(tdb, match_3));
                  }
               } catch (TennisDatabaseRuntimeException e) {
                  System.out.println("No matches entered yet!");
               }

               break;
            case 4:
               // Choice: Insert a new player
               while (!noErrors) {
                  try {
                     System.out.print("Enter Player ID:");
                     String id_4 = input.nextLine().toUpperCase().replaceAll(" ", "");
                     System.out.print("Enter Player's First Name: ");
                     String firstName_4 = input.nextLine().toUpperCase().replaceAll(" ", "");
                     System.out.print("Enter Player's Last Name: ");
                     String lastName_4 = input.nextLine().toUpperCase().replaceAll(" ", "");

                     // Checks for valid year (1800 - 2100)
                     int year_4;
                     do {
                        System.out.print("Enter Player Birth Year: ");
                        year_4 = input.nextInt();
                        input.nextLine(); // To cancel the nextInt()

                        if (year_4 < 1700 || year_4 > 2100) {
                           System.out.println("\nPlease enter a more realistic year (from 1700 - 2100)!\n");
                        }
                     } while (year_4 < 1700 || year_4 > 2100);

                     System.out.print("Enter Player's Country: ");
                     String country_4 = input.nextLine().toUpperCase().replaceAll(" ", "");

                     // Checks with user if the info entered is what they want
                     System.out.println("The info entered was:\n"
                           + String.format("%s: %s %s, %d, %s", id_4, firstName_4, lastName_4, year_4, country_4));

                     String response; // For user's response (yes or no)
                     do {
                        System.out.print("Is this information correct (please enter 'yes', 'no', or 'exit'): ");
                        response = input.nextLine().toUpperCase();
                        if (!(response.equals("NO") || response.equals("YES") || response.equals("EXIT"))) {
                           System.out.println("\nInvalid response. Please only enter yes, no, or exit!\n");
                        }
                     } while (!(response.equals("NO") || response.equals("YES") || response.equals("EXIT")));

                     if (response.equals("YES")) {
                        tdb.insertPlayer(id_4, firstName_4, lastName_4, year_4, country_4);
                        noErrors = true; // Will only happen if no errors
                     } else if (response.equals("EXIT")) {
                        break; // Go to the main menu
                     } else {
                        noErrors = false; // Restarts the input process
                     }

                  } catch (InputMismatchException e) {
                     // Will only happen if invalid birth year is inputted
                     System.out.println("\nInvalid year inputted. Please only enter a number as the year\n");
                     input.nextLine(); // Resets nextInt()
                  } catch (TennisDatabaseException e) {
                     System.out.println("\nGiven id already exists. Please enter a different id\n");
                  }
               }
               break;
            case 5:
               // Choice: Insert a new match
               while (!noErrors) {
                  try {
                     System.out.print("Enter Player 1 ID: ");
                     String player1Id_5 = input.nextLine().toUpperCase().replaceAll(" ", "");
                     System.out.print("Enter Player 2 ID: ");
                     String player2Id_5 = input.nextLine().toUpperCase().replaceAll(" ", "");

                     // Checks for valid year
                     int year_5;
                     do {
                        System.out.print("Enter Match Year: ");
                        year_5 = input.nextInt();
                        input.nextLine();

                        // Checks if year is realistic (1900-2100)
                        if (year_5 < 1700 || year_5 > 2100) {
                           System.out.println("\nPlease enter a more realistic year (from 1700 - 2100)!\n");
                        }
                     } while (year_5 < 1700 || year_5 > 2100);

                     // Checks for valid month
                     int month_5;
                     do {
                        System.out.print("Enter Match Month (as number): ");
                        month_5 = input.nextInt();
                        input.nextLine();

                        // Checks if month is valid (1-12)
                        if (month_5 <= 0 || month_5 > 12) {
                           System.out.println("\nPlease enter a valid month number (from 1 - 12)!\n");

                        }
                     } while (month_5 <= 0 || month_5 > 12);

                     // Checks for valid day
                     int day_5;
                     do {
                        System.out.print("Enter Match Day: ");
                        day_5 = input.nextInt();
                        input.nextLine();

                        // Checks if day is valid (1-31)
                        if (day_5 <= 0 || day_5 > 31) {
                           System.out.println("\nPlease enter a valid day number (from 1 - 31)!\n");
                        }
                     } while (day_5 <= 0 || day_5 > 31);

                     System.out.print("Enter Tournament Name/Location: ");
                     String tournament_5 = input.nextLine().toUpperCase().replaceAll(" ", "");
                     System.out.print("Enter Match Score, with no spaces and in the format, (e.g 6-4,2-6,7-6): ");
                     String score_5 = input.nextLine().replaceAll(" ", "");

                     // Checks with user if the info entered is what they want
                     System.out.println("The info entered was:\n");

                     System.out.println(String.format("%s/%s/%s, %s - %s, %s, %s", year_5, month_5, day_5, player1Id_5,
                           player2Id_5, tournament_5, score_5));

                     String response; // For user's response (yes or no)
                     do {
                        System.out.print("Is this information correct (please enter 'yes', 'no', or 'exit'): ");
                        response = input.nextLine().toUpperCase();
                        if (!(response.equals("NO") || response.equals("YES") || response.equals("EXIT"))) {
                           System.out.println("\nInvalid response. Please only enter yes, no, or exit!\n");
                        }
                     } while (!(response.equals("NO") || response.equals("YES") || response.equals("EXIT")));

                     if (response.equals("YES")) {
                        tdb.insertMatch(player1Id_5, player2Id_5, year_5, month_5, day_5, tournament_5, score_5);
                        noErrors = true;
                     } else if (response.equals("EXIT")) {
                        break; // Go to main menu
                     } else {
                        noErrors = false; // Restarts the input process
                     }

                  } catch (TennisDatabaseRuntimeException | TennisDatabaseException e) {
                     System.out.println(
                           "\nInvalid data entered. Please ensure that the ids exist & are unique and that the match score are in the asked format.\n");
                  } catch (InputMismatchException e) {
                     System.out.println(
                           "\nInvalid data entered. Please only enter a number for the year, month, and day\n");
                     input.nextLine(); // Resets the nextInt()
                  }
               }
               break;
            case 6:
               // Choice: Exit
               runDatabase = false;
               break;
            case 7:
               // Choice: Print a Player
               while (!noErrors) {
                  try {
                     System.out.print("Enter Player ID (Enter 'exit' to return to main menu): ");
                     String id_7 = input.nextLine().toUpperCase().replaceAll(" ", ""); // Auto-caps and removes white
                                                                                       // space

                     System.out.println("\n"); // Just to be a bit neater

                     if (id_7.compareTo("EXIT") == 0) {
                        break; // Go to main menu
                     } else {
                        TennisPlayer player_7 = tdb.getPlayer(id_7);
                        System.out.println(String.format("%s: %s %s, %d, %s, %d/%d", player_7.getId(),
                              player_7.getFirstName(), player_7.getLastName(), player_7.getBirthYear(),
                              player_7.getCountry(), player_7.getNumWin(), player_7.getNumLoss()));
                     }

                     noErrors = true; // Only will happen if no errors
                  } catch (TennisDatabaseRuntimeException e) {
                     System.out.println("\nGiven ID doesn't exist. Please enter valid ID\n");
                  }
               }

               break;
            case 8:
               // Choice: Delete a Player
               while (!noErrors) {
                  try {
                     System.out.print("Enter Player ID (Enter 'exit' to return to main menu): ");
                     String id_8 = input.nextLine().toUpperCase().replaceAll(" ", ""); // Auto-caps and removes white
                                                                                       // space

                     System.out.println("\n"); // Just to be a bit neater

                     if (id_8.compareTo("EXIT") == 0) {
                        break; // Go to main menu
                     } else {
                        tdb.deletePlayer(id_8);
                     }

                     noErrors = true; // Only will happen if no errors
                  } catch (TennisDatabaseException e) {
                     System.out.println("\nGiven ID doesn't exist. Please enter a valid ID\n");
                  } catch (TennisDatabaseRuntimeException e) {
                     // Since this just means that there were no matches, just continue and do
                     // nothing
                     noErrors = true;
                  }
               }

               break;
            case 9:
               // Choice: Reset the Database
               tdb = new TennisDatabase();
               System.out.println("\nDatabase reset!\n");
               break;
            case 10:
               // Choice: Import from File
               try {
                  // Gets filename
                  System.out.print("Enter filename: ");
                  String fileName_10 = input.nextLine();
                  TennisDatabase tempTdb = new TennisDatabase();
                  tempTdb.loadFromFile(fileName_10); // Load file into the temp tennis database
                  tdb = tempTdb; // If loading was successful, then we will update the current database
               } catch (TennisDatabaseException e) {
                  System.out.println("File with given name does not exist. Loading unsuccessful!");

               } catch (TennisDatabaseRuntimeException e) {
                  System.out.println("Input given is not valid. Loading unsuccessful!");

               } catch (Exception e) {
                  System.out.println("Loading unsuccessful!");
               }

               break;
            case 11:
               while (!noErrors) {
                  // Choice: Export to File
                  System.out.print("Enter filename (Enter 'exit' to return to main menu): ");
                  String fileName_11 = input.nextLine();

                  if (fileName_11.toUpperCase().equals("EXIT")) {
                     break; // Go to main menu
                  }

                  try {
                     tdb.saveToFile(fileName_11);
                     noErrors = true;
                  } catch (TennisDatabaseException e) {
                     System.out.println(
                           "\nThere was an error saving the data. Please reenter the filename (ensure that you add the .txt extension)\n");
                  }
               }

               break;
            default:
               System.out.println("Invalid input given. Please input a valid number from 1 - 11");
               break;
         }

      }
      System.out.println("You are now exiting the Tennis Manager!");
      input.close(); // Close Scanner
   }
}