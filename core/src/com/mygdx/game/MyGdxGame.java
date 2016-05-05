package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Texture img;
    float red = 1.0f;

    public MyGdxGame() {

    }

    /// <application-adapter>
    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");

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

        Level.generateLevel2();
    }

    @Override
    public void render () {
        float blue = 0.0f;
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            blue = 1.0f;

        Gdx.gl.glClearColor(this.red, blue, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
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
