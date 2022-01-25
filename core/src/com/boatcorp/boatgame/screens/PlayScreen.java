package com.boatcorp.boatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boatcorp.boatgame.tools.MapLoader;
import com.boatcorp.boatgame.frameworks.PointSystem;
import com.boatcorp.boatgame.tools.ShapeMaker;
import entities.Player;

import static com.boatcorp.boatgame.screens.Constants.*;

public class PlayScreen implements Screen {

    private final SpriteBatch mBatch;
    private final SpriteBatch mFontBatch;
    private final World mWorld;
    private final Box2DDebugRenderer mB2dr;
    private final OrthographicCamera mCamera;
    private final Viewport mViewport;
    private final Body mPlayer;
    private final MapLoader mMapLoader;
    private final BitmapFont mFont;
    private final PointSystem mPoints;
    private final Texture playerTexture;
    private final Player player;
    private final Sprite playerSprite;

    public PlayScreen() {
        mBatch = new SpriteBatch();
        mFontBatch = new SpriteBatch();
        mWorld = new World(GRAVITY, true);
        mB2dr = new Box2DDebugRenderer();
        mCamera = new OrthographicCamera();
        mCamera.zoom = DEFAULT_ZOOM;
        mViewport = new FitViewport(640 / PPM, 480 / PPM, mCamera);
        mMapLoader = new MapLoader(mWorld);
        playerTexture = new Texture(Gdx.files.internal("Maps/boat1.png"));
        playerSprite = new Sprite(playerTexture);
        player = new Player(playerSprite, 0, 0);
        mPlayer = mMapLoader.getPlayer();
        mFont = new BitmapFont(Gdx.files.internal("fonts/korg.fnt"), Gdx.files.internal("fonts/korg.png"), false);
        mPoints = new PointSystem();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        draw();
    }

    private void draw() {
        // mBatch drawing
        mBatch.setProjectionMatrix(mCamera.combined);


        mB2dr.render(mWorld, mCamera.combined);
        mMapLoader.render(mCamera);

        mBatch.begin();
        mBatch.draw(playerTexture, player.x, player.y);
        mBatch.end();

        // mFontBatch drawing
        mFontBatch.begin();
        mFont.getData().setScale(0.5f);
        String displayPoint = "SCORE:" + mPoints.getPoints();
        mFont.draw(mFontBatch, displayPoint, 8, 472);
        mFontBatch.end();
    }

    private void update(final float delta) {
        mCamera.position.set(player.getPosition(), 0);
        mCamera.update();
        mWorld.step(delta, 6,2);

        // Player updates
        player.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        mCamera.setToOrtho(false,(float)width/16,(float)height/16);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mBatch.dispose();
        mFontBatch.dispose();
        mWorld.dispose();
        mB2dr.dispose();
        mMapLoader.dispose();
    }
}
