package fr.gabrielabgrall.rmm.network.command;

public class DisconnectCommand extends Command {
    
    public DisconnectCommand(String message) {
        super("!DISCONNECT", "message " + message);
    }
}
