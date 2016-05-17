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
    public long startTime;
    public final long maxTime;
    //private isVibratingSince;
    //private Rotation
    //private final maxTime
    //private remainingTime

    public Level(long time) {
        this.cables = new HashSet<Cable>();
        this.sockets = new HashSet<Socket>();
        this.generators = new HashSet<Generator>();
        this.switches = new HashSet<Switch>();

        this.startTime = System.currentTimeMillis();
        this.maxTime = time;
    }
    public Level(Socket[] sockets, Generator[] generators, Cable[] cables, long time){
        this(time);

        for (Socket soc: sockets){
            this.sockets.add(soc);
        }

        for (Generator gen: generators){
            this.generators.add(gen);
        }

        for (Cable cab: cables) {
            this.cables.add(cab);
        }
    }
    public void reset() {
        this.startTime = System.currentTimeMillis();
    }

    //all levels
    public static Level generateLevel(int levelNumer){
        Level levelGen;

        switch (levelNumer) {
            case 1:  levelGen = generateLevel1();
                break;
            case 2:  levelGen = generateLevel2();
                break;
            case 3:  levelGen = generateLevel3();
                break;
            case 4:  levelGen = generateLevel4();
                break;
            case 5:  levelGen = generateLevel5();
                break;
            default: levelGen = generateLevel2();
                System.out.println("out of levels");
                break;
        }

        return levelGen;

    }

    private static Generator[] addGenerators(Vector2[] generatorsPos){

        Generator[] generators = new Generator[generatorsPos.length];
        for (int i = 0; i < generatorsPos.length; i++){
            generators[i] = new Generator(generatorsPos[i]);
        }

        return generators;
    }

    private static Socket[] addSockets(Vector2[] socketsPos){

        Socket[] sockets = new Socket[socketsPos.length];
        for (int i = 0; i < socketsPos.length; i++){
            sockets[i] = new Socket(socketsPos[i]);
        }

        return sockets;
    }

    private static Cable[] addCables(int[] joints, float[][] xCoord, float[][] yCoord){

        LineSegment[][] lines = new LineSegment[joints.length][];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new LineSegment[joints[i]];
            for (int j = 0; j < lines[i].length; j++){
                lines[i][j] = new LineSegment(new Vector2(xCoord[i][j], yCoord[i][j]), new Vector2(xCoord[i][j + 1], yCoord[i][j + 1]));
            }
        }

        Cable[] cable = new Cable[lines.length];
        for (int i = 0; i < cable.length; i++) {
            cable[i] = new Cable(lines[i]);
        }

        return cable;
    }

    public static Level generateLevel1() {
        //two sockets, one connected to a generator

        //generators
        //Vector2[] generatorsPos = {new Vector2(0.50f, 0.75f)};
        //Generator[] generators = addGenerators(generatorsPos);
        Generator[] generators = {};

        //sockets
        Vector2[] socketsPos = {new Vector2(0.50f, 0.50f)};
        Socket[] sockets = addSockets(socketsPos);

        //lines made into cables
        int[] joints = {};
        float[][] xCoord = {{}};
        float[][] yCoord = {{}};
        Cable[] cables = addCables(joints, xCoord, yCoord);

        //returns Level
        return new Level(sockets, generators, cables, 5000);

        //super simple variant: just svg, the coordinates for the right sockets and the correct rotation?
    }

    public static Level generateLevel2() {
        //two sockets, one connected to a generator

        //generators
        Vector2[] generatorsPos = {new Vector2(0.25f, 0.75f)};
        Generator[] generators = addGenerators(generatorsPos);

        //sockets
        Vector2[] socketsPos = {new Vector2(0.25f, 0.25f), new Vector2(0.75f, 0.25f)};
        Socket[] sockets = addSockets(socketsPos);

        //lines made into cables
        int[] joints = {1};
        float[][] xCoord = {{0.25f, 0.25f}};
        float[][] yCoord = {{0.25f, 0.75f}};
        Cable[] cables = addCables(joints, xCoord, yCoord);

        //could be done nicer too, but nothing comes to my mind now
        cables[0].connectTo(sockets[0]);
        cables[0].connectTo(generators[0]);

        //returns Level
        return new Level(sockets, generators, cables, 5000);

        //super simple variant: just svg, the coordinates for the right sockets and the correct rotation?
    }

    public static Level generateLevel3() {
        //two sockets, one connected to a generator

        //generators
        Vector2[] generatorsPos = {new Vector2(0.50f, 0.75f)};
        Generator[] generators = addGenerators(generatorsPos);

        //sockets
        Vector2[] socketsPos = {new Vector2(0.25f, 0.20f), new Vector2(0.75f, 0.20f)};
        Socket[] sockets = addSockets(socketsPos);

        //lines made into cables
        int[] joints = {3, 3};
        float[][] xCoord = {{0.25f, 0.25f, 0.75f, 0.75f}, {0.75f, 0.75f, 0.50f, 0.50f}};
        float[][] yCoord = {{0.20f, 0.50f, 0.50f, 0.75f}, {0.20f, 0.35f, 0.35f, 0.75f}};
        Cable[] cables = addCables(joints, xCoord, yCoord);

        //could be done nicer too, but nothing comes to my mind now
        cables[0].connectTo(sockets[0]);
        cables[1].connectTo(sockets[1]);
        cables[1].connectTo(generators[0]);

        //returns Level
        return new Level(sockets, generators, cables, 5000);

        //super simple variant: just svg, the coordinates for the right sockets and the correct rotation?
    }

    public static Level generateLevel4() {
        //three sockets, two generators!

        //generators
        Vector2[] generatorsPos = {new Vector2(0.15f, 0.85f), new Vector2(0.7f, 0.5f)};
        Generator[] generators = addGenerators(generatorsPos);

        //sockets
        Vector2[] socketsPos = {new Vector2(0.20f, 0.20f), new Vector2(0.45f, 0.20f), new Vector2(0.80f, 0.20f)};
        Socket[] sockets = addSockets(socketsPos);

        //lines made into cables
        int[] joints = {5, 6, 6};
        float[][] xCoord = {{0.20f, 0.20f, 0.90f, 0.90f, 0.60f, 0.60f}, {0.45f, 0.45f, 0.25f, 0.25f, 0.40f, 0.40f, 0.70f}, {0.80f, 0.80f, 0.15f, 0.15f, 0.70f, 0.70f, 0.15f}};
        float[][] yCoord = {{0.20f, 0.35f, 0.35f, 0.70f, 0.70f, 0.20f}, {0.20f, 0.45f, 0.45f, 0.70f, 0.70f, 0.50f, 0.50f}, {0.20f, 0.40f, 0.40f, 0.65f, 0.65f, 0.85f, 0.85f}};
        Cable[] cables = addCables(joints, xCoord, yCoord);

        //could be done nicer too, but nothing comes to my mind now
        cables[0].connectTo(sockets[0]);
        cables[1].connectTo(sockets[1]);
        cables[1].connectTo(generators[1]);
        cables[2].connectTo(sockets[2]);
        cables[2].connectTo(generators[0]);

        //returns Level
        return new Level(sockets, generators, cables, 5000);

    }

    public static Level generateLevel5() {
        //three sockets, two generators!

        //generators
        Vector2[] generatorsPos = {new Vector2(0.35f, 0.75f), new Vector2(0.65f, 0.75f)};
        Generator[] generators = addGenerators(generatorsPos);

        //sockets
        Vector2[] socketsPos = {new Vector2(0.20f, 0.20f), new Vector2(0.50f, 0.20f), new Vector2(0.80f, 0.20f)};
        Socket[] sockets = addSockets(socketsPos);

        //lines made into cables
        int[] joints = {5, 3, 5};
        float[][] xCoord = {{0.20f, 0.20f, 0.95f, 0.95f, 0.35f, 0.35f}, {0.50f, 0.50f, 0.85f, 0.85f}, {0.80f, 0.80f, 0.15f, 0.15f, 0.65f, 0.65f}};
        float[][] yCoord = {{0.20f, 0.40f, 0.40f, 0.60f, 0.60f, 0.75f}, {0.20f, 0.50f, 0.50f, 0.75f}, {0.20f, 0.35f, 0.35f, 0.90f, 0.90f, 0.75f}};
        Cable[] cables = addCables(joints, xCoord, yCoord);

        //could be done nicer too, but nothing comes to my mind now
        cables[0].connectTo(sockets[0]);
        cables[0].connectTo(generators[0]);
        cables[1].connectTo(sockets[1]);
        cables[2].connectTo(sockets[2]);
        cables[2].connectTo(generators[1]);

        //returns Level
        return new Level(sockets, generators, cables, 5000);

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
