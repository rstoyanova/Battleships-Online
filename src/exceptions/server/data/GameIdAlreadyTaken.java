package exceptions.server.data;

public class GameIdAlreadyTaken extends Exception {
    public GameIdAlreadyTaken(String gameId) {
        super("The game id [" + gameId+ "] is already taken!");
    }
}
