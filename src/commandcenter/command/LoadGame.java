package commandcenter.command;

import server.data.ServerStorage;
import exceptions.server.data.GameIdNotFound;
import exceptions.server.data.NoSuchGameForUser;

public class LoadGame implements Command {

    private final String[] argv;
    private final String loaderUsername;

    public LoadGame(String[] argv, String loaderUsername) {
        this.argv = argv;
        this.loaderUsername = loaderUsername;
    }

    @Override
    public String execute() {
        final String INVALID_NUM_ARG = "Command load-game takes one argument!";

        if (argv == null || argv.length != 1) {
            return INVALID_NUM_ARG;
        }
        final String gameId = argv[0];

        return reply(gameId, loaderUsername);
    }

    private String reply(String gameId, String username) {
        final String GAME_ID_NOT_FOUND = "There is no game with this name!";
        final String NO_SUCH_GAME_FOR_USR = "There is no game with this name in your games!";

        String reply;
        try {
            reply = ServerStorage.loadGame(gameId, username);
        } catch (GameIdNotFound e) {
            return GAME_ID_NOT_FOUND;
        } catch (NoSuchGameForUser e) {
            return NO_SUCH_GAME_FOR_USR;
        }
        return reply;
    }
}
