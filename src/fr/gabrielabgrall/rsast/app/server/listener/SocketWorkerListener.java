package fr.gabrielabgrall.rsast.app.server.listener;

import fr.gabrielabgrall.rsast.InvalidArgumentException;
import fr.gabrielabgrall.rsast.app.server.ServerApp;
import fr.gabrielabgrall.rsast.command.AuthCommand;
import fr.gabrielabgrall.rsast.network.command.Command;
import fr.gabrielabgrall.rsast.network.event.sockethandler.command.CommandReceivedEvent;
import fr.gabrielabgrall.rsast.network.event.sockethandler.socket.ConnectionEvent;
import fr.gabrielabgrall.rsast.network.event.sockethandler.socket.DisconnectionEvent;
import fr.gabrielabgrall.rsast.network.event.sockethandler.socket.LostConnectionEvent;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventHandler;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventListener;
import fr.gabrielabgrall.rsast.utils.Debug;

public class SocketWorkerListener implements NetworkEventListener {

    protected final ServerApp serverApp;
    
    public SocketWorkerListener(ServerApp serverApp) {
        this.serverApp = serverApp;
    }

    @NetworkEventHandler
    public void onConnect(ConnectionEvent e) {
        Debug.log("Connected to remote ", e.getSocket().getRemoteSocketAddress());
    }
    
    @NetworkEventHandler
    public void onDisconnect(DisconnectionEvent e) {
        Debug.log("Disconnected from remote ", e.getSocket().getRemoteSocketAddress(), " with reason: ", e.getMessage());
    }

    @NetworkEventHandler
    public void onConnectionLost(LostConnectionEvent e) {
        Debug.log("Lost connection to remote ", e.getSocket().getRemoteSocketAddress());
    }

    @NetworkEventHandler
    public void onCommand_auth(CommandReceivedEvent e) {
        if(!e.getCommand().getCommandHeader().equals("AUTH")) return;
        try {
            AuthCommand command = new AuthCommand(e.getCommand());
            if(!command.getArgs().get("password").equals("bonjour")) e.getSocketHandler().disconnect("Invalid credentials");
            if(!serverApp.checkVersion(command.getArgs().get("version"))) e.getSocketHandler().disconnect("Invalid version");
            e.getSocketHandler().setName(e.getCommand().getArgs().get("login"));
            e.getSocketHandler().sendCommand(new Command("AUTH_ACK"));
        } catch (InvalidArgumentException err) {
            e.getSocketHandler().disconnect(err.getMessage());
        }
    }
}
