package fr.gabrielabgrall.dmst.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.gabrielabgrall.dmst.network.event.server.IncomingClientEvent;
import fr.gabrielabgrall.dmst.network.event.server.ServerStartedEvent;
import fr.gabrielabgrall.dmst.network.event.utils.NetworkEventManager;

public class Server extends Thread {

    public static final String SERVER_WORKER_IDENTIFIER = "-SW-"; 
    protected final int port;
    protected final ServerSocket serverSocket;

    protected final List<SocketHandler> serverWorkers = new ArrayList<>();
    protected int nextServerWorkerID = 0;
    protected NetworkEventManager eventManager = new NetworkEventManager();

    public Server(String name, int port) throws IOException {
        setName(name);
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        eventManager.triggerEvent(new ServerStartedEvent(this));
        while (!Thread.currentThread().isInterrupted()) {
            try {
                handleNewClient(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void handleNewClient(Socket socket) {
        ServerWorker serverWorker = new ServerWorker(getName() + SERVER_WORKER_IDENTIFIER + nextServerWorkerID, socket, this);
        nextServerWorkerID++;

        IncomingClientEvent e = new IncomingClientEvent(this, serverWorker);
        eventManager.triggerEvent(e);

        if(!e.isCancelled()) {
            serverWorkers.add(serverWorker);
            serverWorker.start();
        } else {
            serverWorker.disconnect();
        }
    }

    public List<SocketHandler> getServerWorkers() {
        return serverWorkers;
    }

    public void removeServerWorker(ServerWorker serverWorker) {
        this.serverWorkers.remove(serverWorker);
    }

    public NetworkEventManager getEventManager() {
        return eventManager;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}