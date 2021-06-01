package commandcenter.command;

import commandcenter.parser.FieldParser;
import exceptions.game.battleships.board.FieldAlreadyAttacked;
import exceptions.game.battleships.board.GameOver;
import exceptions.game.battleships.board.NotAllShipsPlaced;
import exceptions.game.battleships.board.NotValidField;
import game.battleships.board.BattleshipsBoardField;
import exceptions.game.battleships.gameengine.NotPlayersTurn;
import exceptions.game.battleships.gameengine.PlayerNotInGame;
import server.data.ServerStorage;
import exceptions.server.data.UserNotCurrentlyPlaying;

public class Attack implements Command {

    private final String[] argv;
    private final String attackerUsername;

    public Attack(String[] argv, String attackerUsername) {
        this.argv = argv;
        this.attackerUsername = attackerUsername;
    }

    @Override
    public String execute() {
        final String INVALID_NUM_ARG = "Command attack takes one argument!";
        final String INVALID_FIELD = "This field is invalid! Try again :)";
        final String GAME_OVER = "-----------------------------------" + System.lineSeparator() +
                "--------YOU WON! CONGRATS!---------" + System.lineSeparator() +
                "-----------------------------------";
        final String NOT_PLAYING = "You are not playing! Load a game!";
        final String ALREADY_ATTACKED = "This field is already attacked! Try another one!";
        final String NOT_ALL_SHIPS_PLACED = "There are non-placed ships! Wait till placed!";
        final String PLAYER_NOT_IN_GAME = "You are not in this game!";
        final String NOT_USERS_TURN = "It's not your turn! Wait opponent!";
        final String SUCCESS = "Successfully attacked! Wait for opponent!";

        if (argv == null || argv.length != 1) {
            return INVALID_NUM_ARG;
        }
        final String TARGET = argv[0];
        BattleshipsBoardField field = null;
        try {
            field = FieldParser.getField(TARGET);
        } catch (NotValidField e) {
            return INVALID_FIELD;
        }

        try {
            ServerStorage.attack(field, attackerUsername);
        } catch (UserNotCurrentlyPlaying e) {
            return NOT_PLAYING;
        } catch (PlayerNotInGame e) {
            return PLAYER_NOT_IN_GAME;
        } catch (NotPlayersTurn e) {
            return NOT_USERS_TURN;
        } catch (NotValidField e) {
            return INVALID_FIELD;
        } catch (GameOver e) {
            return GAME_OVER;
        } catch (FieldAlreadyAttacked e) {
            return ALREADY_ATTACKED;
        } catch (NotAllShipsPlaced e) {
            return NOT_ALL_SHIPS_PLACED;
        }

        return SUCCESS;
    }
}
