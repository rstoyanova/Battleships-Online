package commandcenter.command;

import server.data.ServerStorage;
import exceptions.server.data.GameIdNotFound;
import exceptions.server.data.NoEmptySlotsInGame;
import exceptions.server.data.UserAlreadyJoinedGame;

public class JoinGame implements Command {

    private final String[] argv;
    private final String joinerUsername;

    public JoinGame(String[] argv, String joinerUsername) {
        this.argv = argv;
        this.joinerUsername = joinerUsername;
    }

    @Override
    public String execute() {
        final String INVALID_NUM_ARG = "Command join-game takes no more than one argument!";
        final String GAME_ID_NOT_FOUND = "There is no game with this name!";
        final String USR_ALREADY_JOINED_GAME = "You already joined that game!";
        final String NO_EMPTY_SLOTS = "There are no empty slots in this game!";

        if (argv == null || argv.length != 1) {
            return INVALID_NUM_ARG;
        }
        final String gameId = argv[0];

        try {
            ServerStorage.joinGame(gameId, joinerUsername);
        } catch (GameIdNotFound e) {
            return GAME_ID_NOT_FOUND;
        } catch (UserAlreadyJoinedGame e) {
            return USR_ALREADY_JOINED_GAME;
        } catch (NoEmptySlotsInGame e) {
            return NO_EMPTY_SLOTS;
        }

        return reply(gameId);
    }

    private String reply(String gameId) {
        return "Joined game " + gameId + System.lineSeparator() +
                "PLAYERS: 2/2, type [start] to start the game";
    }
}
