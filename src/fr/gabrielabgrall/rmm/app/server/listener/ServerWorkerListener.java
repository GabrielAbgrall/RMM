package fr.gabrielabgrall.rmm.app.server.listener;

import fr.gabrielabgrall.rmm.InvalidArgumentException;
import fr.gabrielabgrall.rmm.app.server.AuthenticatedWorker;
import fr.gabrielabgrall.rmm.app.server.ServerApp;
import fr.gabrielabgrall.rmm.command.AuthCommand;
import fr.gabrielabgrall.rmm.command.ClientDataCommand;
import fr.gabrielabgrall.rmm.command.ClientListCommand;
import fr.gabrielabgrall.rmm.command.SubscribeCommand;
import fr.gabrielabgrall.rmm.network.ServerWorker;
import fr.gabrielabgrall.rmm.network.SocketHandler;
import fr.gabrielabgrall.rmm.network.command.Command;
import fr.gabrielabgrall.rmm.network.event.sockethandler.command.CommandReceivedEvent;
import fr.gabrielabgrall.rmm.network.event.sockethandler.socket.ConnectionEvent;
import fr.gabrielabgrall.rmm.network.event.sockethandler.socket.DisconnectionEvent;
import fr.gabrielabgrall.rmm.network.event.sockethandler.socket.LostConnectionEvent;
import fr.gabrielabgrall.rmm.network.event.utils.NetworkEventHandler;
import fr.gabrielabgrall.rmm.network.event.utils.NetworkEventListener;
import fr.gabrielabgrall.rmm.utils.Debug;

public class ServerWorkerListener implements NetworkEventListener {

    protected final ServerApp serverApp;
    protected AuthenticatedWorker authenticatedWorker;

    public ServerWorkerListener(ServerApp serverApp) {
        this.serverApp = serverApp;
    }

    @NetworkEventHandler
    public void onConnect(ConnectionEvent e) {
        Debug.log("Connected to remote ", e.getSocket().getRemoteSocketAddress());
    }
    
    @NetworkEventHandler
    public void onDisconnect(DisconnectionEvent e) {
        Debug.log("Disconnected from remote ", e.getSocket().getRemoteSocketAddress(), " with reason: ", e.getMessage());
        if(authenticatedWorker != null) serverApp.removeAuthenticatedWorker(authenticatedWorker);
    }

    @NetworkEventHandler
    public void onConnectionLost(LostConnectionEvent e) {
        Debug.log("Lost connection to remote ", e.getSocket().getRemoteSocketAddress());
        if(authenticatedWorker != null) serverApp.removeAuthenticatedWorker(authenticatedWorker);
    }

    @NetworkEventHandler
    public void onCommand_auth(CommandReceivedEvent e) {
        if(!e.getCommand().getCommandHeader().equals("AUTH")) return;
        try {
            AuthCommand command = new AuthCommand(e.getCommand());
            if(!serverApp.checkVersion(command.getArgs().get("version"))) {
                e.getSocketHandler().disconnect("Invalid version");
                return;
            }
            
            if(command.getLogin().equals("UserInterface")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SocketHandler sh = e.getSocketHandler();
                        while (sh.isConnected()) {
                            StringBuilder sb = new StringBuilder();
                            for (String w : serverApp.getAuthenticatedWorkers().keySet()) {
                                if(!sb.isEmpty()) sb.append(",");
                                sb.append(w);
                            }
                            Debug.log(new ClientListCommand(sb.toString()));
                            sh.sendCommand(new ClientListCommand(sb.toString()));

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException err) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }).start();
            }

            else {
                this.authenticatedWorker = serverApp.registerAuthenticatedWorker(
                    (ServerWorker)e.getSocketHandler(),
                    command.getLogin(),
                    command.getPassword()
                );
                if(authenticatedWorker == null) {
                    e.getSocketHandler().disconnect("Authentication failed");
                    return;
                }
                e.getSocketHandler().sendCommand(new Command("AUTH_ACK"));
            }            
        } catch (InvalidArgumentException err) {
            e.getSocketHandler().disconnect(err.getMessage());
        }
    }

    @NetworkEventHandler
    public void onCommand_ClientData(CommandReceivedEvent e) {
        if(!e.getCommand().getCommandHeader().equals("CLIENT_DATA")) return;
        try {
            ClientDataCommand command = new ClientDataCommand(e.getCommand());
            serverApp.getSubscribeMap().forEach((socketHanlder, client) -> {
                if(client.equals(command.getLogin())) socketHanlder.sendCommand(command);
            });
        } catch (InvalidArgumentException err) {
            err.printStackTrace();
        }
    }

    @NetworkEventHandler
    public void onCommand_Subscribe(CommandReceivedEvent e) {
        if(!e.getCommand().getCommandHeader().equals("SUBSCRIBE")) return;
        try {
            SubscribeCommand command = new SubscribeCommand(e.getCommand());
            serverApp.subscribe(e.getSocketHandler(), command.getClient());
        } catch (InvalidArgumentException err) {
            err.printStackTrace();
        }
    }

    /*@NetworkEventHandler
    public void onCommandReceived(CommandReceivedEvent e) {
        Debug.log("Command received :\n", e.getCommand().getCommandHeader());
    }*/
}
