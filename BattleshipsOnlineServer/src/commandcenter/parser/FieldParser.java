package commandcenter.parser;

import game.battleships.board.BattleshipsBoardField;
import exceptions.game.battleships.board.NotValidField;

public class FieldParser {

    public static BattleshipsBoardField getField(String str) throws NotValidField {
        if (str.length() != 2) {
            throw new NotValidField(str);
        }
        char row = str.charAt(0);
        int col = str.charAt(1) - '0';

        return new BattleshipsBoardField(row, col);
    }
}
