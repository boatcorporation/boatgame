package com.boatcorp.boatgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private final Sprite sprite;

    public float x;
    public float y;

    public Player(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public Vector2 getPosition() {
        return new Vector2(this.x, this.y);
    }

    public void update(final float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) this.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) this.x += 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) this.y += 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) this.y -= 200 * Gdx.graphics.getDeltaTime();
    }

}
