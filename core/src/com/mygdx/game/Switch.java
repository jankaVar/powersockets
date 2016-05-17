package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

/**
 * Created by moru on 05/05/16.
 */
public class Switch {
    private final Vector2 center;
    private final Collection<Cable> connectedCables;

    /**
     * @deprecated
     */
    private final Map<Integer, Set<Integer>> connectionMappingsDepr;
    private final int[][] connectionMappings;
    private final Map<Cable, Integer> cable2side;
    /**
     * @deprecated
     */
    private final Map<Integer, Cable> side2cableDepr;

    private final Cable[] side2cable = new Cable[4];

    /**
     * mappings when the screen isn't rotated. use this for level setup.
     */
    public final static int TOP = 0, LEFT = 1, BOTTOM = 2, RIGHT = 3;


    private static int upIs = TOP; //Short.TOP;
    public static void setUpIs(int upIs) {
        Switch.upIs = upIs % 4;
    }
    public static int getUpIs() {
        return upIs;
    }

    /**
     * @deprecated
     * @param center
     */
    public Switch(Vector2 center) {
        this.center = center;
        this.connectedCables = new HashSet<Cable>();
        this.connectionMappingsDepr = new HashMap<Integer, Set<Integer>>();
        this.cable2side = new HashMap<Cable, Integer>();
        this.side2cableDepr = new HashMap<Integer, Cable>();
        this.connectionMappings = new int[4][4];
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
    public Switch(Vector2 center, int[][] mappings) {
        this.center = center;
        this.connectedCables = new HashSet<Cable>();
        this.cable2side = new HashMap<Cable, Integer>();
        this.side2cableDepr = new HashMap<Integer, Cable>();
        this.connectionMappingsDepr = new HashMap<Integer, Set<Integer>>();

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

        Map<Integer, Set<Integer>> lookupMapDepr = new HashMap<Integer, Set<Integer>>();
        int[][] lookupMap = new int[4][0];
        for(int[] connection : mappings) {

            for(int side : connection) {
                System.out.println("Switch constructor " +
                        connection + " " +
                        side + " -> " + lookupMap[side]);
                int[] connectedTo = without(side, connection);
                lookupMap[side] = concat(lookupMap[side], connectedTo);
            }
        }
        this.connectionMappings = lookupMap;
    }

    public void connectTo(Cable cable, int fromSide){
        this.cable2side.put(cable, fromSide);
        this.side2cable[fromSide] = cable;
        this.side2cableDepr.put(fromSide, cable);
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
        /* examples:
         *
         * - for {{TOP, LEFT}}-switch = {{0,1}}
         * - cables at LEFT (to generator) = 1
         * - BOTTOM to socket = 2
         * - questionedSideExternal = BOTTOM = 2
         *
         * Ex1:upIs = RIGHT = 3 should result in true
         *
         * Ex2:upIs = BOTTOM = 2 should result in false
         */

        Integer questionedSideExternal = this.cable2side.get(cable); //ex1: 2 //ex2:2
        int questionedSideInternal = (questionedSideExternal + upIs) % 4; //ex1: 1 //ex2:0

        int[] connectedSides = this.connectionMappings[questionedSideInternal]; //ex1: {0} //ex2:{1}
        for(int connectedSideInternal : connectedSides) {
            int connectedSideExternal = (connectedSideInternal - upIs + 4) % 4; //ex1: 1 //ex2: 3

            Cable connectedCable = side2cable[connectedSideExternal];
            if(connectedCable != null && connectedCable.isPowered()) { //ex2: no cable connected to 3 (RIGHT)
                return true; //ex1
            }
        }

        return false;
    }

    private static int[] without(int x, int[] set) {
        int[] filteredSet = new int[set.length - 1];
        for(int i = 0, j = 0; i < set.length; i++) {
            if(set[i] != x) {
                filteredSet[j] = set[i];
                j++;
            }
        }
        return filteredSet;
    }

    private static void arr2stdout(int[][] array) {
        for(int i = 0; i<array.length; i++) {
            if(array[i] != null) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.println(i + " -> " + array[i][j]);
                }
            }
        }
    }

    private static int[] concat(int[] arrA, int[] arrB) {
        int[] ret = new int[arrA.length + arrB.length];
        for(int i = 0; i < arrA.length; i++) {
            ret[i] = arrA[i];
        }
        for(int i = arrA.length; i < ret.length; i++) {
            ret[i] = arrB[i - arrA.length];
        }
        return ret;
    }

}
