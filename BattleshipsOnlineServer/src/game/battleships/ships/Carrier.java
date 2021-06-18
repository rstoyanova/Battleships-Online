package game.battleships.ships;

public class Carrier implements Ship {

    public static int defaultCount() {
        return 1;
    }

    @Override
    public String toString() {
        return "Carrier";
    }

    @Override
    public int getLength() {
        return 5;
    }
}
