package fr.gabrielabgrall.dmst.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import fr.gabrielabgrall.dmst.network.command.Command;
import fr.gabrielabgrall.dmst.network.event.command.CommandReceivedEvent;
import fr.gabrielabgrall.dmst.network.event.socket.DisconnectionEvent;
import fr.gabrielabgrall.dmst.network.event.socket.LostConnectionEvent;
import fr.gabrielabgrall.dmst.network.event.utils.NetworkEventManager;

public class SocketHandler extends Thread {

    public final static String VERSION = "0.1";
    public static long RECONNECT_INTERVAL = 1000;
    public final static String END_STATEMENT = "\r\n";

    protected NetworkEventManager eventManager;
    protected Socket socket;

    public SocketHandler(String name, Socket socket) {
        setName(name);
        this.socket = socket;
        this.eventManager = new NetworkEventManager();
    }

    @Override
    public void run() {
        listen();
    }

    public void disconnect() {
        if(!isConnected()) return;
        closeSocket();
        eventManager.triggerEvent(new DisconnectionEvent(socket));
    }

    protected void closeSocket() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return this.socket != null && this.socket.isConnected() && !this.socket.isClosed();
    }
    
    public void listen() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int charId;
            String data = "";
            while((charId = in.read()) != -1) {
                char[] chars = Character.toChars(charId);
                for (char c : chars) {
                    data += c;
                    int l = data.length();
                    if(l>=2 && data.subSequence(l-2, l).equals("\r\n")) {
                        handleIncomingData(socket, data.substring(0, l-2));
                        data = "";
                    }
                }
            }
        } catch (IOException e) {
            closeSocket();
            eventManager.triggerEvent(new LostConnectionEvent(socket));
        }
    }

    public void handleIncomingData(Socket socket, String data) {
        Command command = new Command(data);
        handleIncomingCommand(command);
    }

    public void handleIncomingCommand(Command command) {
        eventManager.triggerEvent(new CommandReceivedEvent(this, command));
    }

    public void sendData(String data) {
        BufferedWriter out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(data + END_STATEMENT);
            out.flush();
        } catch (IOException e) {
            closeSocket();
            eventManager.triggerEvent(new LostConnectionEvent(socket));
        }
    }

    public void sendCommand(Command command) {
        sendData(command.toString());
    }

    public Socket getSocket() {
        return socket;
    }

    public NetworkEventManager getEventManager() {
        return eventManager;
    }
}
