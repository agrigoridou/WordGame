import java.util.ArrayList;
import java.util.Set;

public class Game {
    private Board board;
    private int score;
    private int targetScore;
    private Set<String> dictionary;

    public Game(int boardSize, int targetScore, Set<String> dictionary) {
        this.board = new Board(boardSize);
        this.score = 0;
        this.targetScore = targetScore;
        this.dictionary = dictionary;
    }

    public boolean isValidWord(String word) {
        return dictionary.contains(word.toUpperCase());
    }

    public void addScore(int wordScore) {
        score += wordScore;
    }

    public int calculateScore(ArrayList<Letter> selectedLetters) {
        int totalScore = 0;
        boolean hasBlueLetter = false;

        for (Letter letter : selectedLetters) {
            totalScore += letter.getPoints();
            if (letter.getColor().equals("blue")) {
                hasBlueLetter = true;
            }
        }

        if (hasBlueLetter) {
            totalScore *= 2; // Διπλασιάζουμε τη βαθμολογία
        }
        return totalScore;
    }

    public void processWord(ArrayList<Letter> selectedLetters) {
        StringBuilder word = new StringBuilder();
        for (Letter letter : selectedLetters) {
            word.append(letter.getSymbol());
        }

        if (isValidWord(word.toString())) {
            int wordScore = calculateScore(selectedLetters);
            addScore(wordScore);
            System.out.println("Συγχαρητήρια, βρήκατε την λέξη " + word + ". Πόντοι: " + wordScore);
        } else {
            System.out.println("Η λέξη " + word + " δεν είναι έγκυρη.");
        }
    }

    public int getScore() {
        return score;
    }

    public boolean hasWon() {
        return score >= targetScore;
    }

    public Board getBoard() {
        return board;
    }
}
