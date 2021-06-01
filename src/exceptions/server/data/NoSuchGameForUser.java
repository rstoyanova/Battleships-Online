package exceptions.server.data;

public class NoSuchGameForUser extends Exception {
    public NoSuchGameForUser(String username,String gameId) {
        super("User [" + username + "] is not part of game [" + gameId + "]!");
    }
}
