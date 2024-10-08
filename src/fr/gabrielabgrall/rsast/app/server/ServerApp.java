package fr.gabrielabgrall.rsast.app.server;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.gabrielabgrall.rsast.app.server.listener.ServerListener;
import fr.gabrielabgrall.rsast.network.Server;
import fr.gabrielabgrall.rsast.network.ServerWorker;
import fr.gabrielabgrall.rsast.utils.Debug;

public class ServerApp {

    public static final String VERSION = "0.1";
    
    protected Server server;
    protected Map<String, AuthenticatedWorker> authenticatedWorkers = new HashMap<>();

    public ServerApp(int port) {
        try {
            this.server = new Server("Server", port);

            server.getEventManager().registerListener(new ServerListener(this));

            server.start();
        } catch (IOException e) {
            Debug.log("Unable to open stream: port already used or permission denied");
        }
    }

    public boolean checkVersion(String version) {
        return VERSION.split("\\.")[0].equals(version.split("\\.")[0]);
    }

    public boolean registerAuthenticatedWorker(ServerWorker serverWorker, String login, String password) {
        if(!password.equals("bonjour")) return false;
        serverWorker.setName(login);
        authenticatedWorkers.put(login, new AuthenticatedWorker(serverWorker, login));
        Debug.log("ServerWorker authenticated");
        return true;
    }

    public Map<String, AuthenticatedWorker> getAuthenticatedWorkers() {
        return authenticatedWorkers;
    }

    public List<AuthenticatedWorker> getAuthServerWorkersSortedByName() {
        List<AuthenticatedWorker> cp = List.copyOf(authenticatedWorkers.values());
        cp.sort(new Comparator<AuthenticatedWorker>() {
            public int compare(AuthenticatedWorker o1, AuthenticatedWorker o2) {
                return o1.getLogin().compareTo(o2.getLogin());
            };
        });
        return cp;
    }

    public List<AuthenticatedWorker> getAuthServerWorkersSortedByErrorCount() {
        List<AuthenticatedWorker> cp = List.copyOf(authenticatedWorkers.values());
        cp.sort(Comparator.comparingInt(AuthenticatedWorker::getErrorCount));
        return cp;
    }
}
