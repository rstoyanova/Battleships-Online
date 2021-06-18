package exceptions.server.data;

public class UserNotCurrentlyPlaying extends Exception{
    public UserNotCurrentlyPlaying() {
        super("User not currently playing!");
    }
}
