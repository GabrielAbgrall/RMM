package fr.gabrielabgrall.rsast.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import fr.gabrielabgrall.rsast.network.command.Command;
import fr.gabrielabgrall.rsast.network.event.sockethandler.command.CommandReceivedEvent;
import fr.gabrielabgrall.rsast.network.event.sockethandler.socket.DisconnectionEvent;
import fr.gabrielabgrall.rsast.network.event.sockethandler.socket.LostConnectionEvent;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventManager;

public class SocketHandler extends Thread {

    public final static String VERSION = "0.1";
    public static long RECONNECT_INTERVAL = 1000;
    public final static String END_STATEMENT = "\r\n";

    protected NetworkEventManager eventManager;
    protected Socket socket;
    protected boolean connected;

    public SocketHandler(String name, Socket socket) {
        setName(name);
        this.socket = socket;
        this.eventManager = new NetworkEventManager();
        this.connected = socket != null && socket.isConnected() && !socket.isClosed();
        
        eventManager.registerInternalListener(new InternalListener());
    }

    @Override
    public void run() {
        listen();
    }

    public void disconnect() {
        if(!isConnected()) return;
        sendCommand(new Command("!DISCONNECT"));
        closeSocket();
        eventManager.triggerEvent(new DisconnectionEvent(this));
    }

    protected void closeSocket() {
        setConnected(false);
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }

    protected void setConnected(boolean connected) {
        this.connected = connected;
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
            lostConnection();
        }
    }

    public void handleIncomingData(Socket socket, String data) {
        handleIncomingCommand(new Command(data));
    }

    public void handleIncomingCommand(Command command) {
        CommandReceivedEvent e = new CommandReceivedEvent(this, command);
        if(command.getCommandHeader().startsWith(Command.INTERNAL_COMMAND_PREFIX)) {
            eventManager.triggerInternalEvent(e);
        } else {
            eventManager.triggerEvent(e);
        }
    }

    public void sendData(String data) {
        BufferedWriter out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(data + END_STATEMENT);
            out.flush();
        } catch (IOException e) {
            lostConnection();
        }
    }

    protected void lostConnection() {
        if(isConnected()) {
            eventManager.triggerEvent(new LostConnectionEvent(this));
            setConnected(false);
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
