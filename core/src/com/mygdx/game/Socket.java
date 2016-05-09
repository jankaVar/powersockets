package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public boolean socketTouched(Vector2 center, float SOCKET_SIZE) {
        Vector2 socketCenter = getCenter();
        System.out.println("socketcenter "+ socketCenter.x + " y " + socketCenter.y + " x >" + center.x + " y " + center.y + " socket size " + SOCKET_SIZE );
        if ((socketCenter.x + SOCKET_SIZE/2 > center.x) && (socketCenter.x - SOCKET_SIZE/2 < center.x) &&
                (socketCenter.y + SOCKET_SIZE/2 > center.y) && (socketCenter.y - SOCKET_SIZE/2 < center.y)) {
            return true;
        } else {
            return false;
        }
    }
}
