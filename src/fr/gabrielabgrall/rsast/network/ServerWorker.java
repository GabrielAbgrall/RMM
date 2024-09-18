package fr.gabrielabgrall.rsast.network;

import java.net.Socket;

public class ServerWorker extends SocketHandler {

    protected Server server;

    public ServerWorker(String name, Socket socket, Server server) {
        super(name, socket);
        this.server = server;
    }

    @Override
    public void run() {
        listen();
        server.removeServerWorker(this);
        interrupt();
    }
}
