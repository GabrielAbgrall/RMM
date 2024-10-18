package fr.gabrielabgrall.rmm.network.event.sockethandler.socket;

import fr.gabrielabgrall.rmm.network.SocketHandler;

public class LostConnectionEvent extends SocketEvent {

    public LostConnectionEvent(SocketHandler socketHandler) {
        super(socketHandler);
    }
    
}
