package fr.gabrielabgrall.rmm.command;

import fr.gabrielabgrall.rmm.InvalidArgumentException;
import fr.gabrielabgrall.rmm.network.command.Command;

public class SubscribeCommand extends Command {

    public SubscribeCommand(String clientLogin) {
        super("SUBSCRIBE", "client " + clientLogin);
    }

    public SubscribeCommand(Command command) throws InvalidArgumentException {
        super(command.toString());
        checkForArgs("client");
    }

    public String getClient() {
        return args.get("client");
    }
}
