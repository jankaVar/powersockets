package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by moru on 05/05/16.
 */
public class Level {
    private final Collection<Cable> cables;
    private final Collection<Socket> sockets;
    private final Collection<Generator> generators;
    private final Collection<Switch> switches;
    //private isVibratingSince;
    //private Rotation
    //private final maxTime
    //private remainingTime

    public Level() {
        this.cables = new HashSet<Cable>();
        this.sockets = new HashSet<Socket>();
        this.generators = new HashSet<Generator>();
        this.switches = new HashSet<Switch>();
    }


    public static Level generateLevel2() {
        //two sockets, one connected to a generator

        Vector2 generatorPos = new Vector2(0.25f, 0.25f);
        Generator generator = new Generator(generatorPos);

        Vector2 connectedSocketPos = new Vector2(0.75f, 0.25f);
        Socket connectedSocket = new Socket(connectedSocketPos);

        Vector2 safeSocketPos = new Vector2(0.75f, 0.75f);
        Socket safeSocket = new Socket(safeSocketPos);


        LineSegment straightCable = new LineSegment(generatorPos, connectedSocketPos);
        Cable cable = new Cable(straightCable);
        cable.connectTo(generator);
        cable.connectTo(connectedSocket);


        Level level = new Level();
        level.sockets.add(safeSocket);
        level.sockets.add(connectedSocket);
        level.generators.add(generator);
        level.cables.add(cable);


        System.out.println("Is safe socket powered? " + safeSocket.isPowered());
        System.out.println("Is dangerous socket powered? " + connectedSocket.isPowered());

        return level;

        //super simple variant: just svg, the coordinates for the right sockets and the correct rotation?
    }

    public Collection<Cable> getCables() {
        return cables;
    }

    public Collection<Socket> getSockets() {
        return sockets;
    }

    public Collection<Generator> getGenerators() {
        return generators;
    }

    public Collection<Switch> getSwitches() {
        return switches;
    }

}
