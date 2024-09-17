package fr.gabrielabgrall.rsast.network.event.command;

import fr.gabrielabgrall.rsast.network.SocketHandler;
import fr.gabrielabgrall.rsast.network.command.Command;
import fr.gabrielabgrall.rsast.network.event.NetworkEvent;

public class CommandReceivedEvent extends NetworkEvent {

    protected Command command;
    protected SocketHandler socketHandler;

    public CommandReceivedEvent(SocketHandler socketHandler, Command command) {
        this.command = command;
        this.socketHandler = socketHandler;
    }

    public SocketHandler getSocketHandler() {
        return socketHandler;
    }
    
    public Command getCommand() {
        return command;
    }
}
