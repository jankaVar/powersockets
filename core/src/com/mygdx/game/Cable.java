package com.mygdx.game;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by moru on 05/05/16.
 */
public class Cable {
    private Collection<Connectable> connectables;
    private Collection<Switch> switches;
    private Collection<LineSegment> cableSegments;

    public Cable(LineSegment...segments){
        this.connectables = new HashSet<Connectable>();
        this.switches = new HashSet<Switch>();
        this.cableSegments = new HashSet<LineSegment>();
        for(LineSegment s : segments) {
            this.cableSegments.add(s);
        }
    }

    public void connectTo(Connectable connectable){
        this.connectables.add(connectable);
        if(!connectable.isConnectedTo(this)) {
            connectable.connectTo(this);
        }
    }
    public void connectTo(Switch sw, int onSideXOfSwitch){
        this.switches.add(sw);
        if(!sw.isConnectedTo(this)) {
            sw.connectTo(this, onSideXOfSwitch);
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
            }
        }
        for(Switch sw : switches) {
            if(sw.directsPowerTo(this)) {
                return true;
            }
        }
        return false;
    }

    public boolean isConnectedTo(Connectable connectable) {
        return this.connectables.contains(connectable);
    }
    public boolean isConnectedTo(Switch sw) {
        return this.switches.contains(sw);
    }

    public Collection<LineSegment> getCableSegments() {
        return cableSegments;
    }
}



