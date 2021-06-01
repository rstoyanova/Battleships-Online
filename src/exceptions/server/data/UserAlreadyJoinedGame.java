package exceptions.server.data;

public class UserAlreadyJoinedGame extends Exception{
    public UserAlreadyJoinedGame(String gameId) {
        super("User already joined game [" + gameId+ "]!");
    }
}
