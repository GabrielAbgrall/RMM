package fr.gabrielabgrall.dmst.network;

public class ConnectionException extends Exception {
    
    public ConnectionException() {
        super("Connection attempt. Socket already connected, try disconnecting before connecting to a new host.");
    }
}
