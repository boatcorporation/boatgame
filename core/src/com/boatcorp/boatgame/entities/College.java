package com.boatcorp.boatgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.boatcorp.boatgame.frameworks.HealthBar;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class College {
    private final SpriteBatch batch;
    private final Sprite sprite;
    private Vector2 position;
    private ArrayList<Bullet> bullets;
    private final ArrayList<Vector2> diagonalDirections;
    private final ArrayList<Vector2> cardinalDirections;
    private final ArrayList<Vector2> rotatingDirections;
    private final ArrayList<ArrayList<Vector2>> attackPatterns;
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

        cardinalDirections = new ArrayList<>();
        cardinalDirections.add(new Vector2(5,0));
        cardinalDirections.add(new Vector2(-5,0));
        cardinalDirections.add(new Vector2(0,5));
        cardinalDirections.add(new Vector2(0,-5));

        diagonalDirections = new ArrayList<>();
        diagonalDirections.add(new Vector2(4,4));
        diagonalDirections.add(new Vector2(4,4));
        diagonalDirections.add(new Vector2(4,-4));
        diagonalDirections.add(new Vector2(-4,4));
        diagonalDirections.add(new Vector2(-4,-4));

        rotatingDirections = new ArrayList<>();
        rotatingDirections.add(new Vector2(5, 0));
        rotatingDirections.add(new Vector2(4, 4));
        rotatingDirections.add(new Vector2(-5, 0));
        rotatingDirections.add(new Vector2(4, 4));
        rotatingDirections.add(new Vector2(4, -4));
        rotatingDirections.add(new Vector2(0, 5));
        rotatingDirections.add(new Vector2(-4, 4));
        rotatingDirections.add(new Vector2(-4, -4));
        rotatingDirections.add(new Vector2(0, -5));


        attackPatterns = new ArrayList<>();
        attackPatterns.add(cardinalDirections);
        attackPatterns.add(rotatingDirections);
        attackPatterns.add(diagonalDirections);

    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(@NotNull Vector2 pos) {
        position.x = pos.x;
        position.y = pos.y;
    }

    public void combat(@NotNull Vector2 playerPos, Matrix4 camera, Player player) {
        double distance = Math.hypot(position.x - playerPos.x, position.y - playerPos.y);
        Random rand = new Random();
        ArrayList<Vector2> randDir;

        // Only begins combat when the player is close enough and the college isn't defeated
        if (distance < 200) {
            if (bullets.isEmpty()) {
                // Randomly choose from set attack patterns
                int random_number = rand.nextInt(attackPatterns.size());
                randDir = attackPatterns.get(random_number);
                for (Vector2 direction : randDir) {
                    bullets.add(new Bullet(this.getPosition(), direction));
                }
            }
            for (int i = 0; i < bullets.size(); i++) {
                // Draw and move bullets and check for collisions
                Bullet bullet = bullets.get(i);
                bullet.setMatrix(camera);
                bullet.draw();
                bullet.move();
                if (bullet.outOfRange(300)) { bullets.remove(bullet); }
                if (bullet.hitTarget(player.getPosition())) {
                    bullets.remove(bullet);
                    player.takeDamage(10);
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

    public float getHealth() {
        return this.currentHealth;
    }

    public boolean isAlive() {
        return this.currentHealth > 0;
    }

    public void takeDamage(int damage) {
        if (this.getHealth() > 0) {
            currentHealth -= damage;
        }
    }

    public void setMatrix(Matrix4 combined) {
        batch.setProjectionMatrix(combined);
        health.setMatrix(combined);
    }

    public void dispose() {
        batch.dispose();
        health.dispose();
        if (!bullets.isEmpty()) {
            for (Bullet bullet : bullets) {
                bullet.dispose();
            }
        }
    }
}
