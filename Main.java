package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        BullsAndCows game = new BullsAndCows();
        run(game);
    }
    public static void run(BullsAndCows game) {
        Input input = new Input();
        lengths(input);
        symbols(input);
        String secretCode = randomGenerator(input);
        System.out.println();
        System.out.println("Okay, let's start a game!");
        int i = 1;
        while (true) {
            System.out.printf("Turn %d:", i);
            System.out.println();
            grader(game, secretCode);
            messages(game);
            System.out.println();
            if (isGameEnds(game, secretCode.length())) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else {
                i++;
                game.setCow(0);
                game.setBull(0);
            }
        }
    }
    public static String randomGenerator(Input input) {
        String set = "0123456789abcdefghijklmnopqrstuvwxyz";
        String stars = "************************************";
        String secretCodeString = " ";
        boolean isUnique;
        Random random = new Random();
        while (secretCodeString.trim().length() < input.getLengths()) {
            isUnique = true;
            int index = random.nextInt(input.getSymbols() + 1);
            for (int j = 0; j < secretCodeString.length(); j++) {
                if (set.charAt(index) == secretCodeString.charAt(j)) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                secretCodeString = String.valueOf(set.charAt(index)).concat(secretCodeString);
            }
        }
        if (input.getSymbols() <= 10) {
            System.out.printf("The secret is prepared: %s (0-%c)", stars.substring(0, input.getLengths()), set.charAt(input.getSymbols() - 1));
        } else {
            System.out.printf("The secret is prepared: %s (0-9, a-%c).", stars.substring(0, input.getLengths()), set.charAt(input.getSymbols() - 1));
        }
        return secretCodeString.trim();
    }

    public static void lengths(Input input) {
        Scanner sc = new Scanner(System.in);
            System.out.println("Input the length of the secret code:");
            String lengths = sc.nextLine();
            try {
                input.setLengths(Integer.parseInt(lengths));
            } catch (Exception e) {
                System.out.printf("Error: %s isn't a valid number.", lengths);
                System.exit(1);
            }
            if (input.getLengths() > 36) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique symbols.",
                        input.getLengths());
                System.exit(1);
            } else if (input.getLengths() < 1) {
                System.out.println("Error: can't generate a secret number with a length of less then 1");
                System.exit(1);
            }

    }

    public static void symbols(Input input) {
        Scanner sc = new Scanner(System.in);
            System.out.println("Input the number of possible symbols in the code:");
            String symbols = sc.nextLine();
            try {
                input.setSymbols(Integer.parseInt(symbols));
            } catch (Exception e) {
                System.out.printf("Error: %s isn't a valid number.", symbols);
                System.exit(1);
            }
            if (input.getSymbols() > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(1);
            } if (input.getSymbols() < 1) {
                System.out.println("Error: can't generate a secret number with zero or negative number of symbols");
                System.exit(1);
            } if (input.getSymbols() < input.getLengths()) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.",
                        input.getLengths(), input.getSymbols());
                System.exit(1);
            }
    }

    public static void grader(BullsAndCows game, String secretCodeString) {
        Scanner sc = new Scanner(System.in);
        String input = sc.next().trim();
        char[] guessedCode = new char[input.length()];
        char[] secretCode = new char[secretCodeString.length()];
        for (int i = 0; i < input.length(); i++) {
            guessedCode[i] = input.charAt(i);
            secretCode[i] = secretCodeString.charAt(i);
        }

        for (int i = 0; i < guessedCode.length; i++) {
            if (guessedCode[i] == secretCode[i]) {
                game.incBull();
            } else {
                for (int j = 0; j < guessedCode.length; j++) {
                    if (guessedCode[i] == secretCode[j]) {
                        game.incCow();
                        break;
                    }
                }

            }
        }
    }

    public static boolean isGameEnds(BullsAndCows game, int length) {
        return game.getBull() == length;
    }
    public static void messages (BullsAndCows game) {
        if (game.getBull() != 0 && game.getCow() != 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s)", game.getBull(),game.getCow());
        } else if (game.getBull() != 0) {
            System.out.printf("Grade: %d bull(s)", game.getBull());
        } else if (game.getCow() != 0) {
            System.out.printf("Grade: %d cow(s)", game.getCow());
        } else {
            System.out.print("Grade: none");
        }
    }


}

class BullsAndCows {
    private int bull = 0;
    private int cow = 0;

    public int getCow() {
        return cow;
    }

    public int getBull() {
        return bull;
    }

    public void setCow(int cow) {
        this.cow = cow;
    }

    public void setBull(int bull) {
        this.bull = bull;
    }

    public void incCow() {
        cow += 1;
    }

    public void incBull() {
        bull += 1;
    }


}

class Input {

    private int lengths;
    private int symbols;

    public int getLengths() {
        return lengths;
    }
    public int getSymbols() {
        return symbols;
    }
    public void setLengths(int lengths) {
        this.lengths = lengths;
    }
    public void setSymbols(int symbols) {
        this.symbols = symbols;
    }

}