package exceptions.game.battleships.board;

import exceptions.game.battleships.board.BoardException;

public class FieldsNotInLine extends BoardException {
    public FieldsNotInLine(String message) {
        super(message);
    }
}
