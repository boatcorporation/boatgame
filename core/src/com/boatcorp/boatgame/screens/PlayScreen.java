package com.boatcorp.boatgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boatcorp.boatgame.entities.College;
import com.boatcorp.boatgame.entities.Player;
import com.boatcorp.boatgame.frameworks.HealthBar;
import com.boatcorp.boatgame.frameworks.PointSystem;
import com.boatcorp.boatgame.tools.MapLoader;

import java.util.ArrayList;

import static com.boatcorp.boatgame.screens.Constants.*;

public class PlayScreen implements Screen {

    private final SpriteBatch batch;
    private final SpriteBatch fontBatch;
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final MapLoader mapLoader;
    private final BitmapFont font;
    private final Player player;
    private final ArrayList<College> colleges;
    private final HealthBar playerHealth;


    public PlayScreen() {
        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        world = new World(GRAVITY, true);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(640 / PPM, 480 / PPM, camera);
        mapLoader = new MapLoader();
        player = new Player();
        colleges = new ArrayList<>();
        colleges.add(new College("langwith"));
        colleges.add(new College("james"));
        collegeSpread();
        playerHealth = new HealthBar();
        font = new BitmapFont(Gdx.files.internal("fonts/korg.fnt"), Gdx.files.internal("fonts/korg.png"), false);
    }

    private void collegeSpread() {
        // TODO: Implement function
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
        player.setMatrix(camera.combined);
        for (College college : colleges) {
            college.setMatrix(camera.combined);
        }
        batch.setProjectionMatrix(camera.combined);

        b2dr.render(world, camera.combined);

        mapLoader.render(camera);

        for (College college : colleges) {
            college.draw();
            college.combat(player.getPosition(), camera.combined);
        }
        player.draw();
        playerHealth.draw(new Vector2(Gdx.graphics.getWidth() / 2f - 60f, 5),100, 100);


        batch.begin();
        // Empty batch
        batch.end();

        // FontBatch drawing
        fontBatch.begin();
        font.getData().setScale(0.5f);
        String displayPoint = "SCORE:" + PointSystem.getPoints();
        font.draw(fontBatch, displayPoint, 8, 472);

        // USEFUL FOR DEBUGGING
        /*
        Vector2 playerPos = player.getPosition();
        String coords = "X: " + playerPos.x + " Y: " + playerPos.y;
        String cameracoords = "X :" + camera.position.x + "Y: " + camera.position.y;
        font.draw(fontBatch, coords, 8, 440);
        font.draw(fontBatch, cameracoords, 8, 400);
        */
        fontBatch.end();
    }

    private void update(final float delta) {
        camera.zoom = DEFAULT_ZOOM;

        // Get properties of the map from the TileMap
        MapProperties prop = mapLoader.Map.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        // Using `lerping` to slightly lag camera behind player
        float lerp = 5f;
        Vector2 playerPos = player.getPosition();
        camera.position.x += (playerPos.x - camera.position.x) * lerp * delta;
        camera.position.y += (playerPos.y - camera.position.y) * lerp * delta;

        float vw = camera.viewportWidth * camera.zoom;
        float vh = camera.viewportHeight * camera.zoom;

        // clamp the camera position to the size of the map
        camera.position.x = MathUtils.clamp(camera.position.x, vw / 2f, mapWidth * PPM / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, vh / 2f, mapHeight * PPM / 2f);

        camera.update();

        world.step(delta, 6,2);

        player.update(delta);
    }

    @Override
    public void resize(int width, int height) { camera.setToOrtho(false,(float)width/16,(float)height/16); }

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
        player.dispose();
        for (College college : colleges) {
            college.dispose();
        }
    }
}
