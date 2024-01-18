package lzw.networkio;

import lzw.screen.MultiPlayerServer;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Reactor {
    MultiPlayerServer multiPlayerServer;
    private Map<Integer, EventHandler> registeredHandlers = new ConcurrentHashMap<>();

    private Selector selector;

    public Reactor(MultiPlayerServer multiPlayerServer) throws IOException {
        this.multiPlayerServer = multiPlayerServer;
        selector = Selector.open();
    }

    public Selector getSelector() {
        return selector;
    }

    public void registerEventHandler(int evenType, EventHandler eventHandler) {
        registeredHandlers.put(evenType, eventHandler);
    }

    public void registerChannel(int eventType, SelectableChannel channel) throws ClosedChannelException {
        channel.register(selector, eventType);
    }

    public void run() {
        try {
            // Loop indefinitely
            while (true) {
                selector.select();
                Set<SelectionKey> readyHandles = selector.selectedKeys();
                Iterator<SelectionKey> handleIterator = readyHandles.iterator();
                while (handleIterator.hasNext()) {
                    SelectionKey handle = handleIterator.next();
                    if (handle.isAcceptable()) {
                        EventHandler handler = registeredHandlers.get(SelectionKey.OP_ACCEPT);
                        handler.handleEvent(handle);
                    }
                    if (handle.isReadable()) {
                        EventHandler handler = registeredHandlers.get(SelectionKey.OP_READ);
                        handler.handleEvent(handle);
                        handleIterator.remove();
                    }
                    if (handle.isWritable()) {
                        EventHandler handler = registeredHandlers.get(SelectionKey.OP_WRITE);
                        handler.handleEvent(handle);
                        handleIterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
