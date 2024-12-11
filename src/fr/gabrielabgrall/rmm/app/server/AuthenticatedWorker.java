package fr.gabrielabgrall.rmm.app.server;

import fr.gabrielabgrall.rmm.network.ServerWorker;

public class AuthenticatedWorker {

    protected ServerWorker serverWorker;
    protected String login;

    public AuthenticatedWorker(ServerWorker serverWorker, String login) {
        this.serverWorker = serverWorker;
        this.login = login;
    }

    public ServerWorker getServerWorker() {
        return serverWorker;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AuthenticatedWorker && ((AuthenticatedWorker)obj).login.equals(this.login);
    }
}
