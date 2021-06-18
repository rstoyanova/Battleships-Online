package commandcenter.command;

import server.data.ServerStorage;
import exceptions.server.data.GameIdAlreadyTaken;

public class CreateGame implements Command {

    private final String[] argv;
    private final String creatorsUsername;

    public CreateGame(String[] argv, String creatorsUsername) {
        this.argv = argv;
        this.creatorsUsername = creatorsUsername;
    }

    @Override
    public String execute() {
        final String INVALID_NUM_ARG = "Command create-game takes one argument!";
        final String GAME_ID_TAKEN = "Game name already taken! Try another one :)";

        if (argv == null || argv.length != 1) {
            return INVALID_NUM_ARG;
        }
        final String gameId = argv[0];

        try {
            ServerStorage.createGame(gameId, creatorsUsername);
        } catch (GameIdAlreadyTaken e) {
            return GAME_ID_TAKEN;
        }

        return reply(gameId);
    }

    private String reply(String gameId) {
        return "Created game " + gameId + ", players 1/2";
    }

}
