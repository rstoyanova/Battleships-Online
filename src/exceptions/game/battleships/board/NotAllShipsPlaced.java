package exceptions.game.battleships.board;

import exceptions.game.battleships.board.BoardException;

public class NotAllShipsPlaced extends BoardException {
    public NotAllShipsPlaced() {
        super("Not all ships are placed! Can't attack!");
    }
}
