package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

    private final static float DEFAULT_GREEN = 0.02734375f;
    private final static float DEFAULT_BLUE = 0.16796875f;
    private final static Color DEFAULT_BG = new Color(0.0f, DEFAULT_GREEN, DEFAULT_BLUE, 1.0f);
    private final static Color ELECTROCUTED_BG = new Color(1.0f, 1.0f, DEFAULT_BLUE, 1.0f);
    private final static Color SUCCESS_BG = new Color(0.0f, 1.0f, DEFAULT_BLUE, 1.0f);
    private final static Color TIMEOUT_BG = new Color(1.0f, 0.0f, DEFAULT_BLUE, 1.0f);
    private final static int SUCCESS_ANIMATION_DURATION = 1000; //in ms
    private final static int FAILURE_ANIMATION_DURATION = 1300; //in ms
    private final static int TIMEOUT_ANIMATION_DURATION = 5000; //in ms


    SpriteBatch batch;
    Texture socketImg, generatorImg, switchImg, pixelImg;
    TextureRegion pixelReg;
    Sprite pixelSprite;
    Level level;
    int levelNumber = 1;
    float red = 0.0f;
    float green = 0.02734375f;
    private long timeOfSuccess = -1;
    private long timeOfFailure = -1;
    private Color bgColor = DEFAULT_BG;
    private float percentageTimeRemaining = 1.0f;
    private boolean timoutVibrationStarted = false;


    public MyGdxGame() {

    }

    /// <application-adapter>
    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.socketImg = new Texture("socket.png");
        this.generatorImg = new Texture("generator.png");
        this.switchImg = new Texture("switch_top_to_left.png");
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

        this.level = Level.generateLevel(levelNumber);
    }

    @Override
    public void render () {
        this.update();

        final int screenHeight = Gdx.graphics.getHeight();
        final int screenWidth = Gdx.graphics.getWidth();
        final int drawAreaSize = Math.min(screenHeight, screenWidth);

        final float SOCKET_SIZE = 0.2f * drawAreaSize;

        //avoid stretching during window-resizes
        //TODO only do this on actual resize
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, screenWidth, screenHeight);
        batch.setProjectionMatrix(matrix);

        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears the screen

        batch.begin();

        //draw timeslider for remaining time
        Utils.drawOrthagonalLine(
                batch,
                pixelImg,
                new Vector2(0.0f, 0.0f).scl(drawAreaSize),
                new Vector2(percentageTimeRemaining, 0.0f).scl(drawAreaSize),
                10.0f
        );

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
        for(Switch sw : this.level.getSwitches()) {
            Vector2 center = sw.getCenter();
            batch.draw(
                    switchImg,
                    center.x * drawAreaSize - SOCKET_SIZE / 2,
                    center.y * drawAreaSize - SOCKET_SIZE / 2,
                    SOCKET_SIZE,
                    SOCKET_SIZE
            );
    }

        batch.end();
    }
    /// </application-adapter>

    public void update() {

        if(isAfterSuccess()){
            //success animation has just finished
            this.timeOfSuccess = -1;
            this.levelNumber++;
            this.level = Level.generateLevel(levelNumber);

        } else if(isAfterFailure()) {
            //failure animation has just finished
            this.timeOfFailure = -1;
            this.level.reset();
        } else if(isAfterTimeout()) {
            this.timoutVibrationStarted = false;
            this.level.reset();
        }

        if(inSuccessAnimation()) {
            this.bgColor = SUCCESS_BG;

        } else if (inDefeatAnimation()) {
            this.bgColor = ELECTROCUTED_BG;

        } else if (inTimeoutAnimation()) {
            if(!this.timoutVibrationStarted) {
                this.timoutVibrationStarted = true;
                Gdx.input.vibrate(new long[] { 0, 300, 300, 300, 300, 300}, -1);
            }
            this.bgColor = TIMEOUT_BG;
            this.percentageTimeRemaining = 1.0f -
                    (System.currentTimeMillis() - level.startTime - level.maxTime) * 1.0f /
                            TIMEOUT_ANIMATION_DURATION;

        } else {
            this.bgColor = DEFAULT_BG;
            this.percentageTimeRemaining = 1.0f - (System.currentTimeMillis() - level.startTime) * 1.0f / level.maxTime;
        }
    }


    private boolean inSuccessAnimation() {
        return this.timeOfSuccess > 0 &&
                (System.currentTimeMillis() - this.timeOfSuccess) <= SUCCESS_ANIMATION_DURATION;

    }
    private boolean isAfterSuccess() {
       return this.timeOfSuccess > 0 &&
               (System.currentTimeMillis() - this.timeOfSuccess) > SUCCESS_ANIMATION_DURATION;
    }


    private boolean isAfterFailure() {
        return this.timeOfFailure > 0 &&
                (System.currentTimeMillis() - this.timeOfFailure) > FAILURE_ANIMATION_DURATION;
    }
    private boolean inDefeatAnimation() {
        return this.timeOfFailure > 0 &&
                (System.currentTimeMillis() - this.timeOfFailure) <= FAILURE_ANIMATION_DURATION;
    }


    private boolean inTimeoutAnimation() {
        return System.currentTimeMillis() > level.startTime + level.maxTime;
    }
    private boolean isAfterTimeout() {
        return System.currentTimeMillis() > level.startTime + level.maxTime + TIMEOUT_ANIMATION_DURATION;
    }

    /// <input-processor>
    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {

        //TODO for degugging, remove me
        Switch.upIs = (short)((Switch.upIs + 1) % 4);
        for(Socket s : level.getSockets())
            System.out.println(System.currentTimeMillis() +
                    ":: UP=" + Switch.upIs +
                    " Socket is powered? " + s.isPowered());

        //while success or defeat are playing, we shouldn't take inputs
        if(inSuccessAnimation() || inDefeatAnimation() || inTimeoutAnimation()) {
            return true;
        }

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

                this.success();
                return true; //lever accomplished!

            } else if (soc.isPowered() && socTouched){
                this.failure();
                return true; //loose

            } else {
                //didn't touch the socket
                System.out.println("no socket touched");
                this.bgColor = DEFAULT_BG;
            }
        }

        return true;
    }

    public void success() {
        System.out.println("right socket!");
        this.bgColor = SUCCESS_BG;
        this.timeOfSuccess = System.currentTimeMillis();
        //levelNumber++;
        //this.level = Level.generateLevel(levelNumber);
    }
    public void failure() {
        //level accomplished
        //Gdx.input.vibrate(new long[] { 0, 200, 200, 200}, -1); //or simply Gdx.input.vibrate(2000); to vibrate just for 2s
        System.out.println("wrong socket!");
        Gdx.input.vibrate(FAILURE_ANIMATION_DURATION);
        this.timeOfFailure = System.currentTimeMillis();
        this.bgColor = ELECTROCUTED_BG;
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
