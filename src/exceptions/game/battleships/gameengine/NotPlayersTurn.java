package exceptions.game.battleships.gameengine;

public class NotPlayersTurn extends Exception {
    public NotPlayersTurn(String username) {
        super("Not " + username + "'s turn!");
    }
}
