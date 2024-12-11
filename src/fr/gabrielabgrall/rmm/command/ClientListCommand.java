package fr.gabrielabgrall.rmm.command;

import fr.gabrielabgrall.rmm.InvalidArgumentException;
import fr.gabrielabgrall.rmm.network.command.Command;

public class ClientListCommand extends Command {

    /**
     * 
     * @param clients Clients' login separated by a coma (ex: "ClientA,ClientB,ClientC")
     */
    public ClientListCommand(String clients) {
        super("CLIENT_LIST", "clients " + clients);
    }

    public ClientListCommand(Command command) throws InvalidArgumentException {
        super(command.toString());
        checkForArgs("clients");
    }

    public String[] getClients() {
        return getArgs().get("clients").split(",");
    }
}
