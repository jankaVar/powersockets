package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by moru on 05/05/16.
 */
public class Switch extends Connectable {
    //0..3 mod 4 for rotation?
    //TOP..LEFT
    //connectedSides: [(Side,Side)]

    //center: (x,y)
    //connectedCables: []

    public Switch(Vector2 center) {
        super(center);
    }

    public boolean directsPowerTo(Cable cable) {
        //TODO implement
        return false;
    }

}
