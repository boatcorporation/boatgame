package com.boatcorp.boatgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boatcorp.boatgame.frameworks.PointSystem;
import org.jetbrains.annotations.NotNull;

public class MainMenuScreen implements Screen {

    private final Game boatGame;
    private static final int WORLD_HEIGHT = Gdx.graphics.getHeight();
    private final SpriteBatch fontBatch;
    private final BitmapFont font;
    private final Viewport viewport;
    private OrthographicCamera camera;

    public MainMenuScreen(Game boatGame) {
        this.boatGame = boatGame;
        this.fontBatch = new SpriteBatch();
        this.font = new BitmapFont(Gdx.files.internal("fonts/korg.fnt"), Gdx.files.internal("fonts/korg.png"), false);
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        fontBatch.setProjectionMatrix(camera.combined);
        font.getData().setScale(0.5f);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fontBatch.begin();
        String mainString = "Main Menu";
        String enterToStart = "Press Enter to Start";
        GlyphLayout mainGlyph = new GlyphLayout(font, mainString);
        GlyphLayout enterGlyph = new GlyphLayout(font, enterToStart);

        font.draw(fontBatch, mainString, viewport.getScreenWidth() / 2f - mainGlyph.width/2, viewport.getScreenHeight() / (4f/3f));
        font.draw(fontBatch, enterToStart, viewport.getScreenWidth() / 2f - enterGlyph.width/2, viewport.getScreenHeight() / 2f);
        fontBatch.end();
        checkInputs();
        camera.update();

    }

    private void checkInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) ) {
            PointSystem.setPoints(0);
            boatGame.setScreen(new PlayScreen(boatGame));
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        font.dispose();
        fontBatch.dispose();
    }
}
