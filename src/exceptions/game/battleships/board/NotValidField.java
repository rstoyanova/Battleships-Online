package exceptions.game.battleships.board;

import exceptions.game.battleships.board.BoardException;
import game.battleships.board.BattleshipsBoardField;

public class NotValidField extends BoardException {
    public NotValidField(String field) {
        super("Field " + field.toString() + " is not valid!");
    }
    public NotValidField(BattleshipsBoardField field) {
        super("Field " + field.toString() + " is not valid!");
    }
}
