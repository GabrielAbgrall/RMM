package fr.gabrielabgrall.dmst.network.exception;

public class ConnectionException extends NetworkException {
    
    public ConnectionException() {
        super("Connection attempt. Socket already connected, try disconnecting before connecting to a new host.");
    }
}
