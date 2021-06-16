package game.battleships.gameengine;


public interface Game {

    String toString(String player);

    int numberOfPlayersForGame();

    int numberOfCurrentPlayers();

    String getCreatorsUsername();

    String getOpponentUsername();

    String getStat();
}
