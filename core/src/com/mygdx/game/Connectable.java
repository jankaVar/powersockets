package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import java.util.Collection;
import java.util.HashSet;

/**
 * Anything that can be attached to a cable.
 * Created by moru on 05/05/16.
 */
public abstract class Connectable {
    private final Vector2 center;
    private final Collection<Cable> connectedCables;

    public Connectable(Vector2 center) {
        this.center = center;
        this.connectedCables = new HashSet<Cable>();
    }

    public void connectTo(Cable cable){
        this.connectedCables.add(cable);
        if(!cable.isConnectedTo(this)) {
            cable.connectTo(this);
        }
    }
    public boolean isConnectedTo(Cable cable) {
        return connectedCables.contains(cable);
    }

    public Collection<Cable> getConnectedCables() {
        return this.connectedCables;
    }
}
