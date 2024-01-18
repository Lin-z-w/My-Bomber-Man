package lzw.screen;

import lzw.networkio.AcceptEventHandler;
import lzw.networkio.Reactor;
import lzw.networkio.ReadEventHandler;
import lzw.networkio.WriteEventHandler;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

public class ServerReactorThread implements Runnable {
    MultiPlayerServer multiPlayerServer;
    private static final int SERVER_PORT = 7070;

    public ServerReactorThread(MultiPlayerServer multiPlayerServer) {
        this.multiPlayerServer = multiPlayerServer;
    }

    public void startReactor(int port) throws Exception {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(port));
        server.configureBlocking(false);
        Reactor reactor = new Reactor(multiPlayerServer);
        reactor.registerChannel(SelectionKey.OP_ACCEPT, server);
        reactor.registerEventHandler(SelectionKey.OP_ACCEPT, new AcceptEventHandler(reactor.getSelector()));
        reactor.registerEventHandler(SelectionKey.OP_READ, new ReadEventHandler(reactor.getSelector(), multiPlayerServer));
        reactor.registerEventHandler(SelectionKey.OP_WRITE, new WriteEventHandler(multiPlayerServer));
        reactor.run(); // Run the dispatcher loop
    }

    @Override
    public void run() {
        try {
            startReactor(SERVER_PORT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
