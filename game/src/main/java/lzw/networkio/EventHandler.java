package lzw.networkio;

import java.nio.channels.SelectionKey;

public interface EventHandler {
    void handleEvent(SelectionKey handle) throws Exception;
}
