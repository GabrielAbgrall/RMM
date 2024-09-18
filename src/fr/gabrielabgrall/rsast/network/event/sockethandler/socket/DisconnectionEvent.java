package fr.gabrielabgrall.rsast.network.event.sockethandler.socket;

import fr.gabrielabgrall.rsast.network.SocketHandler;

public class DisconnectionEvent extends SocketEvent {

    public DisconnectionEvent(SocketHandler socketHandler) {
        super(socketHandler);
    }
    
}
