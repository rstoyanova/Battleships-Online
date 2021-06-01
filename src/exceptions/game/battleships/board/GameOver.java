package exceptions.game.battleships.board;

import exceptions.game.battleships.board.BoardException;

public class GameOver extends BoardException {
    public GameOver () {
        super("Game Over!");
    }
}
