package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Texture socketImg, generatorImg, pixelImg;
    TextureRegion pixelReg;
    Sprite pixelSprite;
    Level level;
    float red = 0.0f;
    float green = 0.02734375f;

    public MyGdxGame() {

    }

    /// <application-adapter>
    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.socketImg = new Texture("socket.png");
        this.generatorImg = new Texture("generator.png");
        this.pixelImg = new Texture("pixel.png");
        this.pixelReg = new TextureRegion(
                new Texture("pixel.png"),
                0.0f, 0.0f, 1.0f, 1.0f
        );
        this.pixelSprite = new Sprite(
                new Texture("pixel.png"),
                0, 0, 1, 1
        );

        Gdx.input.setInputProcessor(this);

        /*
        TODO use non-continuous rendering to save on battery:
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();

        If continuous rendering is set to false, the render() method will be called only when the following things happen.
            An input event is triggered
            Gdx.graphics.requestRendering() is called
            Gdx.app.postRunnable() is called

        */

        this.level = Level.generateLevel2();
    }

    @Override
    public void render () {
        final int screenHeight = Gdx.graphics.getHeight();
        final int screenWidth = Gdx.graphics.getWidth();
        final int drawAreaSize = Math.min(screenHeight, screenWidth);

        final float SOCKET_SIZE = 0.2f * drawAreaSize;

        //avoid stretching during window-resizes
        //TODO only do this on actual resize
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, screenWidth, screenHeight);
        batch.setProjectionMatrix(matrix);

        Gdx.gl.glClearColor(this.red, this.green, 0.16796875f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears the screen

        batch.begin();

        for(Cable cable : this.level.getCables()) {
            for(LineSegment line : cable.getCableSegments()) {
                Utils.drawOrthagonalLine(
                        batch,
                        pixelImg,
                        new Vector2(line.start).scl(drawAreaSize),
                        new Vector2(line.end).scl(drawAreaSize),
                        2.0f);
            }

        }

        for(Socket socket : this.level.getSockets()) {
            Vector2 center = socket.getCenter();
            batch.draw(
                    socketImg,
                    center.x * drawAreaSize - SOCKET_SIZE / 2,
                    center.y * drawAreaSize - SOCKET_SIZE / 2,
                    SOCKET_SIZE,
                    SOCKET_SIZE
            );
        }
        for(Generator generator : this.level.getGenerators()) {
            Vector2 center = generator.getCenter();
            batch.draw(
                    generatorImg,
                    center.x * drawAreaSize - SOCKET_SIZE / 2,
                    center.y * drawAreaSize - SOCKET_SIZE / 2,
                    SOCKET_SIZE,
                    SOCKET_SIZE
            );
        }

        batch.end();
    }
    /// </application-adapter>

    /// <input-processor>
    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {

        //can be done better
        final int screenHeight = Gdx.graphics.getHeight();
        final int screenWidth = Gdx.graphics.getWidth();
        final int drawAreaSize = Math.min(screenHeight, screenWidth);
        ///

        System.out.println();
        System.out.println("new round");

        for(Socket soc : level.getSockets()){

            boolean socTouched = soc.socketTouched(new Vector2((float)x/drawAreaSize, (float)(1 - (float)y/drawAreaSize)), 0.2f);

            //if the person touched the powered socket, it will vibrate
            if (!soc.isPowered() && socTouched){

                System.out.println("right socket!");
                this.red = 0.0f;
                this.green = 1.0f;
                this.level = Level.generateLevel4();
                return true; //lever accomplished!

            } else if (soc.isPowered() && socTouched){
                //level accomplished
                //Gdx.input.vibrate(new long[] { 0, 200, 200, 200}, -1); //or simply Gdx.input.vibrate(2000); to vibrate just for 2s
                System.out.println("wrong socket!");
                this.red = 1.0f;
                this.green = 1.0f;
                return true; //loose

            } else {
                //didn't touch the socket
                System.out.println("no socket touched");
                this.red = 0.0f;
                this.green = 0.02734375f;
            }
        }

        return true;
    }
    @Override
    public boolean keyDown (int keycode) { return false; }

    @Override
    public boolean keyUp (int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped (char character) {
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }
    /// </input-processor>
}
