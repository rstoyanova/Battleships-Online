package exceptions.server.data;

public class NoEmptySlotsInGame extends Exception {
    public NoEmptySlotsInGame(String gameId) {
        super("No more empty slots in game [" + gameId + "]");
    }
}
