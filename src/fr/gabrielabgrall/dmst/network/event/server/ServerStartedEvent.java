package fr.gabrielabgrall.dmst.network.event.server;

import fr.gabrielabgrall.dmst.network.Server;

public class ServerStartedEvent extends ServerEvent {
    
    public ServerStartedEvent(Server server){
        super(server);
    }
}
