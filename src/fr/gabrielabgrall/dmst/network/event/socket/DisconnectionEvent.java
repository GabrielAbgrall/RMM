package fr.gabrielabgrall.dmst.network.event.socket;

import java.net.Socket;

public class DisconnectionEvent extends SocketEvent {

    public DisconnectionEvent(Socket socket) {
        super(socket);
    }
    
}
