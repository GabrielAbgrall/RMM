package fr.gabrielabgrall.rsast.network.command;

import java.util.Base64;

public class DisconnectCommand extends Command {
    
    public DisconnectCommand(String message) {
        super("!DISCONNECT", "message " + Base64.getEncoder().encodeToString(message.getBytes()));
    }
}
