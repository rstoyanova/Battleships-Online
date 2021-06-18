package game.battleships.gameengine;

import exceptions.game.battleships.board.BoardException;
import game.battleships.board.BattleshipsBoardField;
import exceptions.game.battleships.gameengine.NotPlayersTurn;
import exceptions.game.battleships.gameengine.PlayerNotInGame;
import exceptions.game.battleships.gameengine.PlayersSlotsAreTaken;
import org.junit.Before;
import org.junit.Test;
import game.battleships.ships.Battleship;
import game.battleships.ships.Carrier;
import game.battleships.ships.Destroyer;

import static org.junit.Assert.assertEquals;

public class ActiveBattleshipsGameTest {
    final static private String INDENT = "\t\t\t\t\t";
    final static private String YOUR_BOARD_BANNER ="        YOUR BOARD";
    final static private String ENEMY_BOARD_BANNER = "       ENEMY BOARD";
    final static private String NUMBERS = "   1 2 3 4 5 6 7 8 9 10";
    final static private String TOP_OF_BOARD = "   _ _ _ _ _ _ _ _ _ _";
    final static private String NL = System.lineSeparator();

    // valid for carrier
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_1 = new BattleshipsBoardField('E', 3);
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_2 = new BattleshipsBoardField('E', 7);
    // valid for battleship
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_3 = new BattleshipsBoardField('E', 1);
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_4 = new BattleshipsBoardField('H', 1);
    //valid for destroyer
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_5 = new BattleshipsBoardField('B', 10);
    final static private BattleshipsBoardField TEST_BATTLESHIPS_BOARD_FIELD_6 = new BattleshipsBoardField('C', 10);

    final static private String EMPTY_BOARD =
            INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
            INDENT + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
            INDENT + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
            INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
            INDENT + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
            INDENT + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
            INDENT + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
            INDENT + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
            INDENT + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
            INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL;

    final static private String LEGEND = "  Legend:" + NL +
            "  * - ship field" + NL +
            "  X - hit ship field" + NL +
            "  O - hit empty field" + NL + NL;

    final static private String USERNAME_1 = "reni";
    final static private String USERNAME_2 = "bobi";


    private ActiveBattleshipsGame testGame;

    @Before
    public void setUp() {
        testGame = new ActiveBattleshipsGame(USERNAME_1);
        try {
            testGame.setSecondPlayer(USERNAME_2);
        } catch (PlayersSlotsAreTaken e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testToStringWithNewGameTwoPlayers() {
        final String EXPECTED = NL +
                INDENT + YOUR_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                EMPTY_BOARD +
                NL + NL +
                INDENT + ENEMY_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                EMPTY_BOARD +
                NL + LEGEND;
        String actual = testGame.toString(USERNAME_1);
        assertEquals(EXPECTED, actual);
    }

    @Test
    public void testToStringWithPlaceValidShipsOnValidFields() {
        try {
            testGame.placeShip(USERNAME_1, new Carrier(),
                    TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
            testGame.placeShip(USERNAME_1, new Battleship(),
                    TEST_BATTLESHIPS_BOARD_FIELD_3, TEST_BATTLESHIPS_BOARD_FIELD_4);
            testGame.placeShip(USERNAME_2, new Destroyer(),
                    TEST_BATTLESHIPS_BOARD_FIELD_5, TEST_BATTLESHIPS_BOARD_FIELD_6);
        } catch (BoardException | PlayerNotInGame e) {
            System.out.println("Something else went wrong! testToStringWithPlaceValidShipsOnValidFields");
        }
        final String EXPECTED = NL +
                INDENT + YOUR_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "E |*|_|*|*|*|*|*|_|_|_|" + NL +
                INDENT + "F |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "G |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "H |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL +
                NL + NL +
                INDENT + ENEMY_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                EMPTY_BOARD +
                NL + LEGEND;

        String actual = testGame.toString(USERNAME_1);
        assertEquals(EXPECTED,actual);
    }

    @Test
    public void testAttackEnemyHit() {
        final BattleshipsBoardField SHIP_FIELD_BOARD_USER_1 = new BattleshipsBoardField('E', 5);
        try {
            testGame.placeShip(USERNAME_1, new Carrier(),
                    TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
            testGame.placeShip(USERNAME_1, new Battleship(),
                    TEST_BATTLESHIPS_BOARD_FIELD_3, TEST_BATTLESHIPS_BOARD_FIELD_4);
            testGame.placeShip(USERNAME_2, new Destroyer(),
                    TEST_BATTLESHIPS_BOARD_FIELD_5, TEST_BATTLESHIPS_BOARD_FIELD_6);

            testGame.attack(USERNAME_2, SHIP_FIELD_BOARD_USER_1);
        } catch (BoardException | PlayerNotInGame | NotPlayersTurn e) {
            System.out.println("Something else went wrong! testToStringWithPlaceValidShipsOnValidFields");
        }
        final String EXPECTED = NL +
                INDENT + YOUR_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "E |*|_|*|*|X|*|*|_|_|_|" + NL +
                INDENT + "F |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "G |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "H |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL +
                NL + NL +
                INDENT + ENEMY_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL +
                NL + LEGEND ;

        String actual = testGame.toString(USERNAME_1);
        assertEquals(EXPECTED,actual);
    }

    @Test
    public void testAttackEnemyMiss() {
        final BattleshipsBoardField EMPTY_FIELD_BOARD_USER_1 = new BattleshipsBoardField('B', 7);
        try {
            testGame.placeShip(USERNAME_1, new Carrier(),
                    TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
            testGame.placeShip(USERNAME_1, new Battleship(),
                    TEST_BATTLESHIPS_BOARD_FIELD_3, TEST_BATTLESHIPS_BOARD_FIELD_4);
            testGame.placeShip(USERNAME_2, new Destroyer(),
                    TEST_BATTLESHIPS_BOARD_FIELD_5, TEST_BATTLESHIPS_BOARD_FIELD_6);

            testGame.attack(USERNAME_2, EMPTY_FIELD_BOARD_USER_1);
        } catch (BoardException | PlayerNotInGame | NotPlayersTurn e) {
            System.out.println("Something else went wrong! testToStringWithPlaceValidShipsOnValidFields");
        }
        final String EXPECTED = NL +
                INDENT + YOUR_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "B |_|_|_|_|_|_|O|_|_|_|" + NL +
                INDENT + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "E |*|_|*|*|*|*|*|_|_|_|" + NL +
                INDENT + "F |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "G |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "H |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL +
                NL + NL +
                INDENT + ENEMY_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL +
                NL + LEGEND ;

        String actual = testGame.toString(USERNAME_1);
        assertEquals(EXPECTED,actual);

    }

    @Test
    public void testAttackYouHit() {
        final BattleshipsBoardField SHIP_FIELD_BOARD_USER_2 = new BattleshipsBoardField('C', 10);
        try {
            testGame.placeShip(USERNAME_1, new Carrier(),
                    TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
            testGame.placeShip(USERNAME_1, new Battleship(),
                    TEST_BATTLESHIPS_BOARD_FIELD_3, TEST_BATTLESHIPS_BOARD_FIELD_4);
            testGame.placeShip(USERNAME_2, new Destroyer(),
                    TEST_BATTLESHIPS_BOARD_FIELD_5, TEST_BATTLESHIPS_BOARD_FIELD_6);

            testGame.attack(USERNAME_1, SHIP_FIELD_BOARD_USER_2);
        } catch (BoardException | PlayerNotInGame | NotPlayersTurn e) {
            System.out.println("Something else went wrong! testToStringWithPlaceValidShipsOnValidFields");
        }
        final String EXPECTED = NL +
                INDENT + YOUR_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "E |*|_|*|*|*|*|*|_|_|_|" + NL +
                INDENT + "F |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "G |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "H |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL +
                NL + NL +
                INDENT + ENEMY_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "C |_|_|_|_|_|_|_|_|_|X|" + NL +
                INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL +
                NL + LEGEND;

        String actual = testGame.toString(USERNAME_1);
        assertEquals(EXPECTED,actual);
    }

    @Test
    public void testAttackYouMiss() {
        final BattleshipsBoardField EMPTY_FIELD_BOARD_USER_2 = new BattleshipsBoardField('I', 9);
        try {
            testGame.placeShip(USERNAME_1, new Carrier(),
                    TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
            testGame.placeShip(USERNAME_1, new Battleship(),
                    TEST_BATTLESHIPS_BOARD_FIELD_3, TEST_BATTLESHIPS_BOARD_FIELD_4);
            testGame.placeShip(USERNAME_2, new Destroyer(),
                    TEST_BATTLESHIPS_BOARD_FIELD_5, TEST_BATTLESHIPS_BOARD_FIELD_6);

            testGame.attack(USERNAME_1, EMPTY_FIELD_BOARD_USER_2);
        } catch (BoardException | PlayerNotInGame | NotPlayersTurn e) {
            System.out.println("Something else went wrong! testToStringWithPlaceValidShipsOnValidFields");
        }
        final String EXPECTED = NL +
                INDENT + YOUR_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "E |*|_|*|*|*|*|*|_|_|_|" + NL +
                INDENT + "F |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "G |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "H |*|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "I |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL +
                NL + NL +
                INDENT + ENEMY_BOARD_BANNER + NL +
                INDENT + NUMBERS + NL +
                INDENT + TOP_OF_BOARD + NL +
                INDENT + "A |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "B |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "C |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "D |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "E |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "F |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "G |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "H |_|_|_|_|_|_|_|_|_|_|" + NL +
                INDENT + "I |_|_|_|_|_|_|_|_|O|_|" + NL +
                INDENT + "J |_|_|_|_|_|_|_|_|_|_|" + NL +
                NL + LEGEND;

        String actual = testGame.toString(USERNAME_1);
        assertEquals(EXPECTED,actual);

    }

    @Test (expected = PlayerNotInGame.class)
    public void testAttackWithUserNotInTheGame() throws PlayerNotInGame {
        final String NEW_USERNAME = "gosho";
        try {
            testGame.attack(NEW_USERNAME, TEST_BATTLESHIPS_BOARD_FIELD_1);
        } catch (BoardException | NotPlayersTurn e) {
            System.out.println("Not valid field!");
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAttackWithNullArguments() {
        try{
            testGame.attack(USERNAME_1, null);
        } catch (BoardException | PlayerNotInGame | NotPlayersTurn e) {
            System.out.println("Something else went wrong! testAttackWithNullArguments");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetSecondPlayerWithNullArgument() {
        try {
            testGame.setSecondPlayer(null);
        } catch (PlayersSlotsAreTaken e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = PlayerNotInGame.class)
    public void testPlaceShipWithUserNotInTheGame() throws PlayerNotInGame{
        final String NEW_USERNAME = "gosho";
        try {
            testGame.placeShip(NEW_USERNAME, new Carrier(), TEST_BATTLESHIPS_BOARD_FIELD_1, TEST_BATTLESHIPS_BOARD_FIELD_2);
        } catch (BoardException e) {
            System.out.println("Something else went wrong! testPlaceShipFromUserNotInTheGame");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlaceShipWithNullArgument() {
        try {
            testGame.placeShip("", null, null, null);
        } catch (BoardException | PlayerNotInGame e) {
            System.out.println("Something else went wrong! testPlaceShipWithNullArgument");
        }
    }

}
