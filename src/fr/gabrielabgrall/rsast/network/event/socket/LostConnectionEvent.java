package fr.gabrielabgrall.rsast.network.event.socket;

import java.net.Socket;

public class LostConnectionEvent extends SocketEvent {

    public LostConnectionEvent(Socket socket) {
        super(socket);
    }
    
}