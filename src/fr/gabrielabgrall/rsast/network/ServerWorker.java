package fr.gabrielabgrall.rsast.network;

import java.net.Socket;

import fr.gabrielabgrall.rsast.network.event.socket.ConnectionEvent;

public class ServerWorker extends SocketHandler {

    protected Server server;

    public ServerWorker(String name, Socket socket, Server server) {
        super(name, socket);
        this.server = server;
        eventManager.triggerEvent(new ConnectionEvent(socket));
    }

    @Override
    public void run() {
        listen();
        server.removeServerWorker(this);
        interrupt();
    }
}
