package game.battleships.board;

import exceptions.game.battleships.board.*;
import org.junit.Before;
import org.junit.Test;
import game.battleships.ships.Destroyer;
import game.battleships.ships.Submarine;

import static org.junit.Assert.assertEquals;

public class BattleshipsBoardTest {
    final static private String YOUR_BOARD_BANNER = "        YOUR BOARD";
    final static private String ENEMY_BOARD_BANNER = "       ENEMY BOARD";
    final static private String NUMBERS = "   1 2 3 4 5 6 7 8 9 10";
    final static private String TOP_OF_BOARD = "   _ _ _ _ _ _ _ _ _ _";
    final static private String NL = System.lineSeparator();
    final static private char TAB = '\t';


    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_1 = new BattleshipsBoardField('B', 3);
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_2 = new BattleshipsBoardField('B', 4);
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_3 = new BattleshipsBoardField('D', 6);
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_4 = new BattleshipsBoardField('F', 6);
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_5 = new BattleshipsBoardField('D', 3);


    private BattleshipsBoard testBoard;

    @Before
    public void setUp() {
        testBoard = new BattleshipsBoard();
    }

    @Test
    public void testToPrintAbleEmptyYourBoard() {
        final String EXPECTED = TAB + YOUR_BOARD_BANNER + NL +
                TAB + NUMBERS + NL +
                TAB + TOP_OF_BOARD + NL +
                TAB + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "J |_|_|_|_|_|_|_|_|_|_|" + NL;
        String actual = testBoard.toString(false);

        assertEquals("Boards must be equal!", EXPECTED, actual);
    }


    @Test
    public void testToPrinTABleEmptyEnemyBoard() {
        final String EXPECTED = TAB + ENEMY_BOARD_BANNER + NL +
                TAB + NUMBERS + NL +
                TAB + TOP_OF_BOARD + NL +
                TAB + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "J |_|_|_|_|_|_|_|_|_|_|" + NL;
        String actual = testBoard.toString(true);

        assertEquals("Boards must be equal!", EXPECTED, actual);
    }

    @Test
    public void testToPrinTABleYourBoardWithShipsHitsAndMisses() {
        final String EXPECTED = TAB + YOUR_BOARD_BANNER + NL +
                TAB + NUMBERS + NL +
                TAB + TOP_OF_BOARD + NL +
                TAB + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "B |_|_|*|*|_|_|_|_|_|_|" + NL +
                TAB + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "D |_|_|O|_|_|X|_|_|_|_|" + NL +
                TAB + "E |_|_|_|_|_|*|_|_|_|_|" + NL +
                TAB + "F |_|_|_|_|_|*|_|_|_|_|" + NL +
                TAB + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "J |_|_|_|_|_|_|_|_|_|_|" + NL;
        try {
            testBoard.placeShip(new Destroyer(), TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
            testBoard.placeShip(new Submarine(), TEST_BATTLESHIPS_BOARD_FIELD_3, TEST_BATTLESHIPS_BOARD_FIELD_4);
            //hit
            testBoard.attack(TEST_BATTLESHIPS_BOARD_FIELD_3);
            //miss
            testBoard.attack(TEST_BATTLESHIPS_BOARD_FIELD_5);
        } catch (BoardException e) {
            System.out.println("PlaceShip error!");
        }

        String actual = testBoard.toString(false);

        assertEquals("Boards must be equal!", EXPECTED, actual);
    }

    @Test
    public void testToStringEnemyBoardWithShipsHitsAndMisses() {
        final String EXPECTED = TAB + ENEMY_BOARD_BANNER + NL +
                TAB + NUMBERS + NL +
                TAB + TOP_OF_BOARD + NL +
                TAB + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "B |_|_|X|_|_|_|_|_|_|_|" + NL +
                TAB + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "D |_|_|O|_|_|_|_|_|_|_|" + NL +
                TAB + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "J |_|_|_|_|_|_|_|_|_|_|" + NL;
        try {
            testBoard.placeShip(new Destroyer(), TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
            testBoard.placeShip(new Submarine(), TEST_BATTLESHIPS_BOARD_FIELD_3, TEST_BATTLESHIPS_BOARD_FIELD_4);
            //hit
            testBoard.attack(TEST_BATTLESHIPS_BOARD_FIELD_1);
            //miss
            testBoard.attack(TEST_BATTLESHIPS_BOARD_FIELD_5);
        } catch (BoardException e) {
            System.out.println("PlaceShip error!");
        }

        String actual = testBoard.toString(true);

        assertEquals("Boards must be equal!", EXPECTED, actual);
    }

    @Test
    public void testPlaceAvailableShipOnEmptyFieldsOnAColumn() {
        try {
            testBoard.placeShip(new Submarine(), TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_5);
        } catch (Exception e) {
            System.out.println("PlaceShip error!");
        }

        final String EXPECTED = TAB + YOUR_BOARD_BANNER + NL +
                TAB + NUMBERS + NL +
                TAB + TOP_OF_BOARD + NL +
                TAB + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "B |_|_|*|_|_|_|_|_|_|_|" + NL +
                TAB + "C |_|_|*|_|_|_|_|_|_|_|" + NL +
                TAB + "D |_|_|*|_|_|_|_|_|_|_|" + NL +
                TAB + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "J |_|_|_|_|_|_|_|_|_|_|" + NL;
        String actual = testBoard.toString(false);

        assertEquals("Boards must be equal!", EXPECTED, actual);
    }

    @Test
    public void testPlaceAvailableShipOnEmptyFieldsOnARow() {
        try {
            testBoard.placeShip(new Destroyer(), TEST_BATTLESHIPS_BOARD_FIELD_2, TEST_BATTLESHIPS_BOARD_FIELD_1);
        } catch (BoardException e) {
            System.out.println("PlaceShip error!");
        }

        final String EXPECTED = TAB + YOUR_BOARD_BANNER + NL +
                TAB + NUMBERS + NL +
                TAB + TOP_OF_BOARD + NL +
                TAB + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "B |_|_|*|*|_|_|_|_|_|_|" + NL +
                TAB + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                TAB + "J |_|_|_|_|_|_|_|_|_|_|" + NL;
        String actual = testBoard.toString(false);

        assertEquals("Boards must be equal!", EXPECTED, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlaceShipWithNullArguments() {
        try {
            testBoard.placeShip(null, null, null);
        } catch (BoardException e) {
            System.out.println("Something else went wrong! testPlaceShipWithNullArguments");
        }

    }

    @Test(expected = FieldsAlreadyOccupied.class)
    public void testPlaceShipOnAlreadyOccupiedFields() throws FieldsAlreadyOccupied {
        try {
            testBoard.placeShip(new Destroyer(), TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
            testBoard.placeShip(new Destroyer(), TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
        } catch (NoAvailableShipsOfType | NotAppropriateFieldsForShip
                | FieldsNotInLine | NotValidField
                | AllShipsAlreadyPlaced e) {
            System.out.println("Something else went wrong! testPlaceShipOnAlreadyOccupiedFields");
        }
    }

    @Test(expected = FieldsNotInLine.class)
    public void testPlaceShipWithFieldsNotInLine() throws FieldsNotInLine {
        try {
            testBoard.placeShip(new Submarine(), TEST_BATTLESHIPS_BOARD_FIELD_2, TEST_BATTLESHIPS_BOARD_FIELD_3);
        } catch (NoAvailableShipsOfType | NotAppropriateFieldsForShip
                | FieldsAlreadyOccupied | NotValidField
                | AllShipsAlreadyPlaced e) {
            System.out.println("Something else went wrong! testPlaceShipWithFieldsNotInLine");
        }
    }

    @Test(expected = NotAppropriateFieldsForShip.class)
    public void testPlaceShipWithNotAppropriateFieldsForShip() throws NotAppropriateFieldsForShip {
        try {
            testBoard.placeShip(new Submarine(), TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
        } catch (NoAvailableShipsOfType | FieldsAlreadyOccupied
                | FieldsNotInLine | NotValidField
                | AllShipsAlreadyPlaced e) {
            System.out.println("Something else went wrong! testPlaceShipWithNotAppropriateFieldsForShip");
        }
    }

    @Test(expected = NoAvailableShipsOfType.class)
    public void testPlaceShipWhenNoAvailableShipsOfKind() throws NoAvailableShipsOfType {
        final BattleshipsBoardField testBattleshipsBoardField_4 = new BattleshipsBoardField('C', 3);
        final BattleshipsBoardField testBattleshipsBoardField_5 = new BattleshipsBoardField('C', 4);
        final BattleshipsBoardField testBattleshipsBoardField_6 = new BattleshipsBoardField('E', 6);
        final BattleshipsBoardField testBattleshipsBoardField_7 = new BattleshipsBoardField('H', 6);
        try {
            testBoard.placeShip(new Destroyer(), TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
            testBoard.placeShip(new Destroyer(), testBattleshipsBoardField_4, testBattleshipsBoardField_5);
        } catch (BoardException e) {
            System.out.println("Something else went wrong! testPlaceShipWithNotAppropriateFieldsForShip");
        }
        try {
            testBoard.placeShip(new Destroyer(), testBattleshipsBoardField_6, testBattleshipsBoardField_7);
        } catch (FieldsAlreadyOccupied | NotAppropriateFieldsForShip
                | FieldsNotInLine | NotValidField
                | AllShipsAlreadyPlaced e) {
            System.out.println("Something else went wrong! testPlaceShipWithNotAppropriateFieldsForShip");
        }
    }
}
