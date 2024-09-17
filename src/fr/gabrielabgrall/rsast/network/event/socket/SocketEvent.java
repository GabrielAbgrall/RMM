package fr.gabrielabgrall.rsast.network.event.socket;

import java.net.Socket;

import fr.gabrielabgrall.rsast.network.event.NetworkEvent;

public class SocketEvent extends NetworkEvent {

    protected final Socket socket;

    public SocketEvent(Socket socket) {
        this.socket = socket;
    }
    
    public Socket getSocket() {
        return socket;
    }
}
