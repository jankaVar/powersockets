package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
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
    private TextureRegion region;

    public Connectable(Vector2 center, TextureRegion textRegion) {
        this.center = center;
        this.connectedCables = new HashSet<Cable>();
        this.region = textRegion;
    }

    public void draw (Batch batch, float parentAlpha) {
        //maybe I don't need color
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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
    public Vector2 getCenter() {
        return center;
    }
}
