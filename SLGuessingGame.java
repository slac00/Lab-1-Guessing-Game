// Sid Lacy
// 04.17.2020
// CS145, Spring Quarter of 2020
// Lab 1: Guessing Game

// Improvement of Guessing Game lab from CS140
// Plays a simple number guessing game

import java.util.Random; // For creating pseudorandom number to guess
import java.util.Scanner; // For user input object

public class SLGuessingGame {
    
    public enum Status { // Status of guess (too high, low); Did I use enum correctly?

        HIGHER, LOWER;

    }
    
    public static void main(final String args[]) {

        final int max = 100; // Guess range: 1 to max (simply moved global constant into main())

        final Random rand = new Random(); // Random number object
        final Scanner input = new Scanner(System.in); // User input object

        boolean playAgain = true; // Primes boolean value to start games
        int totalGames = 0; // Primes counter for number of total games played
        int totalGuesses = 0; // Primes counter for total guesses made
        int bestGame = 9999; // Primes highscore tracker (lower is better)
        int guesses = 0; // Primes local game guess counter

        introduction(max); // Calls intro to game

        while (playAgain) { // Loops new games until user quits

            System.out.printf("\nI'm thinking of a number between 1 and %d...\n", max);
            final int n = rand.nextInt(max + 1); // Generates number for user to guess
            // n = 42; // For testing; uncomment to set n = 42

            guesses = singleGame(input, n, guesses, max); // Starts game, retrieves guess count

            if (guesses < bestGame) { // Updates high score
                bestGame = guesses;
            }

            totalGuesses += guesses; // Adds guesses in last game to total guess count
            totalGames++; // Increments total number of games played
            guesses = 0; // resets local game guess counter

            System.out.print("Do you want to play again? ");
            final String strPlayAgain = input.next();
            if (strPlayAgain.charAt(0) == 'n' || strPlayAgain.charAt(0) == 'N') { // End game?
                playAgain = false;
                results(totalGames, totalGuesses, bestGame); // Calls results
            }

        }

    }

    // Plays a single game
    public static int singleGame(final Scanner input, final int n, int guesses, final int max) {

        int currentGuess = 0; // Primes var for current guess
        while (currentGuess != n) { // Prompts for new guess if last guess incorrect

            System.out.print("Your guess? ");
            try { // Crash-resistance against invalid input

                currentGuess = input.nextInt(); // Prompts for a guess

                if (currentGuess > max || currentGuess < 1) { // Guess out of range
                    System.out.printf("Out of bounds! Range: 1 - %d\n", max);
                    guesses--; // Ensures that out of bounds guess is not counted
                } else if (currentGuess > n) { // Determines if guess is too low...
                    System.out.println("It's " + Status.LOWER + "."); // String enums can't be...
                } else if (currentGuess < n) { // ...or too high
                    System.out.println("It's " + Status.HIGHER + "."); // ... printed with printf
                }

                guesses++; // Increments guess counter by 1 every valid turn

            }

            catch (final java.util.InputMismatchException ex) {

                System.out.println("Invalid input! Only integers are allowed.");
                currentGuess = 0; // Resets currentGuess to 0, doesn't count turn as guess
                input.nextLine(); // Prevents try-catch indefinite loop

            }

        }

        System.out.printf("You got it right in %d guess", guesses);
        switch (guesses) { // Determines whether "guess" in string above needs to be plural

            case 1: // "Guess" does not need to be plural
                System.out.println();
                break;
            default: // "Guess" does need to be plural
                System.out.println("es");
                break;

        }

        return guesses; // Returns number of guesses made during game to main()

    }

    // Prints statistics when user quits
    public static void results(final int totalGames, final int totalGuesses, final int bestGame) {

        System.out.print("\nOverall results:");
        System.out.printf("\n    total games   = %d", totalGames);
        System.out.printf("\n    total guesses = %d", totalGuesses);
        System.out.printf("\n    guesses/game  = %.1f", (double) totalGuesses / totalGames);
        System.out.printf("\n    best game     = %d\n", bestGame);

    }

    // Prints intro to game
    public static void introduction(final int max) {

        System.out.printf("This program allows you to play a guessing game.\nI will think " +
            "of a number between 1 and\n%d and will allow you to guess until\nyou get it. " +
            "For each guess, I will tell you\nwhether the right answer is higher or lower" +
            "\nthan your guess.\n", max); // Combined multiple print statments into single printf

    }

}