package server.data.actions;

import game.battleships.gameengine.ActiveBattleshipsGame;
import game.battleships.gameengine.BattleshipsGame;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Saver {
    public static void saveServerDataToFile(String filename, Map<String, BattleshipsGame> data) {
        Path file = Path.of(filename);
        try (var oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("Could not find file " + filename);
        }
    }

    public static void saveActiveGameToFile(String gameId, ActiveBattleshipsGame data) {
        String filename = gameId + "_data";
        Path file = Path.of(filename);
        try (var oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("Could not find file " + filename);
        }
    }
}
