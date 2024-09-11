package fr.gabrielabgrall.dmst.network.commands;

import java.util.Map;

import fr.gabrielabgrall.dmst.network.CommandHandler;
import fr.gabrielabgrall.dmst.network.SocketHandler;
import fr.gabrielabgrall.dmst.utils.Debug;

public class AuthCommand implements CommandHandler {

    protected SocketHandler socketHandler;

    public AuthCommand(SocketHandler socketHandler){
        this.socketHandler = socketHandler;
    }
    
    @Override
    public void handleCommand(String command, Map<String, String> args) {

        String data = "\t" + command;

        for (Map.Entry<String, String> entry : args.entrySet()) {
            data += "\n\t" + entry.getKey() + " " + entry.getValue();
        }
        
        Debug.log("Incoming command from ", socketHandler.getSocket().getRemoteSocketAddress(), ":\n", data);
    }
}
