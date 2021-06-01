package exceptions.game.battleships.board;

public class AllShipsAlreadyPlaced extends BoardException {
    public AllShipsAlreadyPlaced() {
        super("All ships already placed!");
    }
}
