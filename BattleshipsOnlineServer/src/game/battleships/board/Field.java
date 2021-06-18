package game.battleships.board;

public interface Field {
    boolean isValidField();

    char getRow();

    int getCol();

    String toString();
}
