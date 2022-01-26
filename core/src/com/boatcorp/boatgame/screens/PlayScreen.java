package com.boatcorp.boatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boatcorp.boatgame.entities.Player;
import com.boatcorp.boatgame.frameworks.PointSystem;
import com.boatcorp.boatgame.tools.MapLoader;

import static com.boatcorp.boatgame.screens.Constants.*;

public class PlayScreen implements Screen {

    private final SpriteBatch batch;
    private final SpriteBatch fontBatch;
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final OrthographicCamera camera;
    private final MapLoader mapLoader;
    private final BitmapFont font;
    private final Player player;

    public PlayScreen() {
        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        world = new World(GRAVITY, true);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.zoom = DEFAULT_ZOOM;
        Viewport viewport = new FitViewport(640 / PPM, 480 / PPM, camera);
        mapLoader = new MapLoader();

        player = new Player(0, 0);
        font = new BitmapFont(Gdx.files.internal("fonts/korg.fnt"), Gdx.files.internal("fonts/korg.png"), false);
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
        // Batch drawing
        player.batch.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);


        b2dr.render(world, camera.combined);

        mapLoader.render(camera);

        player.draw();

        batch.begin();
        // Empty batch
        batch.end();

        // FontBatch drawing
        fontBatch.begin();
        font.getData().setScale(0.5f);
        String displayPoint = "SCORE:" + PointSystem.getPoints();
        font.draw(fontBatch, displayPoint, 8, 472);
        String coords = "X: " + player.x + " Y: " + player.y;
        font.draw(fontBatch, coords, 8, 400);
        fontBatch.end();
    }

    private void update(final float delta) {
        MapProperties prop = mapLoader.Map.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int mapLeft = 0;
        int mapRight = mapWidth;
        int mapBottom = 0;
        int mapTop = mapHeight;

        float cameraHalfWidth = camera.viewportWidth * DEFAULT_ZOOM * 0.5f;
        float cameraHalfHeight = camera.viewportHeight * DEFAULT_ZOOM * 0.5f;

        camera.position.set(player.getPosition(), 0);

        float cameraLeft = camera.position.x - cameraHalfWidth;
        float cameraRight = camera.position.x + cameraHalfWidth;
        float cameraBottom = camera.position.y - cameraHalfHeight;
        float cameraTop = camera.position.y + cameraHalfHeight;

        // Horizontal axis
        if(mapWidth < camera.viewportWidth)
        {
            camera.position.x = mapRight * 0.5f;
        }
        else if(cameraLeft <= mapLeft)
        {
            camera.position.x = mapLeft + cameraHalfWidth;
        }
        else if(cameraRight >= mapRight)
        {
            camera.position.x = mapRight - cameraHalfWidth;
        }

        // Vertical axis
        if(mapHeight < camera.viewportHeight)
        {
            camera.position.y = mapTop * 0.5f;
        }
        else if(cameraBottom <= mapBottom)
        {
            camera.position.y = mapBottom + cameraHalfHeight;
        }
        else if(cameraTop >= mapTop)
        {
            camera.position.y = mapTop - cameraHalfHeight;
        }

        camera.update();
        world.step(delta, 6,2);

        // Player updates
        player.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,(float)width/16,(float)height/16);
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
        batch.dispose();
        fontBatch.dispose();
        world.dispose();
        b2dr.dispose();
        mapLoader.dispose();
    }
}
