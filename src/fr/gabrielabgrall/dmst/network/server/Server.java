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
        Debug.log(this, "Server started. Listening to incoming connections...");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                handleNewClient(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void handleNewClient(Socket socket) {
        ServerWorker serverWorker = new ServerWorker("ServerWorker-" + nextServerWorkerID, socket);
        nextServerWorkerID++;
        serverWorkers.add(serverWorker);
        
        Debug.log(this, "New client connected to ", serverWorker.getName(), " from ", serverWorker.socket.getRemoteSocketAddress());
        serverWorker.start();
    }

    @Override
    public String toString() {
        return getName();
    }
}