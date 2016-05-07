package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by moru on 05/05/16.
 */
public class LineSegment {
    public final Vector2 start;
    public final Vector2 end;
    public LineSegment(Vector2 start, Vector2 end) {
        this.start = start;
        this.end = end;
    }
}
