package commandcenter;

import commandcenter.command.*;
import commandcenter.parser.CmdParser;


public class Controller {

    private static final String CREATE_GAME = "create-game";
    private static final String DELETE_GAME = "delete-game";
    private static final String JOIN_GAME = "join-game";
    private static final String LOAD_GAMES = "load-game";
    private static final String SAVED_GAMES = "saved-games";
    private static final String ATTACK = "attack";
    private static final String PLACE_SHIP = "place-ship";
    private static final String PRINT_BOARD = "print-board";
    private static final String EXIT = "exit";

    private static final Executor executor = new Executor();

    public String executeCommand(String argv, String username) {
        String cmd = CmdParser.getCMD(argv);
        String[] arg = CmdParser.getArg(argv);

        final String INVALID_COMMAND = "Invalid command!" + System.lineSeparator();

        return switch (cmd) {
            case CREATE_GAME -> executor.execute(new CreateGame(arg, username));
            case DELETE_GAME -> executor.execute(new DeleteGame(arg, username));
            case JOIN_GAME -> executor.execute(new JoinGame(arg, username));
            case LOAD_GAMES -> executor.execute(new LoadGame(arg, username));
            case SAVED_GAMES -> executor.execute(new SavedGames(arg, username));
            case ATTACK -> executor.execute(new Attack(arg, username));
            case PLACE_SHIP -> executor.execute(new PlaceShip(arg, username));
            case PRINT_BOARD -> executor.execute(new ViewBoard(username));
            case EXIT -> executor.execute(new ExitGame(username));
            default -> INVALID_COMMAND;
        };
    }
}
