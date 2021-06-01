package commandcenter.command;

import exceptions.server.data.UserNotCurrentlyPlaying;
import server.data.ServerStorage;

public class PrintBoard implements Command {
    private final String player;
    public PrintBoard(String player) {
        this.player = player;
    }

    public String execute() {
        final String NOT_PLAYING = "You are not playing! Load a game!";
        String reply;
        try {
            reply = ServerStorage.printBoard(player);
        } catch (UserNotCurrentlyPlaying e) {
            return NOT_PLAYING;
        }
        return reply;
    }
}
