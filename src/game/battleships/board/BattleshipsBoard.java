package game.battleships.board;

import exceptions.game.battleships.board.*;
import game.battleships.ships.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BattleshipsBoard implements Grid, Serializable {

    private final List<List<Character>> board;
    private final Map<String, Integer> ships;
    private int attackedFields;

    private static final int ROW_COL_LEN = 10;
    private static final String NL = System.lineSeparator();

    private static final char SHIP_FIELD = '*';
    private static final char HIT_FIELD = 'X';
    private static final char MISS_FIELD = 'O';
    private static final char EMPTY_FIELD = '_';
    private static final char COLUMN_SEPARATOR = '|';
    private static final char TAB = '\t';
    private static final char SPACE = ' ';

    private static final String YOUR_BOARD_BANNER = "        YOUR BOARD";
    private static final String ENEMY_BOARD_BANNER = "       ENEMY BOARD";
    private static final String NUMBERS = "   1 2 3 4 5 6 7 8 9 10";
    private static final String TOP_OF_BOARD = "   _ _ _ _ _ _ _ _ _ _";


    public BattleshipsBoard() {
        board = new ArrayList<>(ROW_COL_LEN);
        ships = new HashMap<>();
        attackedFields = 0;
        fillBoard();
        fillShips();
    }

    @Override
    public void print(boolean enemy) {
        System.out.print(toString(enemy));
    }

    @Override
    public String toString(boolean enemy) {
        StringBuilder output = getHeader(enemy);
        for (int i = 0; i < ROW_COL_LEN; i++) {
            output.append(TAB)
                    .append(rowLabel(i))
                    .append(SPACE);
            for (Character f : board.get(i)) {
                output.append(COLUMN_SEPARATOR)
                        .append(getField(f, enemy));
            }
            output.append(COLUMN_SEPARATOR).append(NL);
        }
        return output.toString();
    }

    public void placeShip(Ship ship, BattleshipsBoardField startPoint, BattleshipsBoardField endPoint)
            throws NoAvailableShipsOfType,
            NotAppropriateFieldsForShip,
            FieldsNotInLine,
            FieldsAlreadyOccupied,
            NotValidField,
            AllShipsAlreadyPlaced {

        if (ship == null || startPoint == null || endPoint == null) {
            throw new IllegalArgumentException("Arguments must not be null!" + NL);
        }
        if (!startPoint.isValidField()) {
            throw new NotValidField(startPoint);
        }
        if (!endPoint.isValidField()) {
            throw new NotValidField(endPoint);
        }
        if (allShipsPlaced()) {
            throw new AllShipsAlreadyPlaced();
        }
        // if all of the ships of this type are already placed
        if (noAvailableShipsOfType(ship)) {
            throw new NoAvailableShipsOfType(ship);
        }

        if (BattleshipsBoardField.distanceBetween(startPoint, endPoint) == ship.getLength()) {
            placeShip(startPoint, endPoint);
            ships.put(ship.toString(), (ships.get(ship.toString()) - 1));
        } else {
            throw new NotAppropriateFieldsForShip();
        }
    }

    public void attack(BattleshipsBoardField field)
            throws NotValidField, GameOver,
            FieldAlreadyAttacked, NotAllShipsPlaced {

        if (field == null) {
            throw new IllegalArgumentException();
        }
        if (!field.isValidField()) {
            throw new NotValidField(field);
        }
        if (allShipsPlaced()) {
            throw new NotAllShipsPlaced();
        }
        if (isAttackedField(field)) {
            throw new FieldAlreadyAttacked();
        } else if (isShipField(field)) {
            attackedFields++;
            setHitField(field);
        } else {
            setMissField(field);
        }

        if (gameOver()) {
            throw new GameOver();
        }
    }

    public boolean allShipsPlaced() {
        for (Map.Entry<String, Integer> shipData : ships.entrySet()) {
            if (shipData.getValue() != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean gameOver() {
        final int TOTAL_NUM_OF_POSSIBLE_ATTACKED_FIELDS = 30;
        return attackedFields == TOTAL_NUM_OF_POSSIBLE_ATTACKED_FIELDS;
    }

    private StringBuilder getHeader(boolean enemy) {
        StringBuilder output = new StringBuilder().append(TAB);

        if (enemy) {
            output.append(ENEMY_BOARD_BANNER);
        } else {
            output.append(YOUR_BOARD_BANNER);
        }
        String HEADER = NL + TAB + NUMBERS + NL + TAB + TOP_OF_BOARD + NL;
        output.append(HEADER);
        return output;
    }

    private char getField(char fieldData, boolean enemy) {
        if (fieldData == SHIP_FIELD && enemy) {
            return EMPTY_FIELD;
        } else {
            return fieldData;
        }
    }

    private void placeShip(BattleshipsBoardField startPoint, BattleshipsBoardField endPoint)
            throws FieldsAlreadyOccupied,
            FieldsNotInLine {

        if (!BattleshipsBoardField.compareOnSameLine(startPoint, endPoint)) {
            BattleshipsBoardField tmp = startPoint;
            startPoint = endPoint;
            endPoint = tmp;
        }
        char row = startPoint.getRow();
        int col = startPoint.getCol();

        if (BattleshipsBoardField.onTheSameCol(startPoint, endPoint)) {
            while (row <= endPoint.getRow()) {
                if (isShipField(new BattleshipsBoardField(row, col))) {
                    throw new FieldsAlreadyOccupied();
                }
            }
            while (row <= endPoint.getRow()) {
                setShipField(new BattleshipsBoardField(row, col));
                row++;
            }
        } else if (BattleshipsBoardField.onTheSameRow(startPoint, endPoint)) {
            while (col <= endPoint.getCol()) {
                if (isShipField(new BattleshipsBoardField(row, col))) {
                    throw new FieldsAlreadyOccupied();
                }
            }
            while (col <= endPoint.getCol()) {
                setShipField(new BattleshipsBoardField(row, col));
                col++;
            }
        } else {
            throw new FieldsNotInLine("Field arguments in placeShip must be in a line!");
        }
    }

    private boolean noAvailableShipsOfType(Ship ship) {
        return ships.get(ship.toString()) <= 0;
    }

    private boolean isShipField(BattleshipsBoardField field) {
        int col = field.getCol() - 1;
        return board.get(rowNumber(field.getRow())).get(col) == SHIP_FIELD;
    }

    private boolean isAttackedField(BattleshipsBoardField field) {
        int col = field.getCol() - 1;
        return board.get(rowNumber(field.getRow())).get(col) == HIT_FIELD;
    }

    private void setShipField(BattleshipsBoardField field) {
        int col = field.getCol() - 1;
        board.get(rowNumber(field.getRow())).set(col, SHIP_FIELD);
    }

    private void setHitField(BattleshipsBoardField field) {
        int col = field.getCol() - 1;
        board.get(rowNumber(field.getRow())).set(col, HIT_FIELD);
    }

    private void setMissField(BattleshipsBoardField field) {
        int col = field.getCol() - 1;
        board.get(rowNumber(field.getRow())).set(col, MISS_FIELD);
    }

    private char rowLabel(int rowNum) {
        return (char) ('A' + rowNum);
    }

    private int rowNumber(char rowCh) {
        return Character.toLowerCase(rowCh) - 'a';
    }

    private void fillBoard() {
        for (int i = 0; i < ROW_COL_LEN; i++) {
            board.add(new ArrayList<>(ROW_COL_LEN));
            for (int j = 0; j < ROW_COL_LEN; j++) {
                board.get(i).add(EMPTY_FIELD);
            }
        }
    }

    private void fillShips() {
        ships.put(new Destroyer().toString(), Destroyer.defaultCount());
        ships.put(new Submarine().toString(), Submarine.defaultCount());
        ships.put(new Battleship().toString(), Battleship.defaultCount());
        ships.put(new Carrier().toString(), Carrier.defaultCount());
    }
}
