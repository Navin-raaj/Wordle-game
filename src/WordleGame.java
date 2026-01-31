import java.util.*;

public class wordle {
    private static final String[] WORD_LIST = {
            "APPLE", "BRAVE", "CRANE", "STONE", "WORLD", "HELLO", "HONEY"
    };
    private static final int MAX_ATTEMPTS = 6;

    // ANSI escape color codes
    private static final String GREEN = "\u001B[42m\u001B[30m";  // Green bg, black text
    private static final String YELLOW = "\u001B[43m\u001B[30m"; // Yellow bg, black text
    private static final String GRAY = "\u001B[47m\u001B[30m";   // Gray bg, black text
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        // Pick random word
        String target = WORD_LIST[rand.nextInt(WORD_LIST.length)];
        target = target.toUpperCase();

        System.out.println("Welcome to Wordle in Java!");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess the 5-letter word.\n");

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            System.out.print("Attempt " + attempt + ": Enter your 5-letter guess: ");
            String guess = sc.nextLine().toUpperCase();

            if (guess.length() != 5) {
                System.out.println("❌ Please enter exactly 5 letters!\n");
                attempt--; // don't count invalid attempt
                continue;
            }

            if (guess.equals(target)) {
                printFeedback(guess, target);
                System.out.println("\n🎉 Congratulations! You guessed the word: " + target);
                return;
            }

            printFeedback(guess, target);
            System.out.println();
        }

        System.out.println("❌ Out of attempts! The word was: " + target);
    }

    private static void printFeedback(String guess, String target) {
        char[] feedback = new char[5];
        Arrays.fill(feedback, ' ');

        boolean[] targetUsed = new boolean[5];

        // Step 1: Green letters
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == target.charAt(i)) {
                feedback[i] = 'G';
                targetUsed[i] = true;
            }
        }

        // Step 2: Yellow letters
        for (int i = 0; i < 5; i++) {
            if (feedback[i] == ' ') {
                for (int j = 0; j < 5; j++) {
                    if (!targetUsed[j] && guess.charAt(i) == target.charAt(j)) {
                        feedback[i] = 'Y';
                        targetUsed[j] = true;
                        break;
                    }
                }
                if (feedback[i] == ' ') {
                    feedback[i] = 'X'; // Gray
                }
            }
        }

        // Print feedback with colors
        for (int i = 0; i < 5; i++) {
            char g = guess.charAt(i);
            if (feedback[i] == 'G') System.out.print(GREEN + " " + g + " " + RESET);
            else if (feedback[i] == 'Y') System.out.print(YELLOW + " " + g + " " + RESET);
            else System.out.print(GRAY + " " + g + " " + RESET);
        }
    }
}
