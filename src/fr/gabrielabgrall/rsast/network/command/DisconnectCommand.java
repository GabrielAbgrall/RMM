package fr.gabrielabgrall.rsast.network.command;

public class DisconnectCommand extends Command {
    
    public DisconnectCommand(String message) {
        super("!DISCONNECT", "message " + message);
    }
}
