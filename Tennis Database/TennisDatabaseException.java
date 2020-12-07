
// Giuseppe Turini
// CS-102, Fall 2020
// Assignment 2

package TennisDatabase;

// Custom (checked) exception for the TennisDatabase package, representing critical runtime errors (that must be handled).
public class TennisDatabaseException extends java.lang.Exception {

   private static final long serialVersionUID = 8402797256414485414L;

   // Desc.: Constructor.
   // Input: Description of the runtime error.
   public TennisDatabaseException(String s) {
      super(s);
   }

}
