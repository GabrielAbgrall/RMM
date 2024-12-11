package fr.gabrielabgrall.rmm.app.client;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import fr.gabrielabgrall.rmm.command.AuthCommand;
import fr.gabrielabgrall.rmm.command.ClientDataCommand;
import fr.gabrielabgrall.rmm.network.Client;
import fr.gabrielabgrall.rmm.utils.Debug;

public class ClientWorker extends Thread {

    protected ClientApp clientApp;
    protected Client client;

    public ClientWorker(String name, ClientApp clientaApp) {
        setName(name + "-Worker");
        this.clientApp = clientaApp;
        this.client = clientaApp.getClient();
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            while (!clientApp.isAuthenticated()) {
                client.sendCommand(new AuthCommand(client.getName(), "bonjour", Client.VERSION));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
            Debug.log("Auth OK");
            while (clientApp.getClient().isConnected()) {
                long time = System.currentTimeMillis();
                double cpuLoad = getCpuLoad();
                Debug.log("CPU Load: ", cpuLoad);
                client.sendCommand(new ClientDataCommand(client.getName(), time, cpuLoad));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }

    public double getCpuLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        return osBean.getCpuLoad();
    }
}
