package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Texture socketImg;
    Texture generatorImg;
    Level level;
    float red = 0.0f;

    public MyGdxGame() {

    }

    /// <application-adapter>
    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.socketImg = new Texture("socket.png");
        this.generatorImg = new Texture("generator.png");

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

        final float SOCKET_SIZE = 0.1f * drawAreaSize;

        //avoid stretching during window-resizes
        //TODO only do this on actual resize
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, screenWidth, screenHeight);
        batch.setProjectionMatrix(matrix);

        Gdx.gl.glClearColor(this.red, 0.02734375f, 0.16796875f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears the screen

        batch.begin();

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

        //batch.draw(socketImg, 0.0f, 0.0f);
        //batch.draw(socketImg, 0.0f, 0.0f, 100.0f, 100.0f);
        //batch.draw(socketImg, 0.0f, 0.0f, SOCKET_SIZE, SOCKET_SIZE);
        batch.end();
    }
    /// </application-adapter>

    /// <input-processor>
    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        if(this.red > 0)
            this.red = 0.0f;
        else
            this.red = 1.0f;
        System.out.println("clicked");
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
