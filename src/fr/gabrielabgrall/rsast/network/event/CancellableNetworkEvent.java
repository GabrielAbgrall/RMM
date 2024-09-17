package fr.gabrielabgrall.rsast.network.event;

public interface CancellableNetworkEvent {

    void setCancelled(boolean cancelled);

    boolean isCancelled();
}
