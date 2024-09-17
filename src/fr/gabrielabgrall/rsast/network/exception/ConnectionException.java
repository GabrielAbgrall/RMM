package fr.gabrielabgrall.rsast.network.exception;

public class ConnectionException extends NetworkException {
    
    public ConnectionException() {
        super("Connection attempt. Socket already connected, try disconnecting before connecting to a new host.");
    }
}
