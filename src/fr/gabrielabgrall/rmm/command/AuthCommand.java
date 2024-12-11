package fr.gabrielabgrall.rmm.command;

import fr.gabrielabgrall.rmm.InvalidArgumentException;
import fr.gabrielabgrall.rmm.network.command.Command;

public class AuthCommand extends Command {

    public AuthCommand(String login, String password, String version) {
        super("AUTH", "login " + login, "password " + password, "version " + version);
    }

    public AuthCommand(Command command) throws InvalidArgumentException {
        super(command.toString());
        checkForArgs("login", "password", "version");
    }

    public String getLogin() {
        return getArgs().get("login");
    }

    public String getPassword() {
        return getArgs().get("password");
    }

    public String getVersion() {
        return getArgs().get("version");
    }
}
