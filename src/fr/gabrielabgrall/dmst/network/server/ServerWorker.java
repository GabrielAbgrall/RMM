package fr.gabrielabgrall.dmst.network.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.gabrielabgrall.dmst.network.SocketHandler;

public class ServerWorker extends SocketHandler {


    ServerWorker(String name, Socket socket) throws UnknownHostException, IOException {
        super(name, socket);
    }
}