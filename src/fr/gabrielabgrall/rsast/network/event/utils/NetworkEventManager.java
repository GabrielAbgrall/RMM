package fr.gabrielabgrall.rsast.network.event.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.gabrielabgrall.rsast.network.event.NetworkEvent;

public class NetworkEventManager {

    protected List<NetworkEventListener> listeners = new ArrayList<>();
    protected List<NetworkEventListener> internalListeners = new ArrayList<>();

    public void triggerEvent(NetworkEvent e) {
        triggerListeners(e, listeners);
    }

    public void triggerInternalEvent(NetworkEvent e) {
        triggerListeners(e, internalListeners);
    }

    protected void triggerListeners(NetworkEvent e, List<NetworkEventListener> ls) {
        for (NetworkEventListener l : ls) {
            for(Method m : l.getClass().getMethods()) {
                boolean isAnnotated = m.isAnnotationPresent(NetworkEventHandler.class);
                boolean canHandleEvent = Arrays.asList(m.getParameterTypes()).stream().map(t -> t.isInstance(e)).toList().contains(true);
                boolean hasSingleParam = m.getParameterCount()==1;
                if(isAnnotated && canHandleEvent && hasSingleParam) {            
                    m.setAccessible(true);
                    try {
                        m.invoke(l, e);
                    } catch (IllegalAccessException | InvocationTargetException err) {
                        err.printStackTrace();
                    }
                }
            }
        }
    }
    
    public void registerListener(NetworkEventListener listener) {
        this.listeners.add(listener);
    }

    public void unregisterListener(NetworkEventListener listener) {
        this.listeners.remove(listener);
    }
    
    public void registerInternalListener(NetworkEventListener listener) {
        this.internalListeners.add(listener);
    }

    public void unregisterInternalListener(NetworkEventListener listener) {
        this.internalListeners.remove(listener);
    }
}
