package exceptions.game.battleships.board;

import exceptions.game.battleships.board.BoardException;
import game.battleships.ships.Ship;

public class NoAvailableShipsOfType extends BoardException {
    public NoAvailableShipsOfType(Ship ship) {
        super("No more " + ship.toString());
    }
}
