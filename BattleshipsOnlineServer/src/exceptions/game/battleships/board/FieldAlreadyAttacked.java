package exceptions.game.battleships.board;

import exceptions.game.battleships.board.BoardException;

public class FieldAlreadyAttacked extends BoardException {
    public FieldAlreadyAttacked() {
        super("Field already attacked!");
    }
}
