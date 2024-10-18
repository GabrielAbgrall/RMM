package fr.gabrielabgrall.rmm.network.event.sockethandler.command;

import fr.gabrielabgrall.rmm.network.SocketHandler;
import fr.gabrielabgrall.rmm.network.command.Command;
import fr.gabrielabgrall.rmm.network.event.sockethandler.SocketHandlerEvent;

public class CommandReceivedEvent extends SocketHandlerEvent {

    protected Command command;

    public CommandReceivedEvent(SocketHandler socketHandler, Command command) {
        super(socketHandler);
        this.command = command;
    }
    
    public Command getCommand() {
        return command;
    }
}
