package fr.gabrielabgrall.rmm.command;

import fr.gabrielabgrall.rmm.InvalidArgumentException;
import fr.gabrielabgrall.rmm.network.command.Command;

public class ClientDataCommand extends Command {

    public ClientDataCommand(String login, long time, Double cpuLoad) {
        super("CLIENT_DATA", "login " + login, "time " + time, "cpu " + cpuLoad);
    }

    public ClientDataCommand(Command command) throws InvalidArgumentException {
        super(command.toString());
        checkForArgs("login", "time", "cpu");
    }

    public String getLogin() {
        return getArgs().get("login");
    }

    public long getTime() {
        return Long.parseLong(getArgs().get("time"));
    }

    public Double getCpuLoad() {
        return Double.parseDouble(getArgs().get("cpu"));
    }
}
