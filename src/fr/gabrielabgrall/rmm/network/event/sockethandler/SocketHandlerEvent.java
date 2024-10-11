package fr.gabrielabgrall.rmm.network.event.sockethandler;

import fr.gabrielabgrall.rmm.network.SocketHandler;
import fr.gabrielabgrall.rmm.network.event.NetworkEvent;

public class SocketHandlerEvent extends NetworkEvent {
    
    protected SocketHandler socketHandler;

    public SocketHandlerEvent(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public SocketHandler getSocketHandler() {
        return socketHandler;
    }
}
