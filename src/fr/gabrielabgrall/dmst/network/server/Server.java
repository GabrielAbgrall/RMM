package fr.gabrielabgrall.dmst.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.gabrielabgrall.dmst.utils.Debug;

public class Server extends Thread {

    protected final int port;
    protected final ServerSocket serverSocket;

    protected final List<ServerWorker> serverWorkers = new ArrayList<>();
    protected int nextServerWorkerID = 0;

    public Server(int port) throws IOException {
        setName("Server");
        this.port = port;
        this.serverSocket = new ServerSocket(port);


    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                handleNewClient(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void handleNewClient(Socket socket) {
        ServerWorker serverWorker = new ServerWorker("Server-" + nextServerWorkerID, socket);
        nextServerWorkerID++;
        serverWorkers.add(serverWorker);
        
        Debug.Log(this, " New client connected to ", serverWorker, " from ", serverWorker.socket.getRemoteSocketAddress());
    }
}