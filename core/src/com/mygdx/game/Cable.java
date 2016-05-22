package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by moru on 05/05/16.
 */
public class Cable extends Actor{
    private Collection<Connectable> connectables;
    private Collection<LineSegment> cableSegments;
    private TextureRegion region;

    public Cable(LineSegment...segments){
        this.connectables = new HashSet<Connectable>();
        this.cableSegments = new HashSet<LineSegment>();
        for(LineSegment s : segments) {
            this.cableSegments.add(s);
        }
        this.region = new TextureRegion(new Texture("pixel.png"));
    }

    public void draw (Batch batch, float parentAlpha) {
        //do I need color?
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void connectTo(Connectable connectable){
        this.connectables.add(connectable);
        if(!connectable.isConnectedTo(this)) {
            connectable.connectTo(this);
        }
    }

    /*
     * returns true if any of the attached connectables is a generator
     * or a switch that's connecting to a powered cable
     */
    public boolean isPowered() {
        for(Connectable c : connectables) {
            if(c instanceof Generator) {
                return true;
            } else if (c instanceof Switch && ((Switch)c).directsPowerTo(this)) {
                return true;
            }
        }
        return false;
    }

    public boolean isConnectedTo(Connectable connectable) {
        return this.connectables.contains(connectable);
    }

    public Collection<LineSegment> getCableSegments() {
        return cableSegments;
    }
}



