package fr.gabrielabgrall.rmm.network.event.sockethandler.socket;

import fr.gabrielabgrall.rmm.network.SocketHandler;

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
