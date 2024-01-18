package lzw.networkio;

import lzw.screen.MultiPlayerServer;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ReadEventHandler implements EventHandler {

    private Selector selector;

    private ByteBuffer inputBuffer = ByteBuffer.allocate(2048);

    MultiPlayerServer multiPlayerServer;

    public ReadEventHandler(Selector selector, MultiPlayerServer multiPlayerServer) {
        this.selector = selector;
        this.multiPlayerServer = multiPlayerServer;
    }
    @Override
    public void handleEvent(SelectionKey handle) throws Exception {
        System.out.println("==Read handler==");
        // get channel
        SocketChannel socketChannel = (SocketChannel) handle.channel();
        // read channel
        socketChannel.read(inputBuffer);
        inputBuffer.flip();
        byte[] buffer = new byte[inputBuffer.limit()];
        inputBuffer.get(buffer);
        String clientInput = new String(buffer);
        System.out.println("Server Read: "+clientInput);
        if (clientInput.contains("new")) {
//            String id = clientInput.substring(4);
//            System.out.println(id);
            multiPlayerServer.newClient();
        }
        else {
            multiPlayerServer.respondToClientInput(clientInput);
        }
        inputBuffer.flip();
        socketChannel.register(selector, SelectionKey.OP_WRITE, inputBuffer);
    }
}
