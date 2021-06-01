package commandcenter;

import commandcenter.command.JoinGame;
import commandcenter.command.LoadGame;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.data.ServerStorage;

import static org.junit.Assert.assertEquals;

public class ControllerTest {
    private static final String CREATE_GAME = "create-game";
    private static final String DELETE_GAME = "delete-game";
    private static final String JOIN_GAME = "join-game";
    private static final String LOAD_GAMES = "load-game";
    private static final String SAVED_GAMES = "saved-games";

    private static final String USERNAME_1 = "reni_s";
    private static final String USERNAME_2 = "bobi_98";
    private static final String USERNAME_3 = "pesho";
    private static final String GAME_NAME_1 = "game1";
    private static final String GAME_NAME_2 = "game2";
    private static final String GAME_NAME_3 = "game3";
    private static final String EXTRA_ARG = "alabala";

    private static final String SPACE = " ";

    static Controller controller;

    @BeforeClass
    public static void startingSetUp() {
        controller = new Controller();
    }

    @Before
    public void setUpBeforeEach() {
        ServerStorage storage = ServerStorage.getStorage();
    }

    //
    // ---------- TESTS FOR EXECUTE COMMAND WITH [CREATE-GAME] ----------
    //

    @Test
    public void testExCreateGameWithTooFewArgOutput() {
        final String MESSAGE = "Wrong counting of arguments!";
        final String EXPECTED_OUTPUT = "Command create-game takes one argument!";
        final String CMD = CREATE_GAME;
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExCreateGameWithTooManyArgOutput() {
        final String MESSAGE = "Wrong counting of arguments!";
        final String EXPECTED_OUTPUT = "Command create-game takes one argument!";
        final String CMD = CREATE_GAME + SPACE + GAME_NAME_1 + SPACE + EXTRA_ARG;
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExCreateGameWithAlreadyTakenGameNameOutput() {
        final String MESSAGE = "Wrong creating of games!";
        final String EXPECTED_OUTPUT = "Game name already taken! Try another one :)";
        final String CMD = CREATE_GAME + SPACE + GAME_NAME_1;

        // creating game with name GAME_NAME_1 for USERNAME_1
        controller.executeCommand(CMD,GAME_NAME_1);
        // trying to again create game with name GAME_NAME_1 for USERNAME_1
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExCreateGameWithValidInputOutput() {
        final String MESSAGE = "Wrong creating of games!";
        final String NEW_G_NAME = "newGame";
        final String EXPECTED_OUTPUT = "Created game " + NEW_G_NAME + ", players 1/2";
        final String CMD = CREATE_GAME + SPACE + NEW_G_NAME;

        // creating game with name GAME_NAME_1 for USERNAME_1
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    //
    // ---------- TESTS FOR EXECUTE COMMAND WITH [DELETE-GAME] ----------
    //
    @Test
    public void testExDeleteGameWithTooFewArgOutput() {
        final String MESSAGE = "Wrong counting of arguments!";
        final String EXPECTED_OUTPUT = "Command delete-game takes one argument!";
        final String CMD = DELETE_GAME;
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExDeleteGameWithTooManyArgOutput() {
        final String MESSAGE = "Wrong counting of arguments!";
        final String EXPECTED_OUTPUT = "Command delete-game takes one argument!";
        final String CMD = DELETE_GAME + SPACE + GAME_NAME_1 + SPACE + EXTRA_ARG;
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExDeleteGameWithGameNotInStorageOutput() {
        final String MESSAGE = "Wrong deleting game!";
        final String EXPECTED_OUTPUT = "There is no game with this name!";
        final String NEW_G_NAME = "newGame3";
        final String CMD = DELETE_GAME + SPACE + NEW_G_NAME;

        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExDeleteGameWithNoSuchGameForUserOutput() {
        final String MESSAGE = "Wrong deleting of games!";
        final String EXPECTED_OUTPUT = "There is no game with this name in your games!";
        final String CMD_DEL = DELETE_GAME + SPACE + GAME_NAME_1;
        final String CMD_CREAT_1 = CREATE_GAME + SPACE + GAME_NAME_1;
        final String CMD_CREAT_2 = CREATE_GAME + SPACE + GAME_NAME_2;


        // creating a game with name GAME_NAME_1 for USERNAME_1
        controller.executeCommand(CMD_CREAT_1, USERNAME_1);
        // creating a game with name GAME_NAME_2 for USERNAME_1,
        // just to get him into the system
        controller.executeCommand(CMD_CREAT_2, USERNAME_2);
        // trying to delete GAME_NAME_1 for USERNAME_2
        String actual = controller.executeCommand(CMD_DEL, USERNAME_2);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExDeleteGameWithValidInputOutput() {
        final String MESSAGE = "Wrong deleting of games!";
        final String EXPECTED_OUTPUT = "Game [" + GAME_NAME_1 + "] was successfully deleted ";
        final String CMD = CREATE_GAME + SPACE + GAME_NAME_1;
        final String CMD_DEL = DELETE_GAME + SPACE + GAME_NAME_1;

        // creating a game with name GAME_NAME_1 for USERNAME_1
        controller.executeCommand(CMD, USERNAME_1);
        // deleting GAME_NAME_1 for USERNAME_1
        String actual = controller.executeCommand(CMD_DEL, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }


    //
    // ---------- TESTS FOR EXECUTE COMMAND WITH [JOIN-GAME] ----------
    //
    /*
    @Test
    public void testExJoinGameWithTooFewArgOutput() {
        final String MESSAGE = "Wrong counting of arguments!";
        final String EXPECTED_OUTPUT = "Command join-game takes one argument!";
        final String CMD = JOIN_GAME;
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }*/

    @Test
    public void testExJoinGameWithTooManyArgOutput() {
        final String MESSAGE = "Wrong counting of arguments!";
        final String EXPECTED_OUTPUT = "Command join-game takes no more than one argument!";
        final String CMD = JOIN_GAME + SPACE + GAME_NAME_1 + SPACE + EXTRA_ARG + SPACE + EXTRA_ARG;
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExJoinGameWithGameNotInStorageOutput() {
        final String MESSAGE = "Wrong joining to games!";
        final String EXPECTED_OUTPUT = "There is no game with this name!";
        final String CMD = JOIN_GAME + SPACE + GAME_NAME_1;

        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExJoinGameWithAlreadyJoinedUserOutput() {
        final String MESSAGE = "Wrong joining to games!";
        final String EXPECTED_OUTPUT = "You already joined that game!";
        final String CMD_CREAT_1 = CREATE_GAME + SPACE + GAME_NAME_1;
        final String CMD_CREAT_2 = CREATE_GAME + SPACE + GAME_NAME_2;
        final String CMD_JOIN = JOIN_GAME + SPACE + GAME_NAME_2;

        // creating a game with name GAME_NAME_1 for USERNAME_1
        controller.executeCommand(CMD_CREAT_1, USERNAME_1);
        // creating a game with name GAME_NAME_2 for USERNAME_2
        controller.executeCommand(CMD_CREAT_2, USERNAME_2);

        // joining for a first time for USERNAME_1 in GAME_NAME_2
        controller.executeCommand(CMD_JOIN, USERNAME_1);
        // joining for a second time
        String actual = controller.executeCommand(CMD_JOIN, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExJoinGameWithNoFreeSpotsInGameOutput() {
        final String MESSAGE = "Wrong joining to games!";
        final String EXPECTED_OUTPUT = "There are no empty slots in this game!";
        final String CMD_CREAT_1 = CREATE_GAME + SPACE + GAME_NAME_1;
        final String CMD_CREAT_2 = CREATE_GAME + SPACE + GAME_NAME_2;
        final String CMD_CREAT_3 = CREATE_GAME + SPACE + GAME_NAME_3;
        final String CMD_JOIN = JOIN_GAME + SPACE + GAME_NAME_3;

        // creating a game with name GAME_NAME_1 for USERNAME_1
        controller.executeCommand(CMD_CREAT_1, USERNAME_1);
        // creating a game with name GAME_NAME_2 for USERNAME_2
        controller.executeCommand(CMD_CREAT_2, USERNAME_2);
        // creating a game with name GAME_NAME_3 for USERNAME_3
        controller.executeCommand(CMD_CREAT_3, USERNAME_3);

        // joining USERNAME_1 in GAME_NAME_3
        controller.executeCommand(CMD_JOIN, USERNAME_1);
        // trying to join USERNAME_2 in GAME_NAME_3
        String actual = controller.executeCommand(CMD_JOIN, USERNAME_2);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExJoinGameWithValidInputOutput() {
        final String MESSAGE = "Wrong joining to games!";
        final String NEW_G_NAME = "newGame2";
        final String EXPECTED_OUTPUT = "Joined game " + NEW_G_NAME + System.lineSeparator() +
                "PLAYERS: 2/2, type [start] to start the game";

        final String CMD_CREAT_1 = CREATE_GAME + SPACE + GAME_NAME_1;
        final String CMD_CREAT_2 = CREATE_GAME + SPACE + NEW_G_NAME;
        final String CMD_JOIN = JOIN_GAME + SPACE + NEW_G_NAME;

        // creating a game with name GAME_NAME_1 for USERNAME_1
        controller.executeCommand(CMD_CREAT_1, USERNAME_1);
        // creating a game with name NEW_G_NAME for USERNAME_2
        controller.executeCommand(CMD_CREAT_2, USERNAME_2);
        // joining USERNAME_1 in NEW_G_NAME
        String actual = controller.executeCommand(CMD_JOIN, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    //
    // ---------- TESTS FOR EXECUTE COMMAND WITH [LOAD-GAME] ----------
    //

    @Test
    public void testExLoadGameWithTooFewArgOutput() {
        final String MESSAGE = "Wrong counting of arguments!";
        final String EXPECTED_OUTPUT = "Command load-game takes one argument!";
        final String CMD = LOAD_GAMES;
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExLoadGameWithTooManyArgOutput() {
        final String MESSAGE = "Wrong counting of arguments!";
        final String EXPECTED_OUTPUT = "Command load-game takes one argument!";
        final String CMD = LOAD_GAMES + SPACE + GAME_NAME_1 + SPACE + EXTRA_ARG;
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExLoadGameWithGameNotInStorageOutput() {
        final String MESSAGE = "Wrong loading game!";
        final String EXPECTED_OUTPUT = "There is no game with this name!";
        final String NEW_G_NAME = "newGame3";
        final String CMD = LOAD_GAMES + SPACE + NEW_G_NAME;

        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExLoadGameWithNoSuchGameForUserOutput() {
        final String MESSAGE = "Wrong loading game!";
        final String EXPECTED_OUTPUT = "There is no game with this name in your games!";
        final String NEW_G_NAME = "newGame4";
        final String CMD_LOAD = LOAD_GAMES + SPACE + NEW_G_NAME;
        final String CMD_CREAT_1 = CREATE_GAME + SPACE + GAME_NAME_1;
        final String CMD_CREAT_2 = CREATE_GAME + SPACE + NEW_G_NAME;


        // creating a game with name GAME_NAME_1 for USER_1
        controller.executeCommand(CMD_CREAT_1, USERNAME_1);
        // creating a game with name NEW_G_NAME for USER_2,
        // just to get him into the system
        controller.executeCommand(CMD_CREAT_2, USERNAME_2);
        // trying to load NEW_G_NAME
        String actual = controller.executeCommand(CMD_LOAD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    @Test
    public void testExLoadGameWithValidInputOutput() {
        final String MESSAGE = "Wrong loading game!";
        final String YOUR_BOARD_BANNER = "        YOUR BOARD";
        final String ENEMY_BOARD_BANNER = "       ENEMY BOARD";
        final String NUMBERS = "   1 2 3 4 5 6 7 8 9 10";
        final String TOP_OF_BOARD = "   _ _ _ _ _ _ _ _ _ _";
        final String NL = System.lineSeparator();
        final char TAB = '\t';
        final String EXPECTED_OUTPUT = NL + TAB + YOUR_BOARD_BANNER + NL +
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
                TAB + "J |_|_|_|_|_|_|_|_|_|_|" + NL + NL +
                "Legend:" + NL +
                "* - ship field" + NL +
                "X - hit ship field" + NL +
                "О - hit empty field" + NL +
                "   Possible actions" + NL +
                "***********************************" + NL +
                "  place-ship [ship-type] from to" + NL +
                "___________________________________" + NL +
                "  [ship-type] | length | #" + NL +
                "  destroyer   |     2  | 2" + NL +
                "  submarine   |     3  | 3" + NL +
                "  battleship  |     4  | 2" + NL +
                "  destroyer   |     5  | 1" + NL;

        final String CMD_CREAT = CREATE_GAME + SPACE + GAME_NAME_1;
        final String CMD = LOAD_GAMES + SPACE + GAME_NAME_1;

        // creating a game with name GAME_NAME_1 for USERNAME_1
        controller.executeCommand(CMD_CREAT, USERNAME_1);
        // deleting GAME_NAME_1 for USERNAME_1
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    //
    // ---------- TESTS FOR EXECUTE COMMAND WITH [SAVED-GAME] ----------
    //

    @Test
    public void testExSavedGamesWithTooManyArgOutput() {
        final String MESSAGE = "Wrong listing saved games!";
        final String EXPECTED_OUTPUT = "Command saved-games takes no argument!";
        final String CMD = SAVED_GAMES + SPACE + EXTRA_ARG;
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

    //
    // ---------- TESTS FOR EXECUTE COMMAND WITH INVALID COMMAND ----------
    //

    @Test
    public void testExInvalidCommand() {
        final String MESSAGE = "Wrong handling invalid commands!";
        final String EXPECTED_OUTPUT = "Invalid command!" + System.lineSeparator();
        final String CMD = "alabala-portokala";
        String actual = controller.executeCommand(CMD, USERNAME_1);

        assertEquals(MESSAGE, EXPECTED_OUTPUT, actual);
    }

}
