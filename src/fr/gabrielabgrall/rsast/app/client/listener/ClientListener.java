package fr.gabrielabgrall.rsast.app.client.listener;

import fr.gabrielabgrall.rsast.app.client.ClientApp;
import fr.gabrielabgrall.rsast.network.event.sockethandler.command.CommandReceivedEvent;
import fr.gabrielabgrall.rsast.network.event.sockethandler.socket.ConnectionEvent;
import fr.gabrielabgrall.rsast.network.event.sockethandler.socket.DisconnectionEvent;
import fr.gabrielabgrall.rsast.network.event.sockethandler.socket.LostConnectionEvent;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventHandler;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventListener;
import fr.gabrielabgrall.rsast.utils.Debug;

public class ClientListener implements NetworkEventListener {

    protected ClientApp clientApp;

    public ClientListener(ClientApp clientApp) {
        this.clientApp = clientApp;
    }

    @NetworkEventHandler
    public void onConnect(ConnectionEvent e) {
        Debug.log("Connected to remote ", e.getSocket().getRemoteSocketAddress());
    }
    
    @NetworkEventHandler
    public void onDisconnect(DisconnectionEvent e) {
        Debug.log("Disconnected from remote ", e.getSocket().getRemoteSocketAddress(), " with reason: ", e.getMessage());
        clientApp.getClient().setTryReconnect(false);
    }

    @NetworkEventHandler
    public void onConnectionLost(LostConnectionEvent e) {
        Debug.log("Lost connection to remote ", e.getSocket().getRemoteSocketAddress());
    }

    @NetworkEventHandler
    public void onCommand_auth_ack(CommandReceivedEvent e) {
        if(!e.getCommand().getCommandHeader().equals("AUTH_ACK")) return;
        clientApp.setAuthenticated(true);
    }
}
