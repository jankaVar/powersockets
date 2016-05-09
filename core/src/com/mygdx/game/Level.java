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

    public static Level generateLevel4() {
        //three sockets, two generators
        ///redoit!!!!!

        Vector2 generatorPos = new Vector2(0.20f, 0.80f);
        Generator generator = new Generator(generatorPos);

        Vector2 generatorPos2 = new Vector2(0.80f, 0.60f);
        Generator generator2 = new Generator(generatorPos2);

        Vector2 safeSocketPos = new Vector2(0.20f, 0.20f);
        Socket safeSocket = new Socket(safeSocketPos);

        Vector2 connectedSocketPos = new Vector2(0.40f, 0.20f);
        Socket connectedSocket = new Socket(connectedSocketPos);

        Vector2 connectedSocketPos2 = new Vector2(0.80f, 0.20f);
        Socket connectedSocket2 = new Socket(connectedSocketPos2);


        //final int sockets = 3;

        LineSegment[] lines1 = new LineSegment[5];

        lines1[0] = new LineSegment(safeSocketPos, new Vector2(safeSocketPos.x, safeSocketPos.y + 0.20f));
        lines1[1] = new LineSegment(new Vector2(safeSocketPos.x, safeSocketPos.y + 0.20f), new Vector2(safeSocketPos.x + 0.8f, safeSocketPos.y + 0.20f));
        lines1[2] = new LineSegment(new Vector2(safeSocketPos.x + 0.8f, safeSocketPos.y + 0.20f), new Vector2(safeSocketPos.x + 0.8f, safeSocketPos.y + 0.60f));
        lines1[3] = new LineSegment(new Vector2(safeSocketPos.x + 0.8f, safeSocketPos.y + 0.60f), new Vector2(safeSocketPos.x + 0.4f, safeSocketPos.y + 0.60f));
        lines1[4] = new LineSegment(new Vector2(safeSocketPos.x + 0.4f, safeSocketPos.y + 0.60f), new Vector2(safeSocketPos.x + 0.4f, safeSocketPos.y));

        Cable cable1 = new Cable(lines1);
        cable1.connectTo(safeSocket);

        LineSegment[] lines2 = new LineSegment[6];

        lines2[0] = new LineSegment(generatorPos, new Vector2(generatorPos.x + 0.40f, generatorPos.y));
        lines2[1] = new LineSegment(new Vector2(generatorPos.x + 0.40f, generatorPos.y), new Vector2(generatorPos.x + 0.4f, generatorPos.y - 0.20f));
        lines2[2] = new LineSegment(new Vector2(generatorPos.x + 0.4f, generatorPos.y - 0.20f), new Vector2(generatorPos.x , generatorPos.y - 0.20f));
        lines2[3] = new LineSegment(new Vector2(generatorPos.x, generatorPos.y - 0.20f), new Vector2(generatorPos.x, generatorPos.y - 0.50f));
        lines2[4] = new LineSegment(new Vector2(generatorPos.x, generatorPos.y - 0.50f), new Vector2(generatorPos.x + 0.6f, generatorPos.y - 0.50f));
        lines2[5] = new LineSegment(new Vector2(generatorPos.x + 0.6f, generatorPos.y - 0.50f), connectedSocketPos2);

        Cable cable2 = new Cable(lines2);
        cable2.connectTo(connectedSocket);
        cable2.connectTo(generator);

        LineSegment[] lines3 = new LineSegment[4];

        lines3[0] = new LineSegment(generatorPos2, new Vector2(generatorPos2.x - 0.20f, generatorPos2.y));
        lines3[1] = new LineSegment(new Vector2(generatorPos2.x - 0.20f, generatorPos2.y), new Vector2(generatorPos2.x - 0.2f, generatorPos2.y - 0.2f));
        lines3[2] = new LineSegment(new Vector2(generatorPos2.x - 0.2f, generatorPos2.y - 0.2f), new Vector2(generatorPos2.x - 0.20f, generatorPos2.y - 0.40f));
        lines3[3] = new LineSegment(new Vector2(generatorPos2.x - 0.2f, generatorPos2.y - 0.40f), connectedSocketPos);

        Cable cable3 = new Cable(lines3);
        cable3.connectTo(connectedSocket2);
        cable3.connectTo(generator2);

        Level level = new Level();
        level.sockets.add(safeSocket);
        level.sockets.add(connectedSocket);
        level.sockets.add(connectedSocket2);
        level.generators.add(generator);
        level.generators.add(generator2);
        level.cables.add(cable1);
        level.cables.add(cable2);
        level.cables.add(cable3);


        System.out.println("Is safe socket powered? " + safeSocket.isPowered());
        System.out.println("Is dangerous socket powered? " + connectedSocket.isPowered());
        System.out.println("Is dangerous socket2 powered? " + connectedSocket2.isPowered());

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
