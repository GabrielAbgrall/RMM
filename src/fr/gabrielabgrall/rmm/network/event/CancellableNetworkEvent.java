package fr.gabrielabgrall.rmm.network.event;

public interface CancellableNetworkEvent {

    void setCancelled(boolean cancelled);

    boolean isCancelled();
}
