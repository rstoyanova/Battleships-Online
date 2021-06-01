package game.battleships.ships;

public class Destroyer implements Ship {

    public static int defaultCount() {
        return 2;
    }

    @Override
    public String toString() {
        return "Destroyer";
    }

    @Override
    public int getLength() {
        return 2;
    }
}
