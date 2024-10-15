import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class WordGameGUI {
    private JFrame frame;
    private JPanel boardPanel;
    private JLabel scoreLabel;
    private JLabel messageLabel;
    private JButton[][] letterButtons;
    private Game game;
    private ArrayList<JButton> selectedButtons = new ArrayList<>();
    private ArrayList<Letter> selectedLetters = new ArrayList<>();

    public WordGameGUI(Game game) {
        this.game = game;
        frame = new JFrame("Βρες τη Λέξη");
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(game.getBoard().getSize(), game.getBoard().getSize()));
        letterButtons = new JButton[game.getBoard().getSize()][game.getBoard().getSize()];

        initializeBoard();

        scoreLabel = new JLabel("Συνολική Βαθμολογία: " + game.getScore());
        frame.add(scoreLabel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);

        messageLabel = new JLabel("Επιλέξτε γράμματα για να σχηματίσετε λέξεις.");
        frame.add(messageLabel, BorderLayout.SOUTH);

        // Προσθέτουμε δεξί κλικ για ακύρωση
        boardPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    resetSelection();
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initializeBoard() {
        for (int i = 0; i < game.getBoard().getSize(); i++) {
            for (int j = 0; j < game.getBoard().getSize(); j++) {
                Letter letter = game.getBoard().getLetterAt(i, j);
                JButton button = new JButton(String.valueOf(letter.getSymbol()));
                button.addActionListener(new LetterButtonListener(i, j, letter));
                letterButtons[i][j] = button;
                boardPanel.add(button);
            }
        }
    }

    private class LetterButtonListener implements ActionListener {
        private int row, col;
        private Letter letter;

        public LetterButtonListener(int row, int col, Letter letter) {
            this.row = row;
            this.col = col;
            this.letter = letter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource();
            try {
                if (!selectedButtons.contains(buttonClicked)) {
                    if (selectedButtons.isEmpty() || checkNeighboring(row, col)) {
                        selectedButtons.add(buttonClicked);
                        selectedLetters.add(letter);
                        buttonClicked.setBackground(Color.YELLOW); // Αλλαγή χρώματος
                        messageLabel.setText("Επιλέξατε το γράμμα: " + letter.getSymbol());
                    } else {
                        throw new InvalidNeighboringException("Τα γράμματα δεν είναι γειτονικά!");
                    }
                } else {
                    if (buttonClicked == selectedButtons.get(selectedButtons.size() - 1)) {
                        selectedButtons.remove(buttonClicked);
                        selectedLetters.remove(letter);
                        buttonClicked.setBackground(null); // Επαναφορά χρώματος
                        messageLabel.setText("Ακυρώσατε την επιλογή του γράμματος: " + letter.getSymbol());
                    }
                }
            } catch (InvalidNeighboringException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Σφάλμα Γειτνίασης", JOptionPane.ERROR_MESSAGE);
            }
        }

        private boolean checkNeighboring(int currentRow, int currentCol) {
            if (selectedButtons.isEmpty()) return true;
            JButton lastButton = selectedButtons.get(selectedButtons.size() - 1);
            int lastRow = -1;
            int lastCol = -1;

            for (int i = 0; i < game.getBoard().getSize(); i++) {
                for (int j = 0; j < game.getBoard().getSize(); j++) {
                    if (letterButtons[i][j] == lastButton) {
                        lastRow = i;
                        lastCol = j;
                        break;
                    }
                }
            }

            return Math.abs(currentRow - lastRow) <= 1 && Math.abs(currentCol - lastCol) <= 1;
        }
    }

    private void resetSelection() {
        for (JButton button : selectedButtons) {
            button.setBackground(null); // Επαναφορά χρώματος
        }
        selectedButtons.clear();
        selectedLetters.clear();
        messageLabel.setText("Ακυρώσατε την επιλογή των γραμμάτων.");
    }

    public static void main(String[] args) {
        // Δοκιμαστικός κώδικας για το Main
        try {
            Set<String> dictionary = DictionaryLoader.loadDictionary("dictionary.txt");
            Game game = new Game(8, 50, dictionary);  // Ταμπλό 8x8, στόχος 50 πόντους
            new WordGameGUI(game);
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά τη φόρτωση του λεξικού: " + e.getMessage());
        }
    }
}
