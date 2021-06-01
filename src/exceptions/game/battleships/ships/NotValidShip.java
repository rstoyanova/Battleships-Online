package exceptions.game.battleships.ships;

public class NotValidShip extends Exception{
    public NotValidShip() {
        super("Not valid ship");
    }
}
