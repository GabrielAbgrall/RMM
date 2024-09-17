package fr.gabrielabgrall.dmst.network.event;

public interface CancellableNetworkEvent {

    void setCancelled(boolean cancelled);

    boolean isCancelled();
}
