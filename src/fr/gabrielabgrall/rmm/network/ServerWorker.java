package fr.gabrielabgrall.rmm.network;

import java.net.Socket;

import fr.gabrielabgrall.rmm.network.event.sockethandler.socket.ConnectionEvent;

public class ServerWorker extends SocketHandler {

    protected Server server;

    public ServerWorker(String name, Socket socket, Server server) {
        super(name, socket);
        this.server = server;
    }

    @Override
    public void run() {
        eventManager.triggerEvent(new ConnectionEvent(this));
        listen();
        server.removeServerWorker(this);
        interrupt();
    }
}
