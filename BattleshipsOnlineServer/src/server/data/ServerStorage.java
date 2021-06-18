package server.data;

import exceptions.game.battleships.board.*;
import exceptions.server.data.*;
import game.battleships.board.BattleshipsBoardField;
import exceptions.game.battleships.gameengine.NotPlayersTurn;
import exceptions.game.battleships.gameengine.PlayerNotInGame;
import exceptions.game.battleships.gameengine.PlayersSlotsAreTaken;
import game.battleships.gameengine.ActiveBattleshipsGame;
import game.battleships.gameengine.BattleshipsGame;
import game.battleships.ships.Ship;
import server.data.actions.Loader;
import server.data.actions.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerStorage implements Storage {
    private static final ServerStorage storage = new ServerStorage();

    // <GameId, Game>
    private static Map<String, BattleshipsGame> gameData;
    // <Username, [GameId]> // user <-> games he is joined in
    private static Map<String, List<String>> usersGames;
    // <GameId, ActiveGame>> // currently active games
    private static Map<String, ActiveBattleshipsGame> activeGames;
    // <Username, GameId> // users currently playing
    private static Map<String, String> playingUsers;

    private static final String DEFAULT_FILENAME_FOR_SAVINGS = "data_server.txt";
    private static final String BANNER =
            "                         Possible actions" + System.lineSeparator()
                    + "*****************************************************************" + System.lineSeparator();
    private static final String ATTACK_INFO = "  attack [target]" + System.lineSeparator() + System.lineSeparator();

    private ServerStorage() {
        gameData = new HashMap<>();
        usersGames = new HashMap<>();
        activeGames = new HashMap<>();
        playingUsers = new HashMap<>();
    }

    public static ServerStorage getStorage() {
        return storage;
    }

    @Override
    public void saveServerDataInFile() {
        Saver.saveServerDataToFile(DEFAULT_FILENAME_FOR_SAVINGS, gameData);
    }

    @Override
    public void loadServerDataFromFile() {
        gameData = Loader.loadServerDataFromFile(DEFAULT_FILENAME_FOR_SAVINGS);
    }

    public static void createGame(String gameId, String creatorUsername) throws GameIdAlreadyTaken {
        if (gameData.containsKey(gameId)) {
            throw new GameIdAlreadyTaken(gameId);
        }
        if (!usersGames.containsKey(creatorUsername)) {
            List<String> games = new ArrayList<>();
            usersGames.put(creatorUsername, games);
        }
        usersGames.get(creatorUsername).add(gameId);
        gameData.put(gameId, new BattleshipsGame(creatorUsername));
    }

    public static void joinGame(String gameId, String joinerUsername) throws GameIdNotFound, UserAlreadyJoinedGame, NoEmptySlotsInGame {
        if (!gameData.containsKey(gameId)) {
            throw new GameIdNotFound(gameId);
        }
        if (usersGames.containsKey(joinerUsername) &&
                usersGames.get(joinerUsername).contains(gameId)) {
            throw new UserAlreadyJoinedGame(gameId);
        }

        try {
            gameData.get(gameId).setSecondPlayer(joinerUsername);
            if(activeGames.containsKey(gameId)) {
                activeGames.get(gameId).setSecondPlayer(joinerUsername);
            }
        } catch (PlayersSlotsAreTaken e) {
            throw new NoEmptySlotsInGame(gameId);
        }
        if (!usersGames.containsKey(joinerUsername)) {
            List<String> games = new ArrayList<>();
            usersGames.put(joinerUsername, games);
        }
        usersGames.get(joinerUsername).add(gameId);
    }

    public static Map<String, BattleshipsGame> getSavedGames(String username) {
        if (gameData.isEmpty()) {
            return null;
        }
        return gameData;
    }

    public static Map<String, BattleshipsGame> getSavedGamesForUser(String username) {
        if (gameData.isEmpty()) {
            return null;
        }
        Map<String, BattleshipsGame> userGames = new HashMap<>();
        for (String gameId : usersGames.get(username)) {
            userGames.put(gameId, gameData.get(gameId));
        }
        return userGames;
    }

    public static String loadGame(String gameId, String loaderUsername) throws GameIdNotFound, NoSuchGameForUser {
        if (!gameData.containsKey(gameId)) {
            throw new GameIdNotFound(gameId);
        }
        if (!usersGames.get(loaderUsername).contains(gameId)) {
            throw new NoSuchGameForUser(loaderUsername, gameId);
        }

        ActiveBattleshipsGame game = null;
        if (!activeGames.containsKey(gameId)) {
            if (gameData.containsKey(gameId)) {
                // in case the game is saved in a file
                game = Loader.loadActiveGameFromFile(gameId);
                if (game == null) {
                    // game have not been activated till this moment
                    BattleshipsGame cashedGame = gameData.get(gameId);
                    game = new ActiveBattleshipsGame(cashedGame);
                }
            } else {
                throw new GameIdNotFound(gameId);
            }
            activeGames.put(gameId, game);
        }

        String LEGEND = BANNER;
        if (game.allShipsPlaced()) {
            LEGEND += ATTACK_INFO;
        } else if (game.userPlacedAllShips(loaderUsername)) {
            LEGEND += "  Wait for opponent to place all ships" + System.lineSeparator();
        } else {
            LEGEND += game.getPlaceInfo(loaderUsername);
        }

        playingUsers.put(loaderUsername, gameId);
        return activeGames.get(gameId).toString(loaderUsername) + LEGEND;
    }

    public static void exit(String username) throws UserNotCurrentlyPlaying{
        if (!playingUsers.containsKey(username)
            || !activeGames.containsKey(playingUsers.get(username))) {
            throw new UserNotCurrentlyPlaying();
        }

        String gameId = playingUsers.get(username);
        playingUsers.remove(username);

        // just in case there is one person playing the game is saved in a file
        if (activeGames.get(gameId).numberOfCurrentPlayers() == 1) {
            Saver.saveActiveGameToFile(gameId, activeGames.get(gameId));
            activeGames.remove(gameId);
        }
    }

    public static void deleteGame(String gameId, String delUsername) throws GameIdNotFound, NoSuchGameForUser {
        if (!gameData.containsKey(gameId)) {
            throw new GameIdNotFound(gameId);
        }
        if (!usersGames.get(delUsername).contains(gameId)) {
            throw new NoSuchGameForUser(delUsername, gameId);
        }
        BattleshipsGame toDel = gameData.get(gameId);

        String enemyUsername = toDel.numberOfCurrentPlayers() == toDel.numberOfPlayersForGame() ?
                toDel.getOpponentUsername() : null;
        if (enemyUsername != null) {
            usersGames.get(enemyUsername).remove(gameId);
        }
        gameData.remove(gameId);
        usersGames.get(delUsername).remove(gameId);
        activeGames.remove(gameId);

        deleteFileForGame(gameId);
    }

    public static void attack(BattleshipsBoardField target, String attackerUsername)
            throws UserNotCurrentlyPlaying, NotValidField, GameOver, FieldAlreadyAttacked,
            NotAllShipsPlaced, PlayerNotInGame, NotPlayersTurn {

        if (!playingUsers.containsKey(attackerUsername)) {
            throw new UserNotCurrentlyPlaying();
        }
        String gameId = playingUsers.get(attackerUsername);
        try {
            activeGames.get(gameId).attack(attackerUsername, target);
        } catch (GameOver e) {
            deleteCompletedGame(gameId, attackerUsername);
            throw new GameOver();
        }
    }

    public static String placeShip(Ship ship, BattleshipsBoardField from, BattleshipsBoardField to, String placerUsername)
            throws UserNotCurrentlyPlaying, NoAvailableShipsOfType, NotAppropriateFieldsForShip,
            FieldsNotInLine, FieldsAlreadyOccupied, NotValidField, PlayerNotInGame, AllShipsAlreadyPlaced {

        if (!playingUsers.containsKey(placerUsername)) {
            throw new UserNotCurrentlyPlaying();
        }
        String gameId = playingUsers.get(placerUsername);

        return activeGames.get(gameId).placeShip(placerUsername, ship, from, to);
    }

    public static String viewBoard(String player) throws UserNotCurrentlyPlaying {
        if (!playingUsers.containsKey(player)) {
            throw new UserNotCurrentlyPlaying();
        }
        String gameId = playingUsers.get(player);
        return activeGames.get(gameId).toString(player);
    }

    private static void deleteCompletedGame(String gameId, String winner) {
        BattleshipsGame toDel = gameData.get(gameId);

        String enemyUsername = toDel.getOpponentUsername();
        usersGames.get(enemyUsername).remove(gameId);
        gameData.remove(gameId);
        usersGames.get(winner).remove(gameId);
        activeGames.remove(gameId);

        deleteFileForGame(gameId);
    }

    private static void deleteFileForGame(String gameId) {

        String filename = gameId + "_data";
        try {
            Files.delete(Paths.get(filename));
        } catch (IOException e) {
            System.out.println("Something went wrong!");
        }
    }

    private void saveActiveGameInFile(String gameId, ActiveBattleshipsGame game) {
        Saver.saveActiveGameToFile(gameId, game);
    }

    private ActiveBattleshipsGame loadActiveGameFromFile(String gameId) throws FileNotFoundException {
        return Loader.loadActiveGameFromFile(gameId);
    }
}
