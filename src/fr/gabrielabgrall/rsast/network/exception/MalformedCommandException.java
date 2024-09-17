package fr.gabrielabgrall.rsast.network.exception;

public class MalformedCommandException extends NetworkException {

    public MalformedCommandException() {
        super("Incomplete or incorrect command header.");
    }
    
}
