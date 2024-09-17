package fr.gabrielabgrall.dmst.network.exception;

public class MalformedCommandException extends NetworkException {

    public MalformedCommandException() {
        super("Incomplete or incorrect command header.");
    }
    
}
