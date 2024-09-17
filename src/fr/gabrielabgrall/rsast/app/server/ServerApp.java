package fr.gabrielabgrall.rsast.app.server;

import java.io.IOException;

import fr.gabrielabgrall.rsast.network.Server;
import fr.gabrielabgrall.rsast.network.command.Command;
import fr.gabrielabgrall.rsast.network.event.command.CommandReceivedEvent;
import fr.gabrielabgrall.rsast.network.event.server.IncomingClientEvent;
import fr.gabrielabgrall.rsast.network.event.server.ServerStartedEvent;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventHandler;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventListener;
import fr.gabrielabgrall.rsast.utils.Debug;

public class ServerApp {
    
    protected Server server;

    public ServerApp(int port) {
        try {
            this.server = new Server("Server", port);

            server.getEventManager().registerListener(new NetworkEventListener() {
                @NetworkEventHandler
                public void onIncomingClient(IncomingClientEvent e) {
                    e.getServerWorker().getEventManager().registerListener(new NetworkEventListener() {
                        @NetworkEventHandler
                        public void onCommandReceive(CommandReceivedEvent e) {
                            if(e.getCommand().getCommandHeader().equalsIgnoreCase("DATA")) {
                                e.getSocketHandler().sendCommand(new Command("DATA_ACK"));
                            }
                            Debug.log("Command received from ", e.getSocketHandler().getName(), ": ", e.getCommand().getCommandHeader());
                        }
                    });
                    Debug.log("New client connected on ", e.getServerWorker().getName(), " from ", e.getServerWorker().getSocket().getRemoteSocketAddress());
                }

                @NetworkEventHandler
                public void onServerStarted(ServerStartedEvent e) {
                    Debug.log("Server started, listening to new connections on port ", e.getServer().getServerSocket().getLocalPort());
                }
            });

            server.start();
        } catch (IOException e) {
            Debug.log("Unable to open stream: port already used or permission denied");
        }
    }
}
