public class Letter {
    private char symbol;
    private int points;
    private String color; // "red", "blue", "white"
    private boolean isWildCard; // Για μπαλαντέρ

    public Letter(char symbol, int points, String color, boolean isWildCard) {
        this.symbol = symbol;
        this.points = points;
        this.color = color;
        this.isWildCard = isWildCard;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPoints() {
        return isWildCard ? 0 : (color.equals("red") ? points * 2 : points);
    }

    public String getColor() {
        return color;
    }

    public boolean isWildCard() {
        return isWildCard;
    }
}
