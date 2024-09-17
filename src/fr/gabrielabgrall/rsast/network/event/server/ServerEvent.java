package fr.gabrielabgrall.rsast.network.event.server;

import fr.gabrielabgrall.rsast.network.Server;
import fr.gabrielabgrall.rsast.network.event.NetworkEvent;

public class ServerEvent extends NetworkEvent {

    protected Server server;

    public ServerEvent(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}
