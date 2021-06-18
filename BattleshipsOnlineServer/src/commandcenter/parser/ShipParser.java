package commandcenter.parser;

import exceptions.game.battleships.board.NotValidField;
import exceptions.game.battleships.ships.NotValidShip;
import game.battleships.ships.*;

public class ShipParser {
    private static final String BATTLESHIP = "battleship";
    private static final String CARRIER = "carrier";
    private static final String DESTROYER = "destroyer";
    private static final String SUBMARINE = "submarine";

    public static Ship getShip(String str) throws NotValidShip {
        String ship = str.toLowerCase();

        return switch (ship) {
            case BATTLESHIP -> new Battleship();
            case CARRIER -> new Carrier();
            case DESTROYER -> new Destroyer();
            case SUBMARINE -> new Submarine();
            default -> throw new NotValidShip();
        };
    }
}
