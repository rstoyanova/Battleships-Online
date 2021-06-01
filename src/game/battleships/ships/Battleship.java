package game.battleships.ships;

public class Battleship implements Ship {

    public static int defaultCount() {
        return 2;
    }

    @Override
    public String toString() {
        return "Battleship";
    }

    @Override
    public int getLength() {
        return 4;
    }
}
