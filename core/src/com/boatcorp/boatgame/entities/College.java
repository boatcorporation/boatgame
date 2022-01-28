package com.boatcorp.boatgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.boatcorp.boatgame.frameworks.HealthBar;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class College {
    private final SpriteBatch batch;
    private final Sprite sprite;
    private Vector2 position;
    private ArrayList<Bullet> bullets;
    private final ArrayList<Vector2> diagonalDirections = new ArrayList<>();
    private final ArrayList<Vector2> cardinalDirections = new ArrayList<>();
    private final HealthBar health;
    private final float maxHealth;
    private float currentHealth;

    public College(String college) {
        final String PATH_NAME = "Entities/" + college + ".png";
        final Texture texture = new Texture(Gdx.files.internal(PATH_NAME));
        batch = new SpriteBatch();
        sprite = new Sprite(texture);
        bullets = new ArrayList<>();
        Random rand = new Random();
        position = new Vector2(rand.nextInt(1200), rand.nextInt(1200));
        health = new HealthBar();
        maxHealth = 100;
        currentHealth = 100;

        cardinalDirections.add(new Vector2(5,0));
        cardinalDirections.add(new Vector2(-5,0));
        cardinalDirections.add(new Vector2(0,5));
        cardinalDirections.add(new Vector2(0,-5));

        diagonalDirections.add(new Vector2(4,4));
        diagonalDirections.add(new Vector2(4,4));
        diagonalDirections.add(new Vector2(4,-4));
        diagonalDirections.add(new Vector2(-4,4));
        diagonalDirections.add(new Vector2(-4,-4));
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
        Random rand = new Random();
        ArrayList<Vector2> randDir;
        randDir = (rand.nextBoolean()) ? diagonalDirections: cardinalDirections;
        if (distance < 200) {
            if (bullets.isEmpty()) {
                for (Vector2 direction : randDir) {
                    bullets.add(new Bullet(this.getPosition(), direction));
                }
            }
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                bullet.setMatrix(camera);
                bullet.draw();
                bullet.move();
                if (bullet.outOfRange()) {
                    bullets.remove(bullet);
                }
            }
        }
    }

    public void draw() {
        batch.begin();
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
        batch.end();
        Vector2 currentPos = this.getPosition();
        health.draw(new Vector2(currentPos.x - 9.5f, currentPos.y - 5), maxHealth, currentHealth, 0.5f);
    }

    public void setMatrix(Matrix4 combined) {
        batch.setProjectionMatrix(combined);
        health.setMatrix(combined);
    }

    public void dispose() {
        batch.dispose();
        health.dispose();
        if (!(bullets.isEmpty())) {
            for (Bullet bullet : bullets) {
                bullet.dispose();
            }
        }
    }
}
