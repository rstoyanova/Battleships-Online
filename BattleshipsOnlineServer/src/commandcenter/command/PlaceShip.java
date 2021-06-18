package commandcenter.command;

import commandcenter.parser.FieldParser;
import commandcenter.parser.ShipParser;
import exceptions.game.battleships.board.*;
import exceptions.game.battleships.ships.NotValidShip;
import game.battleships.board.BattleshipsBoardField;
import exceptions.game.battleships.gameengine.PlayerNotInGame;
import game.battleships.ships.Ship;
import server.data.ServerStorage;
import exceptions.server.data.UserNotCurrentlyPlaying;

public class PlaceShip implements Command {
    private final String[] argv;
    private final String placerUsername;

    public PlaceShip(String[] argv, String placerUsername) {
        this.argv = argv;
        this.placerUsername = placerUsername;
    }

    @Override
    public String execute() {
        final String INVALID_NUM_ARG = "Command place-ship takes three arguments!";
        final String INVALID_FIELD = "Invalid fields! Try again :)";
        final String INVALID_SHIP = "Invalid ship! Try again :)";

        final String NOT_PLAYING = "You are not playing! Load a game!";
        final String NO_SHIPS_OF_TYPE = "There are no more ships to place of this type!";
        final String PLAYER_NOT_IN_GAME = "You are not in this game!";
        final String NOT_APPR_FIELDS_FOR_SHIPS = "These fields are not appropriate for this ship!";
        final String FIELDS_NOT_IN_LINE = "These fields are not in a line! Try again :)";
        final String FIELDS_ALREADY_OCCUPIED = "Fields already occupied!";
        final String ALL_SHIPS_PLACED = "All ships are already placed! Attack!";

        if (argv == null || argv.length == 0 || argv.length % 3 != 0) {
            return INVALID_NUM_ARG;
        }
        String BOARD_DATA = "Nothing is placed!";
        StringBuilder ERR_OCCURRED = new StringBuilder();
        for (int i=0; i<argv.length ; i+=3) {
            String from = argv[i+1];
            String to = argv[i+2];

            BattleshipsBoardField fieldFrom;
            BattleshipsBoardField fieldTo;
            try {
                fieldFrom = FieldParser.getField(from);
                fieldTo = FieldParser.getField(to);
            } catch (NotValidField e) {
                ERR_OCCURRED.append(INVALID_FIELD + " : ")
                        .append(from)
                        .append(" ")
                        .append(to).append(System.lineSeparator());
                continue;
            }

            Ship ship;
            try {
                ship = ShipParser.getShip(argv[i]);
            } catch (NotValidShip e) {
                ERR_OCCURRED.append(INVALID_SHIP + " : ")
                        .append(from)
                        .append(" ")
                        .append(to).append(System.lineSeparator());
                continue;
            }

            try {
                BOARD_DATA = ServerStorage.placeShip(ship, fieldFrom, fieldTo, placerUsername);
            } catch (UserNotCurrentlyPlaying e) {
                return NOT_PLAYING;
            } catch (PlayerNotInGame e) {
                return PLAYER_NOT_IN_GAME;
            } catch (AllShipsAlreadyPlaced e) {
                return ALL_SHIPS_PLACED;
            } catch (NoAvailableShipsOfType e) {
                return NO_SHIPS_OF_TYPE;
            } catch (NotValidField e) {
                ERR_OCCURRED.append(INVALID_FIELD + " : ")
                        .append(from).append(" ")
                        .append(to).append(System.lineSeparator());
            } catch (FieldsNotInLine e) {
                ERR_OCCURRED.append(FIELDS_NOT_IN_LINE + " : ")
                        .append(from)
                        .append(" ")
                        .append(to).append(System.lineSeparator());
            } catch (NotAppropriateFieldsForShip e) {
                ERR_OCCURRED.append(NOT_APPR_FIELDS_FOR_SHIPS + " : ")
                        .append(from).append(" ")
                        .append(to).append(System.lineSeparator());
            } catch (FieldsAlreadyOccupied e) {
                ERR_OCCURRED.append(FIELDS_ALREADY_OCCUPIED + " : ")
                        .append(from)
                        .append(" ")
                        .append(to).append(System.lineSeparator());
            }
        }
        return ERR_OCCURRED + BOARD_DATA;
    }
}
