package fr.gabrielabgrall.rmm.app.server.listener;

import fr.gabrielabgrall.rmm.app.server.ServerApp;
import fr.gabrielabgrall.rmm.network.event.server.IncomingClientEvent;
import fr.gabrielabgrall.rmm.network.event.server.ServerStartedEvent;
import fr.gabrielabgrall.rmm.network.event.utils.NetworkEventHandler;
import fr.gabrielabgrall.rmm.network.event.utils.NetworkEventListener;
import fr.gabrielabgrall.rmm.utils.Debug;

public class ServerListener implements NetworkEventListener {

    protected ServerApp serverApp;

    public ServerListener(ServerApp serverApp) {
        this.serverApp = serverApp;
    }

    @NetworkEventHandler
    public void onIncomingClient(IncomingClientEvent e) {
        e.getServerWorker().getEventManager().registerListener(new ServerWorkerListener(serverApp));
        Debug.log("New client connected on ", e.getServerWorker().getName(), " from ", e.getServerWorker().getSocket().getRemoteSocketAddress());
    }

    @NetworkEventHandler
    public void onServerStarted(ServerStartedEvent e) {
        Debug.log("Server started, listening to new connections on port ", e.getServer().getServerSocket().getLocalPort());
    }
}
