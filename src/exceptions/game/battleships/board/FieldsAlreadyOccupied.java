package exceptions.game.battleships.board;

import exceptions.game.battleships.board.BoardException;

public class FieldsAlreadyOccupied extends BoardException {
    public FieldsAlreadyOccupied() {
        super("These fields are already occupied!");
    }
}
