import java.util.Random;

public class Board {
    private Letter[][] board;
    private int size;
    private static final String ALPHABET = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ";
    private static final int[] POINTS = {1, 8, 4, 4, 1, 8, 1, 8, 1, 2, 3, 3, 3, 10, 1, 2, 6, 1, 2, 1, 8, 10, 10, 3};

    public Board(int size) {
        this.size = size;
        board = new Letter[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int index = random.nextInt(ALPHABET.length());
                char symbol = ALPHABET.charAt(index);
                int points = POINTS[index];
                String color = getRandomColor(random);
                boolean isWildCard = random.nextInt(10) < 2; // 20% πιθανότητα να είναι μπαλαντέρ
                board[i][j] = new Letter(symbol, points, color, isWildCard);
            }
        }
    }

    private String getRandomColor(Random random) {
        int chance = random.nextInt(10);
        if (chance < 2) return "red";  // Κόκκινα
        else if (chance < 5) return "blue";  // Μπλε
        return "white";  // Κανονικά
    }

    public Letter getLetterAt(int row, int col) {
        return board[row][col];
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j].getSymbol() + " ");
            }
            System.out.println();
        }
    }

    public int getSize() {
        return size;
    }

    public void removeLetters(ArrayList<JButton> selectedButtons) {
        for (JButton button : selectedButtons) {
            // Εδώ θα ανανεώσουμε το ταμπλό με τυχαία γράμματα
            button.setText("?"); // Placeholder για μπαλαντέρ
        }
    }
}
