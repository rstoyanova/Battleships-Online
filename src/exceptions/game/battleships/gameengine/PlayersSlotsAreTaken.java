package exceptions.game.battleships.gameengine;

public class PlayersSlotsAreTaken extends Exception{
    public PlayersSlotsAreTaken() {
        super("There sre no empty slots in game!");
    }
}
