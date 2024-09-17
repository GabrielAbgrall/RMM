package fr.gabrielabgrall.dmst.network;

import java.io.IOException;
import java.net.Socket;

import fr.gabrielabgrall.dmst.network.event.socket.ConnectionEvent;

public class Client extends SocketHandler {

    protected String host;
    protected int port;

    public Client(String name, String host, int port) {
        super(name, null);

        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            if(!isConnected()) {
                try {
                    if(connect()) continue;
                    Thread.sleep(RECONNECT_INTERVAL);
                } catch (InterruptedException e) {
                    interrupt();
                }
            } else {
                listen();
            }
        }
    }
    
    public boolean connect() {
        if(isConnected()) return true;
        try {
            this.socket = new Socket(host, port);
            eventManager.triggerEvent(new ConnectionEvent(socket));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
