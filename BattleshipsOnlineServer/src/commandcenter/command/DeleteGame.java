package commandcenter.command;

import server.data.ServerStorage;
import exceptions.server.data.GameIdNotFound;
import exceptions.server.data.NoSuchGameForUser;

public class DeleteGame implements Command {

    private final String[] argv;
    private final String delUsername;

    public DeleteGame(String[] argv, String delUsername) {
        this.argv = argv;
        this.delUsername = delUsername;
    }

    @Override
    public String execute() {
        final String INVALID_NUM_ARG = "Command delete-game takes one argument!";
        final String GAME_ID_NOT_FOUND = "There is no game with this name!";
        final String NO_SUCH_GAME_FOR_USR = "There is no game with this name in your games!";

        if (argv == null || argv.length != 1) {
            return INVALID_NUM_ARG;
        }
        String gameId = argv[0];

        try {
            ServerStorage.deleteGame(gameId, delUsername);
        } catch (NoSuchGameForUser e) {
            return NO_SUCH_GAME_FOR_USR;
        } catch (GameIdNotFound e) {
            return GAME_ID_NOT_FOUND;
        }

        return reply(gameId);
    }

    private String reply(String gameId) {
        return "Game [" + gameId + "] was successfully deleted ";
    }
}
