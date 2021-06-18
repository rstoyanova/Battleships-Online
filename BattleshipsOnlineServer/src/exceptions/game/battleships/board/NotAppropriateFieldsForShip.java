package exceptions.game.battleships.board;

import exceptions.game.battleships.board.BoardException;

public class NotAppropriateFieldsForShip extends BoardException {
    public NotAppropriateFieldsForShip() {
        super("Ship won't fit between those fields!");
    }
}
