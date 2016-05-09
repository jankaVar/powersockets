package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Collection;
import java.util.HashSet;

/**
 * Anything that can be attached to a cable.
 * Created by moru on 05/05/16.
 */
public abstract class Connectable extends Actor {
    private final Vector2 center;
    private final Collection<Cable> connectedCables;

    //public abstract TextureRegion getImage();
    //TODO move method to subclasses
    public TextureRegion img;

    public String getImageFilename() {
        return "socket.png";
    }

    public Connectable(Vector2 center) {
        this.center = center;
        this.connectedCables = new HashSet<Cable>();

        this.img = new TextureRegion(
                new Texture(getImageFilename()),
                0.0f, 0.0f, 1.0f, 1.0f
        );
    }

    public void connectTo(Cable cable){
        this.connectedCables.add(cable);
        if(!cable.isConnectedTo(this)) {
            cable.connectTo(this);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw( this.img, 0.5f, 0.5f);
        /*
        batch.draw(this.img, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
                */

    }


    public boolean isConnectedTo(Cable cable) {
        return connectedCables.contains(cable);
    }

    public Collection<Cable> getConnectedCables() {
        return this.connectedCables;
    }
    public Vector2 getCenter() { return center; }
}
