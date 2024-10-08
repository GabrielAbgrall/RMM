package fr.gabrielabgrall.rsast.app.server;

import fr.gabrielabgrall.rsast.network.ServerWorker;

public class AuthenticatedWorker {

    protected ServerWorker serverWorker;
    protected String login;
    protected int errorCount;

    public AuthenticatedWorker(ServerWorker serverWorker, String login) {
        this.serverWorker = serverWorker;
        this.login = login;
        this.errorCount = 0;
    }

    public ServerWorker getServerWorker() {
        return serverWorker;
    }

    public String getLogin() {
        return login;
    }

    public int getErrorCount() {
        return errorCount;
    }
}
