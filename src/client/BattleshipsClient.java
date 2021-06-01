package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class BattleshipsClient {

    private static final int SERVER_PORT = 6666;
    private static final String SERVER_HOST =
            "localhost";
    private static final String SERVER_CONNECTION_ERROR =
            "Could not connect to the server";
    private static final String CONNECTED_MESSAGE =
            "Connected to the server";
    private static final String FLAG_USERNAME = "--username";

    public static void main(String[] args) {
        /*if (args.length != 2 || !args[0].equals(FLAG_USERNAME)) {
            throw new IllegalArgumentException();
        }

        // not ready :(
        */
        final String USERNAME = "reni";
        new BattleshipsClient().run(USERNAME);
    }

    public void run(String username) {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            socketChannel.configureBlocking(false);
            System.out.println(CONNECTED_MESSAGE);

            Thread write = new Thread(
                    new ChannelWriter(socketChannel, scanner, username));
            Thread read = new Thread(
                    new ChannelReader(socketChannel, write));
            write.start();
            read.start();
            write.join();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
