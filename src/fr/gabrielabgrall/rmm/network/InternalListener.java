package fr.gabrielabgrall.rmm.network;

import fr.gabrielabgrall.rmm.network.command.Command;
import fr.gabrielabgrall.rmm.network.event.sockethandler.command.CommandReceivedEvent;
import fr.gabrielabgrall.rmm.network.event.sockethandler.socket.DisconnectionEvent;
import fr.gabrielabgrall.rmm.network.event.utils.NetworkEventHandler;
import fr.gabrielabgrall.rmm.network.event.utils.NetworkEventListener;

public class InternalListener implements NetworkEventListener {

    @NetworkEventHandler
    public void onCommandReceived(CommandReceivedEvent e) {
        if(!e.getCommand().getCommandHeader().startsWith(Command.INTERNAL_COMMAND_PREFIX)) return;

        switch (e.getCommand().getCommandHeader()) {
            case "!DISCONNECT":
                e.getSocketHandler().closeSocket();
                e.getSocketHandler().getEventManager().triggerEvent(new DisconnectionEvent(e.getSocketHandler(), e.getCommand().getArgs().get("message")));
                break;
        
            default:
                break;
        }
    }
}
