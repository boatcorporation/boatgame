package com.boatcorp.boatgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.jetbrains.annotations.NotNull;

public class resultScreen implements Screen {

    private final Game boatGame;
    private static final int WORLD_HEIGHT = Gdx.graphics.getHeight();
    private final SpriteBatch fontBatch;
    private final BitmapFont font;
    private final String victory;
    private final Viewport viewport;

    public resultScreen(boolean win, Game game, @NotNull Screen oldScreen) {
        oldScreen.dispose();
        this.boatGame = game;
        victory = (win) ? "VICTORY" : "GAME OVER";
        viewport = new ExtendViewport(0, WORLD_HEIGHT);
        fontBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/korg.fnt"), Gdx.files.internal("fonts/korg.png"), false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        fontBatch.setProjectionMatrix(viewport.getCamera().combined);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fontBatch.begin();
        font.getData().setScale(0.5f);
        font.draw(fontBatch, this.victory, WORLD_HEIGHT / 2f, WORLD_HEIGHT / (4f/3f));
        font.draw(fontBatch, "Press Any Button", WORLD_HEIGHT / 3f, WORLD_HEIGHT / 4f);
        fontBatch.end();
        checkInputs();
    }

    private void checkInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) ) {
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
        fontBatch.dispose();
        font.dispose();
    }
}
