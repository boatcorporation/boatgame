package com.boatcorp.boatgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import static com.boatcorp.boatgame.screens.Constants.BULLET_PATH;

public class Bullet {
    private final SpriteBatch batch;
    private final Sprite sprite;
    private Vector2 position;

    public Bullet(Vector2 position) {
        final Texture texture = new Texture(Gdx.files.internal(BULLET_PATH));
        batch = new SpriteBatch();
        sprite = new Sprite(texture);
        this.position = position;
    }

    public void draw() {
        batch.begin();
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
        batch.end();
    }

    public Vector2 getPosition() {
        return position.cpy();
    }
    public void setPosition(@NotNull Vector2 pos) {
        position.x = pos.x;
        position.y = pos.y;
    }

    public void move() {
        Vector2 currentPos = this.getPosition();
        currentPos.x += 5;
        this.setPosition(currentPos);
    }

    public void setMatrix(Matrix4 combined) {
        batch.setProjectionMatrix(combined);
    }

    public void dispose() {
        batch.dispose();
    }
}
