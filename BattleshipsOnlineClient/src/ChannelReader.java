
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChannelReader implements Runnable {
    public static final String READ_CHANNEL_ERR =
            "Something went wrong when trying to read from channel";
    private static final int BUFFER_CAPACITY = 1024;
    private final SocketChannel socketChannel;
    private final Thread writeThread;

    public ChannelReader(SocketChannel socketChannel,
                         Thread writeThread) {
        this.socketChannel = socketChannel;
        this.writeThread = writeThread;
    }

    @Override
    public void run() {
        ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        while (writeThread.isAlive()) {
            try {
                readBuffer.clear();
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes != 0) {
                    readBuffer.flip();
                    String receivedMessage =
                            new String(readBuffer.array(), 0, readBuffer.limit());
                    System.out.println(receivedMessage);
                }
            } catch (IOException e) {
                System.out.println(READ_CHANNEL_ERR);
                e.printStackTrace();
            }
        }
    }
}