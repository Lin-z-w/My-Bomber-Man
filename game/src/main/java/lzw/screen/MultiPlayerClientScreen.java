package lzw.screen;

import lzw.asciiPanel.AsciiPanel;
import lzw.networkio.ObjectRW;
import lzw.world.Creature;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Random;

public class MultiPlayerClientScreen implements Screen {

    MultiPlayerServer multiPlayerServer;

    int id;

    MultiPlayerClientScreen() throws IOException {
        Random random = new Random();
        id = 0;
        multiPlayerServer = null;
        sendMsgToServer("new client");
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        multiPlayerServer.displayOutput(terminal);
    }
    @Override
    public Screen update5hms() throws IOException {
        return this;
    }

    @Override
    public Screen update1hms() throws IOException, AWTException {
        sendMsgToServer("request");
        if (multiPlayerServer != null) {
            Creature player = multiPlayerServer.getPlayer(id);
            if (player != null)
                if (!player.isAlive())
                    return new LoseScreen();
                else if (multiPlayerServer.win()) {
                    return new WinScreen();
                }
        }
        return this;
    }

    private void sendMsgToServer(String msg) throws IOException {
        msg = msg + " " + id;
        System.out.println("client send: " + msg);
        Socket clientSocket = new Socket("localhost", 7070);
        // send
//        BufferedWriter inToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//        inToServer.write(msg);
//        inToServer.flush();
        ByteBuffer request = ByteBuffer.wrap(msg.getBytes());
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
        output.write(request.array());
        // read
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        CharBuffer charBuffer = CharBuffer.allocate(20000);
        int numRead = inFromServer.read(charBuffer);
        System.out.println(numRead);
        charBuffer.flip();
        String s = charBuffer.toString();
        System.out.println(s.length());
        Object multiPlayerScreen = ObjectRW.readObject(s);
        assert multiPlayerScreen instanceof MultiPlayerServer;
        this.multiPlayerServer = (MultiPlayerServer) multiPlayerScreen;
        if (this.id == 0) {
            this.id = ((MultiPlayerServer) multiPlayerScreen).newID;
        }
        clientSocket.close();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException, URISyntaxException {
        sendMsgToServer(String.valueOf(key.getKeyCode()));
        return this;
    }
}
