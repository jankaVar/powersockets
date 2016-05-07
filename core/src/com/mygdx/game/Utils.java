package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;

/**
 * Created by moru on 07/05/16.
 */
public class Utils {

    public static void drawOrthagonalLine(SpriteBatch batch, Texture pixelImg, Vector2 start, Vector2 end, float width) {
        Vector2 diff = new Vector2(end).sub(start);
        Direction direction;
        if(Math.abs(diff.x) > Math.abs(diff.y)) {
            direction =  diff.x > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            direction =  diff.y > 0 ? Direction.UP : Direction.DOWN;
        }
        float length = Math.max(Math.abs(diff.x), Math.abs(diff.y));

        drawOrthagonalLine(batch, pixelImg, start, direction, length, width);
    }

    public static void drawOrthagonalLine(SpriteBatch batch, Texture pixelImg, Vector2 start, Direction direction, float length, float width) {
        if(direction == Direction.UP) {
            batch.draw(pixelImg, start.x, start.y, width, length);
        } else if(direction == Direction.DOWN) {
            batch.draw(pixelImg, start.x, start.y, width, -length);
        } else if(direction == Direction.RIGHT) {
            batch.draw(pixelImg, start.x, start.y, length, width);
        } else if(direction == Direction.LEFT) {
            batch.draw(pixelImg, start.x, start.y, -length, width);
        }
    }



    private static ShapeRenderer debugRenderer = null;

    /**
     * http://stackoverflow.com/a/30781020
     */
    public static void DrawDebugLine(Vector2 start, Vector2 end, Matrix4 projectionMatrix)
    {
        DrawDebugLine(start, end, 2, Color.WHITE, projectionMatrix);
    }
    /**
     * http://stackoverflow.com/a/30781020
     */
    public static void DrawDebugLine(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix)
    {
        if(debugRenderer == null)
            debugRenderer = new ShapeRenderer();
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

}
