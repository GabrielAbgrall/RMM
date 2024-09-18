package fr.gabrielabgrall.rsast.network.event.sockethandler.socket;

import fr.gabrielabgrall.rsast.network.SocketHandler;

public class LostConnectionEvent extends SocketEvent {

    public LostConnectionEvent(SocketHandler socketHandler) {
        super(socketHandler);
    }
    
}
