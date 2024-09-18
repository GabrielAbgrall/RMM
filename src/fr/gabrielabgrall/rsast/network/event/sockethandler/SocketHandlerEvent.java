package fr.gabrielabgrall.rsast.network.event.sockethandler;

import fr.gabrielabgrall.rsast.network.SocketHandler;
import fr.gabrielabgrall.rsast.network.event.NetworkEvent;

public class SocketHandlerEvent extends NetworkEvent {
    
    protected SocketHandler socketHandler;

    public SocketHandlerEvent(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public SocketHandler getSocketHandler() {
        return socketHandler;
    }
}
