package game.battleships.gameengine;

import exceptions.game.battleships.board.*;
import game.battleships.board.BattleshipsBoardField;
import exceptions.game.battleships.gameengine.NotPlayersTurn;
import exceptions.game.battleships.gameengine.PlayerNotInGame;
import game.battleships.ships.*;

import java.io.Serializable;

public class ActiveBattleshipsGame extends BattleshipsGame implements Serializable {

    private String userMadeLastTurn;
    private BattleshipsBoardField lastTurn;

    private static final String BANNER =
            "                         Possible actions" + System.lineSeparator()
                    + "*****************************************************************" + System.lineSeparator();
    private static final String ATTACK_INFO = "   attack target" + System.lineSeparator() + System.lineSeparator();

    public ActiveBattleshipsGame(String creator) {
        super(creator);
        userMadeLastTurn = null;
        lastTurn = null;
    }

    public ActiveBattleshipsGame(BattleshipsGame game) {
        super(game);
        userMadeLastTurn = null;
        lastTurn = null;
    }

    public String placeShip(String player, Ship ship, BattleshipsBoardField startPoint, BattleshipsBoardField endPoint)
            throws NoAvailableShipsOfType, NotAppropriateFieldsForShip, FieldsNotInLine,
            FieldsAlreadyOccupied, PlayerNotInGame, NotValidField, AllShipsAlreadyPlaced {

        if (player == null || ship == null || startPoint == null || endPoint == null) {
            throw new IllegalArgumentException("Arguments must not be null!");
        }
        if (playerIsNotInTheGame(player)) {
            throw new PlayerNotInGame(player);
        }

        boards.get(player).placeShip(ship, startPoint, endPoint);
        String BOARD_DATA = "Successfully placed!" + System.lineSeparator() +
                toString(player) + System.lineSeparator() +
                BANNER;
        if (boards.get(player).allShipsPlaced()) {
            BOARD_DATA += ATTACK_INFO;
        } else {
            BOARD_DATA += getPlaceInfo(player);
        }

        return BOARD_DATA;
    }

    public String attack(String player, BattleshipsBoardField target)
            throws NotValidField, GameOver, FieldAlreadyAttacked,
            NotAllShipsPlaced, PlayerNotInGame, NotPlayersTurn {

        if (player == null || target == null) {
            throw new IllegalArgumentException("Arguments must not be null!");
        }
        if (playerIsNotInTheGame(player)) {
            throw new PlayerNotInGame(player);
        }
        if (isNotNewGame() && userMadeLastTurn.equals(player)) {
            throw new NotPlayersTurn(player);
        }

        boolean notNewGame = false;
        String reply = null;
        if (isNotNewGame()) {
            notNewGame = true;
            reply = toStringLastTurn(player, target);
        }

        boards.get(getEnemy(player)).attack(target);
        userMadeLastTurn = player;
        lastTurn = target;
        return notNewGame ? reply : null;
    }

    public String getPlaceInfo(String player) {

        StringBuilder placeInfo = new StringBuilder("      place-ship [ship-type] from to {[ship-type] from to .... }")
                .append(System.lineSeparator())
                .append("_________________________________________________________________")
                .append(System.lineSeparator())
                .append("          [ship-type] | length | #")
                .append(System.lineSeparator())
                .append("          destroyer   |     2  | ")
                .append(boards.get(player).leftToPlaceFromType(new Destroyer()))
                .append(System.lineSeparator())
                .append("          submarine   |     3  | ")
                .append(boards.get(player).leftToPlaceFromType(new Submarine()))
                .append(System.lineSeparator())
                .append("          battleship  |     4  | ")
                .append(boards.get(player).leftToPlaceFromType(new Battleship()))
                .append(System.lineSeparator())
                .append("          carrier     |     5  | ")
                .append(boards.get(player).leftToPlaceFromType(new Carrier()));
        return placeInfo.toString();
    }

    private boolean isNotNewGame() {
        return lastTurn != null;
    }

    private String toStringLastTurn(String player, BattleshipsBoardField target) {
        return getEnemy(player) + "'s last turn: " +
                target.toString() + System.lineSeparator();
    }

}


