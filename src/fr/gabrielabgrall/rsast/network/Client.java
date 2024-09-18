package fr.gabrielabgrall.rsast.network;

import java.io.IOException;
import java.net.Socket;

import fr.gabrielabgrall.rsast.network.event.sockethandler.socket.ConnectionEvent;

public class Client extends SocketHandler {

    protected String host;
    protected int port;
    protected boolean tryReconnect = true;

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
                    if(tryReconnect && connect()) continue;
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
        tryReconnect = true;
        if(isConnected()) return true;
        try {
            this.socket = new Socket(host, port);
            eventManager.triggerEvent(new ConnectionEvent(this));
            setConnected(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void disconnect() {
        tryReconnect = false;
        super.disconnect();
    }
}
