package com.boatcorp.boatgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.boatcorp.boatgame.BoatGame;
import com.boatcorp.boatgame.frameworks.HealthBar;
import com.boatcorp.boatgame.frameworks.PointSystem;
import com.boatcorp.boatgame.screens.PlayScreen;

import java.util.ArrayList;

public class Player {
    private final SpriteBatch batch;
    private final Texture texture = new Texture(Gdx.files.internal("Entities/boat1.png"));
    private final Sprite sprite;
    private final HealthBar health;
    private final float maxHealth;
    private float currentHealth;
    private ArrayList<Bullet> bullets;
    private final OrthographicCamera cam;

    private final int RIGHT = 1;
    private final int LEFT = 2;
    private final int UP = 3;
    private final int DOWN = 4;
    private final int UP_RIGHT = 5;
    private final int UP_LEFT = 6;
    private final int DOWN_RIGHT = 7;
    private final int DOWN_LEFT = 8;
    private int direction = RIGHT;
    private static final float MAX_SPEED = 3f;


    private float x;
    private float y;
    private float xVelocity = 0.0f;
    private float yVelocity = 0.f;
    private float maxSpeed = 3f;
    private final float acceleration = 3f;



    public Player(OrthographicCamera camera) {
        batch = new SpriteBatch();
        sprite = new Sprite(texture);
        health = new HealthBar();
        bullets = new ArrayList<>();
        maxHealth = 100;
        currentHealth = 100;
        cam = camera;
    }

    public Vector2 getPosition() {
        return new Vector2(x,y);
    }

    public void draw() {
        batch.begin();

        if(direction == UP) sprite.setRotation(0);
        if(direction == DOWN) sprite.setRotation(180);
        if(direction == RIGHT) sprite.setRotation(270);
        if(direction == LEFT) sprite.setRotation(90);
        if(direction == UP_RIGHT) sprite.setRotation(330);
        if(direction == UP_LEFT) sprite.setRotation(45);
        if(direction == DOWN_RIGHT) sprite.setRotation(225);
        if(direction == DOWN_LEFT) sprite.setRotation(135);

        sprite.setPosition(x, y);
        sprite.draw(batch);

        batch.end();

        health.draw(new Vector2(8, 440), maxHealth, currentHealth, 2);
    }

    public void update (final float delta) {
        movement(delta);

        if(xVelocity > 0) {
            // moving right
            if(yVelocity > 0) {
                // moving up
                direction = UP_RIGHT;
            }
            // moving down or no angle
            else if(yVelocity < 0) {
                // moving down
                direction = DOWN_RIGHT;
            }
            else {
                // just right
                direction = RIGHT;
            }
        }
        if(xVelocity < 0) {
            // moving left
            if(yVelocity > 0) {
                // moving up
                direction = UP_LEFT;
            }
            // moving down or no angle
            else if(yVelocity < 0) {
                // moving down
                direction = DOWN_LEFT;
            }
            else {
                // just left
                direction = LEFT;
            }
        }
        if(xVelocity == 0) {
            if(yVelocity < 0) {
                direction = DOWN;
            }
            else {
                direction = UP;
            }
        }

        x = x + xVelocity;
        y = y + yVelocity;
    }

    private void movement(final float delta) {
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        boolean horizontal = right || left;
        boolean vertical = up || down;
        boolean diagonal = horizontal && vertical;

        float incrementAmount = 0.1f;
        float diagConstant = 0.7071f;
        maxSpeed = MAX_SPEED;

        // Horizontal movement
        if(right) {
            if(diagonal) {
                maxSpeed *= diagConstant;
                incrementAmount *= 0.5f;
            }
            xVelocity += acceleration * delta;
            if (xVelocity > maxSpeed) {
                xVelocity = maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else if(left) {
            if(diagonal) {
                maxSpeed *= diagConstant;
                incrementAmount *= 0.5f;
            }
            xVelocity -= acceleration * delta;
            if(xVelocity < -maxSpeed) {
                xVelocity = -maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else {
            // neither are pressed
            if(xVelocity < 0) {
                xVelocity += 2*acceleration * delta;
                if(xVelocity >= 0) {
                    xVelocity = 0.0f;
                }
            }
            else {
                xVelocity -= 2*acceleration * delta;
                if (xVelocity <= 0) {
                    xVelocity = 0.0f;
                }
            }
        }

        // Vertical movement

        if(up) {
            if(diagonal) {
                maxSpeed *= diagConstant;
                incrementAmount *= 0.5f;
            }
            yVelocity += acceleration * delta;
            if (yVelocity > maxSpeed) {
                yVelocity = maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else if(down) {
            if(diagonal) {
                maxSpeed *= diagConstant;
                incrementAmount *= 0.5f;
            }
            yVelocity -= acceleration * delta;
            if(yVelocity < -maxSpeed) {
                yVelocity = -maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else {
            // Neither are pressed
            if(yVelocity < 0) {
                yVelocity += 2*acceleration * delta;
                if(yVelocity >= 0) {
                    yVelocity = 0.0f;
                }
            }
            else {
                yVelocity -= 2*acceleration * delta;
                if (yVelocity <= 0) {
                    yVelocity = 0.0f;
                }
            }
        }
    }

    public float getHealth() {
        return currentHealth;
    }

    public Vector2 getVelocity() { return new Vector2(this.xVelocity, this.yVelocity); }

    public void takeDamage(int damage) {
        if (this.getHealth()> 0) {
            currentHealth -= damage;
        }
    }

    public void combat(Matrix4 camera, ArrayList<College> colleges) {
        if (Gdx.input.isTouched() || !bullets.isEmpty()) {
            if (bullets.isEmpty()) {
                Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                cam.unproject(mousePosition);
                float velX = mousePosition.x - x;
                float velY = mousePosition.y - y;
                float length = (float) Math.sqrt(velX * velX + velY * velY);
                if (length != 0) {
                    velX = velX * 10 / length;
                    velY = velY * 10 / length;
                }
                Vector2 adjustedPos = this.getPosition().add(10,10);
                Vector2 bulletVelocity = new Vector2(velX, velY);

                // Sets bullet velocity to current velocity of boat x2, ensuring no division by zero errors

                bullets.add(new Bullet(adjustedPos, bulletVelocity));
            }
            for (int i = 0; i < bullets.size(); i++) {
                // Draw and move bullets and check for collisions
                Bullet bullet = bullets.get(i);
                bullet.setMatrix(camera);
                bullet.draw();
                bullet.move();
                if (bullet.outOfRange(200)) { bullets.remove(bullet); }
                for (College college : colleges) {
                    if (bullet.hitTarget(college.getPosition())) {
                        bullets.remove(bullet);
                        college.takeDamage(10);
                    }
                }
            }
        }
    }

    public void setMatrix(Matrix4 combined) {
        batch.setProjectionMatrix(combined);
    }

    public void dispose() {
        health.dispose();
        batch.dispose();
        if (!bullets.isEmpty()) {
            for (Bullet bullet : bullets) {
                bullet.dispose();
            }
        }
    }
}
