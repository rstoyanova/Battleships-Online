
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChannelWriter implements Runnable {
    private static final String WRITE_CHANNEL_ERR =
            "Something went wrong while trying to write to channel";
    private static final int BUFFER_CAPACITY = 1024;
    private static final String DISCONNECT_MESSAGE = "Disconnecting from server...";
    private static final String FLAG_USERNAME = "--username";
    private final SocketChannel socketChannel;
    private final Scanner scanner;
    private final String username;

    public ChannelWriter(SocketChannel socketChannel, Scanner scanner, String username) {
        this.socketChannel = socketChannel;
        this.scanner = scanner;
        this.username = username;
    }

    @Override
    public void run() {
        ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        String line;
        final String CMD = FLAG_USERNAME + " " + username;

        writeBuffer.clear();
        writeBuffer.put((CMD).getBytes());
        writeBuffer.flip();
        try {
            socketChannel.write(writeBuffer);
        } catch (IOException e) {
            throw new RuntimeException(WRITE_CHANNEL_ERR, e);
        }
        while (socketChannel.isOpen()) {
            line = scanner.nextLine();

            writeBuffer.clear();
            writeBuffer.put(line.getBytes());
            writeBuffer.flip();
            try {
                socketChannel.write(writeBuffer);
                if (line.equals("disconnect")) {
                    System.out.println(DISCONNECT_MESSAGE);
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(WRITE_CHANNEL_ERR, e);
            }
        }
    }
}
