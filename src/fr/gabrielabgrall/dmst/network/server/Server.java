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
        Debug.log("Server started. Listening to incoming connections...");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                handleNewClient(serverSocket.accept());
            } catch (IOException e) {
                Debug.log((Object) e.getStackTrace());
            }
        }
    }

    protected void handleNewClient(Socket socket) {
        ServerWorker serverWorker;
        try {
            serverWorker = new ServerWorker("ServerWorker-" + nextServerWorkerID, socket);
            nextServerWorkerID++;
            serverWorkers.add(serverWorker);
            
            Debug.log("New client connected to ", serverWorker.getName(), " from ", serverWorker.getSocket().getRemoteSocketAddress());
            serverWorker.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}