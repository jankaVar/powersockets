package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by moru on 05/05/16.
 */
public class Socket extends Connectable {
    //center: (x,y)
    //connectedCables: []
    public Socket(Vector2 center) {
        super(center);
    }

    public boolean isPowered() {
        for(Cable cable : this.getConnectedCables()) {
            if(cable.isPowered()) {
                return true;
            }
        }
        return false;
    }
}
