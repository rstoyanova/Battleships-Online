package game.battleships.gameengine;

import game.battleships.board.BattleshipsBoard;
import exceptions.game.battleships.gameengine.PlayersSlotsAreTaken;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BattleshipsGame implements Game, Serializable {
    protected final Map<String, BattleshipsBoard> boards;
    protected final String creatorsUsername;

    private static final String NL = System.lineSeparator();

    public BattleshipsGame(String creator) {
        this.creatorsUsername = creator;
        boards = new HashMap<>();
        boards.put(creatorsUsername, new BattleshipsBoard());
    }

    public BattleshipsGame(BattleshipsGame other) {
        this.boards = other.boards;
        this.creatorsUsername = other.creatorsUsername;
    }

    public void setSecondPlayer(String playersUsername) throws PlayersSlotsAreTaken {
        if (playersUsername == null) {
            throw new IllegalArgumentException("Players username must not be null!");
        }
        if (numberOfCurrentPlayers() == 2) {
            throw new PlayersSlotsAreTaken();
        }
        boards.put(playersUsername, new BattleshipsBoard());
    }

    @Override
    public String toString(String player) {
        String output = NL + boards.get(player).toString(false);

        if (numberOfCurrentPlayers() == 2) {
            output += NL + NL +
                    boards.get(getEnemy(player)).toString(true);
        }
        output += NL +
                getLegend();
        return output;
    }

    @Override
    public int numberOfPlayersForGame() {
        return 2;
    }

    @Override
    public int numberOfCurrentPlayers() {
        if (boards.size() == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public String getCreatorsUsername() {
        return creatorsUsername;
    }

    @Override
    public String getOpponentUsername() {
        return getEnemy(creatorsUsername);
    }

    @Override
    public String getStat() {
        String inProgress = "in progress";
        String pending = "pending";

        return numberOfCurrentPlayers() == numberOfPlayersForGame() ?
                inProgress : pending;
    }

    public boolean allShipsPlaced() {
        return boards.get(creatorsUsername).allShipsPlaced()
                && boards.get(getEnemy(creatorsUsername)).allShipsPlaced();
    }

    public boolean userPlacedAllShips(String username) {
        return boards.get(username).allShipsPlaced();
    }

    protected boolean playerIsNotInTheGame(String player) {
        return !boards.containsKey(player);
    }

    protected String getEnemy(String player) {
        for (Map.Entry<String, BattleshipsBoard> i : boards.entrySet()) {
            if (!i.getKey().equals(player)) {
                return i.getKey();
            }
        }
        return "";
    }

    private String getLegend() {
        return "  Legend:" + NL +
                "  * - ship field" + NL +
                "  X - hit ship field" + NL +
                "  O - hit empty field" + NL + NL;
    }
}
