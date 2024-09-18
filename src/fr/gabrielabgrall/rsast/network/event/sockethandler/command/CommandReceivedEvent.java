package fr.gabrielabgrall.rsast.network.event.sockethandler.command;

import fr.gabrielabgrall.rsast.network.SocketHandler;
import fr.gabrielabgrall.rsast.network.command.Command;
import fr.gabrielabgrall.rsast.network.event.sockethandler.SocketHandlerEvent;

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
