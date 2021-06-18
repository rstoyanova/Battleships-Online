package exceptions.game.battleships.gameengine;

public class PlayerNotInGame extends Exception{
    public PlayerNotInGame(String player) {
        super("Player " + player + " not in game!");
    }
}
