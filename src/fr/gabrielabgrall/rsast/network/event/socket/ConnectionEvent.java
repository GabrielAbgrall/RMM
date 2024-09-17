package fr.gabrielabgrall.rsast.network.event.socket;

import java.net.Socket;

public class ConnectionEvent extends SocketEvent {

    public ConnectionEvent(Socket socket) {
        super(socket);
    }
    
}
