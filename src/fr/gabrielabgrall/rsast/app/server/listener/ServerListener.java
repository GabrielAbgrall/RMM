package fr.gabrielabgrall.rsast.app.server.listener;

import fr.gabrielabgrall.rsast.app.server.ServerApp;
import fr.gabrielabgrall.rsast.network.event.server.IncomingClientEvent;
import fr.gabrielabgrall.rsast.network.event.server.ServerStartedEvent;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventHandler;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventListener;
import fr.gabrielabgrall.rsast.utils.Debug;

public class ServerListener implements NetworkEventListener {

    protected ServerApp serverApp;

    public ServerListener(ServerApp serverApp) {
        this.serverApp = serverApp;
    }

    @NetworkEventHandler
    public void onIncomingClient(IncomingClientEvent e) {
        e.getServerWorker().getEventManager().registerListener(new SocketWorkerListener(serverApp));
        Debug.log("New client connected on ", e.getServerWorker().getName(), " from ", e.getServerWorker().getSocket().getRemoteSocketAddress());
    }

    @NetworkEventHandler
    public void onServerStarted(ServerStartedEvent e) {
        Debug.log("Server started, listening to new connections on port ", e.getServer().getServerSocket().getLocalPort());
    }
}
