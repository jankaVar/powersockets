package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

/**
 * Created by moru on 05/05/16.
 */
public class Switch {
    private final Vector2 center;
    private final Collection<Cable> connectedCables;
    private final Map<Short, Set<Short>> connectionMappings;
    private final Map<Cable, Short> cable2side;
    private final Map<Short, Cable> side2cable;

    /**
     * mappings when the screen isn't rotated. use this for level setup.
     */
    public final static Short TOP = 0, LEFT = 1, BOTTOM = 2, RIGHT = 3;


    public static Short upIs = TOP; //Short.TOP;

    /**
     * @deprecated
     * @param center
     */
    public Switch(Vector2 center) {
        this.center = center;
        this.connectedCables = new HashSet<Cable>();
        this.connectionMappings = new HashMap<Short, Set<Short>>();
        this.cable2side = new HashMap<Cable, Short>();
        this.side2cable = new HashMap<Short, Cable>();
    }

    /**
     *
     * @param center
     * @param mappings which sides are connected with
     *                 each other specified in tupples
     *                 of 2 or 3. e.g.:
     *                 `{{TOP, LEFT}, {RIGHT, BOTTOM}}` or
     *                 `{{RIGHT, TOP, LEFT}}
     */
    public Switch(Vector2 center, Short[][] mappings) {
        this.center = center;
        this.connectedCables = new HashSet<Cable>();
        this.cable2side = new HashMap<Cable, Short>();
        this.side2cable = new HashMap<Short, Cable>();

        /*
         * TODO pick correct image based on mapping
         * There's:
         *  * empty?: {{}}
         *  * a corner: {{LEFT,TOP}}
         *  * straight line: {{TOP, BOTTOM}}
         *  * two corners: {{LEFT, TOP}, {BOTTOM, RIGHT}}
         *  * the cross: {{TOP,BOTTOM}, {LEFT,RIGHT}}
         *  * three-way: {{TOP, BOTTOM, LEFT}}
         *  Everything else is just rotations of these.
         *  Thus it should be enough just rendering the image
         *  with a different rotation.
         */

        Map<Short, Set<Short>> lookupMap = new HashMap<Short, Set<Short>>();
        for(Short[] connection : mappings) {

            Set<Short> connectionAsSet = new HashSet<Short>();
            Collections.addAll(connectionAsSet, connection);

            for(Short side : connection) {
                Set<Short> connectedTo = new HashSet<Short>();
                connectedTo.addAll(connectionAsSet);
                connectedTo.remove(side);
                lookupMap.put(side, connectedTo);
            }
        }
        this.connectionMappings = lookupMap;
    }

    public void connectTo(Cable cable, Short fromSide){
        this.cable2side.put(cable, fromSide);
        this.side2cable.put(fromSide, cable);
        if(!cable.isConnectedTo(this)) {
            cable.connectTo(this, fromSide);
        }
    }
    public boolean isConnectedTo(Cable cable) {
        return cable2side.containsKey(cable);
    }

    public Collection<Cable> getConnectedCables() {
        return this.cable2side.keySet();
    }
    public Vector2 getCenter() { return center; }

    //0..3 mod 4 for rotation?
    //TOP..LEFT
    //connectedShorts: [(Short,Short)]

    //center: (x,y)
    //connectedCables: []

    public boolean directsPowerTo(Cable cable) {
        // example1: for {{TOP, LEFT}}-switch, cables at LEFT (to generator), BOTTOM to socket
        // ex1: cablesSide = BOTTOM = 2
        System.out.println("in directsPowerTo");

        /*
        Short cablesSide = this.cable2side.get(cable);
        int cablesSideAdjusted = (cablesSide + upIs) % 4;
        //(cablesSide + upIs) % 4;
        for(Short connectedSide : this.connectionMappings.get(cablesSideAdjusted)) {
            int connectedSideAdjusted = (connectedSide - upIs) % 4;
            Cable connectedCable = this.side2cable.get(connectedSide);
            if(connectedCable.isPowered()) {
                return true;
            }
        }
        */

        return false;
    }
}
