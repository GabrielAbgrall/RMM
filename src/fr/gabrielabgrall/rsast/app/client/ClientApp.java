package fr.gabrielabgrall.rsast.app.client;

import fr.gabrielabgrall.rsast.network.Client;
import fr.gabrielabgrall.rsast.network.command.Command;
import fr.gabrielabgrall.rsast.network.event.command.CommandReceivedEvent;
import fr.gabrielabgrall.rsast.network.event.socket.ConnectionEvent;
import fr.gabrielabgrall.rsast.network.event.socket.DisconnectionEvent;
import fr.gabrielabgrall.rsast.network.event.socket.LostConnectionEvent;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventHandler;
import fr.gabrielabgrall.rsast.network.event.utils.NetworkEventListener;
import fr.gabrielabgrall.rsast.utils.Debug;

public class ClientApp extends Thread {
    
    protected Client client;

    public ClientApp(String name, String host, int port) {
        this.client = new Client(name, host, port);

        client.getEventManager().registerListener(new NetworkEventListener() {
            @NetworkEventHandler
            public void onDisconnect(DisconnectionEvent e) {
                Debug.log("Disconnected from ", e.getSocket().getRemoteSocketAddress());
            }
            
            @NetworkEventHandler
            public void onConnect(ConnectionEvent e) {
                Debug.log("Connected to ", e.getSocket().getRemoteSocketAddress());
            }

            @NetworkEventHandler
            public void onCommandReceive(CommandReceivedEvent e) {
                Debug.log("Incoming command: ", e.getCommand().getCommandHeader());
            }

            @NetworkEventHandler
            public void onConnectionLost(LostConnectionEvent e) {
                Debug.log("Lost connection to remote.");
            }
        });

        client.start();
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                if(client.isConnected()) client.sendCommand(new Command("DATA", "log thisisatestlogfordataupload"));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}
