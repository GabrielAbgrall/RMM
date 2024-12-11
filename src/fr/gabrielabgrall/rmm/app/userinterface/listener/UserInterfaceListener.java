package fr.gabrielabgrall.rmm.app.userinterface.listener;

import fr.gabrielabgrall.rmm.InvalidArgumentException;
import fr.gabrielabgrall.rmm.app.userinterface.UserInterface;
import fr.gabrielabgrall.rmm.command.ClientDataCommand;
import fr.gabrielabgrall.rmm.command.ClientListCommand;
import fr.gabrielabgrall.rmm.network.event.sockethandler.command.CommandReceivedEvent;
import fr.gabrielabgrall.rmm.network.event.sockethandler.socket.DisconnectionEvent;
import fr.gabrielabgrall.rmm.network.event.utils.NetworkEventHandler;
import fr.gabrielabgrall.rmm.network.event.utils.NetworkEventListener;
import fr.gabrielabgrall.rmm.utils.Debug;

public class UserInterfaceListener implements NetworkEventListener {

    protected final UserInterface ui;

    public UserInterfaceListener(UserInterface ui) {
        this.ui = ui;
    }

    @NetworkEventHandler
    public void onDisconnect(DisconnectionEvent e) {
        ui.displayError("Disconnected with reason: " + e.getMessage());
    }

    @NetworkEventHandler
    public void onCommand_ClientData(CommandReceivedEvent e) {
        if(!e.getCommand().getCommandHeader().equals("CLIENT_DATA")) return;

        try {
            ClientDataCommand command = new ClientDataCommand(e.getCommand());
            ui.registerCpuLoad(command.getTime(), command.getCpuLoad());
            ui.refreshGraph();
        } catch (InvalidArgumentException err) {
            err.printStackTrace();
        }
    }

    @NetworkEventHandler
    public void onCommand_ClientList(CommandReceivedEvent e) {
        if(!e.getCommand().getCommandHeader().equals("CLIENT_LIST")) return;

        try {
            ClientListCommand command = new ClientListCommand(e.getCommand());
            if(command.getArgs().get("clients").equals("null")) ui.displayConnectedClients(new String[0]);
            else ui.displayConnectedClients(command.getClients());
        } catch (InvalidArgumentException err) {
            err.printStackTrace();
        }
    }

    @NetworkEventHandler
    public void onCommandReceived(CommandReceivedEvent e) {
        Debug.log("Command received: ", e.getCommand());
    }
}
