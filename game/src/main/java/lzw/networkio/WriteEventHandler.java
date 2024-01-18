package lzw.networkio;

import lzw.screen.MultiPlayerServer;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class WriteEventHandler implements EventHandler {

    MultiPlayerServer multiPlayerServer;

    public WriteEventHandler(MultiPlayerServer multiPlayerServer) {
        this.multiPlayerServer = multiPlayerServer;
    }
    @Override
    public void handleEvent(SelectionKey handle) throws Exception {
        System.out.println("==Write handler==");
        // get channel
        SocketChannel socketChannel = (SocketChannel) handle.channel();
        String objectString = ObjectRW.writeObject(multiPlayerServer);
        assert objectString != null;
        ByteBuffer buffer = ByteBuffer.wrap(objectString.getBytes());
        // while (buffer.hasRemaining()) {
        // }
        System.out.println(socketChannel.write(buffer));
        socketChannel.close();
    }
}
