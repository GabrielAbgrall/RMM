package fr.gabrielabgrall.dmst.network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.gabrielabgrall.dmst.network.SocketHandler;
import fr.gabrielabgrall.dmst.utils.Debug;

public class Client extends SocketHandler {

    public Client(String name, String host, int port) throws UnknownHostException, IOException {
        super(name, new Socket(host, port));

        Debug.log("Client connected to ", host, ":", port);
    }
}