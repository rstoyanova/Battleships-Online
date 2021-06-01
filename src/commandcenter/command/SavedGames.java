package commandcenter.command;

import game.battleships.gameengine.BattleshipsGame;
import server.data.ServerStorage;

import java.util.Map;

public class SavedGames implements Command {

    private static final char COLUMN_SEPARATOR = '|';
    private static final String NL = System.lineSeparator();
    private static final String HEADER =
            "|     NAME      |    CREATOR    |    STATUS   | PLAYERS |" + NL +
                    "|---------------+---------------+-------------+---------|";
    private static final int NUM_NEEDED_PLAYERS = 2;


    private final String[] argv;
    private final String reqUsername;

    public SavedGames(String[] argv, String saverUsername) {
        this.argv = argv;
        this.reqUsername = saverUsername;
    }

    @Override
    public String execute() {
        final String INVALID_NUM_ARG = "Command saved-games takes no argument!";
        if (argv != null) {
            return INVALID_NUM_ARG;
        }
        Map<String, BattleshipsGame> savedGames = ServerStorage.getSavedGames(reqUsername);

        return reply(savedGames);
    }

    private String reply(Map<String, BattleshipsGame> savedGames) {
        StringBuilder reply = new StringBuilder("There are no saved games!");


        if (savedGames == null) {
            return reply.toString();
        }

        if (savedGames.isEmpty()) {
            return reply.toString();
        }
        reply = new StringBuilder(HEADER);

        reply.append(NL);
        for (Map.Entry<String, BattleshipsGame> game : savedGames.entrySet()) {
            reply.append(getFormattedInfoForGame(game.getKey(), game.getValue()));
        }
        return reply.toString();
    }

    private String getFormattedInfoForGame(String gameId, BattleshipsGame game) {
        String formatNames = COLUMN_SEPARATOR + " %-13s ";
        String formatStat = COLUMN_SEPARATOR + " %-11s ";
        String formatNumPl = COLUMN_SEPARATOR + " %6s  " + COLUMN_SEPARATOR;
        String players = game.numberOfCurrentPlayers() + "/" + NUM_NEEDED_PLAYERS;

        return String.format(formatNames, gameId) +
                String.format(formatNames, game.getCreatorsUsername()) +
                String.format(formatStat, game.getStat()) +
                String.format(formatNumPl, players) +
                NL;
    }

}
