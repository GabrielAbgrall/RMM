package fr.gabrielabgrall.rmm.network.event.server;

import fr.gabrielabgrall.rmm.network.Server;

public class ServerStartedEvent extends ServerEvent {
    
    public ServerStartedEvent(Server server){
        super(server);
    }
}
