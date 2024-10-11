package fr.gabrielabgrall.rmm.network.event.server;

import fr.gabrielabgrall.rmm.network.Server;
import fr.gabrielabgrall.rmm.network.event.NetworkEvent;

public class ServerEvent extends NetworkEvent {

    protected Server server;

    public ServerEvent(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}
