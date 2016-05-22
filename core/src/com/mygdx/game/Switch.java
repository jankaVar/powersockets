package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    //to be changed - socket.png to switch-to-left.png or something like it
    public Switch(Vector2 center) {
        super(center, new TextureRegion(new Texture("socket.png")));
    }

    public boolean directsPowerTo(Cable cable) {
        //TODO implement
        return false;
    }

}
