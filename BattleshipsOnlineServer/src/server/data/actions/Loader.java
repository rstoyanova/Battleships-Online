package server.data.actions;

import game.battleships.gameengine.ActiveBattleshipsGame;
import game.battleships.gameengine.BattleshipsGame;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.file.Files.exists;

public class Loader {
    public static Map<String, BattleshipsGame> loadServerDataFromFile(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Username is empty! ");
        }
        Map<String, BattleshipsGame> data = new ConcurrentHashMap<>();
        Path file = Path.of(filename);
        if (exists(file)) {
            try (var ois = new ObjectInputStream(Files.newInputStream(file))) {
                data = (HashMap) ois.readObject();
            } catch (EOFException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("File hasn't been found!" + e);
            } catch (IOException e) {
                throw new RuntimeException("File didn't open :" + e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Didn't find class :" + e);
            }
        }
        return data;
    }

    public static ActiveBattleshipsGame loadActiveGameFromFile(String gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("Username is empty! ");
        }
        ActiveBattleshipsGame data = null;
        String filename = gameId + "_data";
        Path file = Path.of(filename);
        if (exists(file)) {
            try (var ois = new ObjectInputStream(Files.newInputStream(file))) {
                data = (ActiveBattleshipsGame) ois.readObject();
            } catch (EOFException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException("File didn't open :" + e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Didn't find class :" + e);
            }
        }
        return data;
    }
}
