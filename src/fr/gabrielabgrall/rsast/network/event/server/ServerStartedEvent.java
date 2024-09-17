package fr.gabrielabgrall.rsast.network.event.server;

import fr.gabrielabgrall.rsast.network.Server;

public class ServerStartedEvent extends ServerEvent {
    
    public ServerStartedEvent(Server server){
        super(server);
    }
}
