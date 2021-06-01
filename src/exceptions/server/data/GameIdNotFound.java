package exceptions.server.data;

public class GameIdNotFound extends Exception{
    public GameIdNotFound(String gameId) {
        super("Game id [" + gameId + "] is not in the system!");
    }
}
