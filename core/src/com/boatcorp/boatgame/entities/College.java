package com.boatcorp.boatgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class College {
    private final SpriteBatch batch;
    private final Sprite sprite;
    private Vector2 position;
    private ArrayList<Bullet> bullets;

    public College(String college) {
        final String PATH_NAME = "Entities/" + college + ".png";
        final Texture texture = new Texture(Gdx.files.internal(PATH_NAME));
        batch = new SpriteBatch();
        sprite = new Sprite(texture);
        bullets = new ArrayList<>();
        Random rand = new Random();
        position = new Vector2(rand.nextInt(1200), rand.nextInt(1200));
    }

    public Vector2 getPosition() {
        return position.cpy();
    }
    public void setPosition(@NotNull Vector2 pos) {
        position.x = pos.x;
        position.y = pos.y;
    }

    public void combat(@NotNull Vector2 playerPos, Matrix4 camera) {
        double distance = Math.hypot(position.x - playerPos.x, position.y - playerPos.y);
        if (distance < 200) {
            if (bullets.isEmpty()) { bullets.add(new Bullet(this.getPosition())); }
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                bullet.setMatrix(camera);
                bullet.draw();
                bullet.move();
            }
        }
    }

    public void draw() {
        batch.begin();
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
        batch.end();
    }

    public void setMatrix(Matrix4 combined) {
        batch.setProjectionMatrix(combined);
    }

    public void dispose() {
        batch.dispose();
        if (!(bullets.isEmpty())) {
            for (Bullet bullet : bullets) {
                bullet.dispose();
            }
        }
    }
}
