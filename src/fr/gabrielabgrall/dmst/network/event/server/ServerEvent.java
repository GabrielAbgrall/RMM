package fr.gabrielabgrall.dmst.network.event.server;

import fr.gabrielabgrall.dmst.network.Server;
import fr.gabrielabgrall.dmst.network.event.NetworkEvent;

public class ServerEvent extends NetworkEvent {

    protected Server server;

    public ServerEvent(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}
