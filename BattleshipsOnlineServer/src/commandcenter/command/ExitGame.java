package commandcenter.command;

import exceptions.server.data.UserNotCurrentlyPlaying;
import server.data.ServerStorage;

public class ExitGame implements Command {

    private final String username;

    public ExitGame(String username) {
        this.username = username;
    }

    @Override
    public String execute() {
        final String SUCC_REPLY = "Exited game successfully" + System.lineSeparator();
        final String NOT_PLAYING = "You are not playing! Load a game!";
        try {
            ServerStorage.exit(username);
        } catch (UserNotCurrentlyPlaying e) {
            return NOT_PLAYING;
        }
        return SUCC_REPLY;
    }
}
