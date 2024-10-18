package fr.gabrielabgrall.rmm.network.event.sockethandler.socket;

import java.net.Socket;

import fr.gabrielabgrall.rmm.network.SocketHandler;
import fr.gabrielabgrall.rmm.network.event.sockethandler.SocketHandlerEvent;

public class SocketEvent extends SocketHandlerEvent {

    protected Socket socket;

    public SocketEvent(SocketHandler socketHandler) {
        super(socketHandler);
        this.socket = socketHandler.getSocket();
    }
    
    public Socket getSocket() {
        return socket;
    }
}
