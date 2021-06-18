package game.battleships.board;

import exceptions.game.battleships.board.FieldsNotInLine;

public class BattleshipsBoardField implements Field {
    private final char row;
    private final int col;

    final static private String NL = System.lineSeparator();

    public BattleshipsBoardField(char row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isValidField() {
        final char FIRST_ROW = 'A';
        final char LAST_ROW = 'J';
        final int FIRST_COL = 1;
        final int LAST_COL = 10;

        return Character.toUpperCase(row) >= FIRST_ROW
                && Character.toUpperCase(row) <= LAST_ROW
                && col >= FIRST_COL
                && col <= LAST_COL;
    }

    public static boolean onTheSameRow(BattleshipsBoardField lhs, BattleshipsBoardField rhs) {
        if (lhs == null || rhs == null) {
            throw new IllegalArgumentException("Fields must not be null!" + NL);
        }
        char lhsRow = Character.toLowerCase(lhs.row);
        char rhsRow = Character.toLowerCase(rhs.row);

        return lhsRow == rhsRow;
    }

    public static boolean onTheSameCol(BattleshipsBoardField lhs, BattleshipsBoardField rhs) {
        if (lhs == null || rhs == null) {
            throw new IllegalArgumentException("Fields must not be null!" + NL);
        }
        return lhs.col == rhs.col;
    }

    public static int distanceBetween(BattleshipsBoardField lhs, BattleshipsBoardField rhs) throws FieldsNotInLine {
        if (lhs == null || rhs == null) {
            throw new IllegalArgumentException("Fields must not be null!" + NL);
        }

        if (onTheSameCol(lhs, rhs) && onTheSameRow(lhs, rhs)) {
            return 0;
        } else if (onTheSameRow(lhs, rhs)) {
            return lhs.col > rhs.col ? lhs.col - rhs.col + 1 : rhs.col - lhs.col + 1;
        } else if (onTheSameCol(lhs, rhs)) {
            int cntr = 1;
            char ptr = lhs.row;
            if (lhs.row < rhs.row) {
                while (ptr != rhs.row) {
                    ptr++;
                    cntr++;
                }
            }
            return cntr;
        } else {
            throw new FieldsNotInLine("Fields must be on the same row or column!" + NL);
        }
    }

    public static boolean compareOnSameLine(BattleshipsBoardField lhs, BattleshipsBoardField rhs) {
        if (onTheSameCol(lhs, rhs)) {
            return lhs.row < rhs.row;
        } else {
            return lhs.col < rhs.col;
        }
    }

    public char getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return row + Integer.toString(col);
    }

}
