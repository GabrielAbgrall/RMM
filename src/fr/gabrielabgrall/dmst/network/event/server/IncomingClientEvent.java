package fr.gabrielabgrall.dmst.network.event.server;

import fr.gabrielabgrall.dmst.network.Server;
import fr.gabrielabgrall.dmst.network.ServerWorker;
import fr.gabrielabgrall.dmst.network.event.CancellableNetworkEvent;

public class IncomingClientEvent extends ServerEvent implements CancellableNetworkEvent{

    protected final ServerWorker serverWorker;
    protected boolean cancelled = false;

    public IncomingClientEvent(Server server, ServerWorker serverWorker) {
        super(server);
        this.serverWorker = serverWorker;
    }

    public ServerWorker getServerWorker() {
        return serverWorker;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
