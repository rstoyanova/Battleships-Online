package game.battleships.ships;

public class Submarine implements Ship {

    public static int defaultCount() {
        return 3;
    }

    @Override
    public String toString() {
        return "Submarine";
    }

    @Override
    public int getLength() {
        return 3;
    }
}
