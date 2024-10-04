package fr.gabrielabgrall.rsast.network.event.sockethandler.socket;

import fr.gabrielabgrall.rsast.network.SocketHandler;

public class DisconnectionEvent extends SocketEvent {

    protected String message;

    public DisconnectionEvent(SocketHandler socketHandler, String message) {
        super(socketHandler);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
