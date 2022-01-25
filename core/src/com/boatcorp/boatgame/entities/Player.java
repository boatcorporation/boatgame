package com.boatcorp.boatgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.boatcorp.boatgame.frameworks.PointSystem;

public class Player {
    public Vector2 position = new Vector2();
    public SpriteBatch batch;

    private final int RIGHT = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int UP = 3;
    private final float MAX_SPEED = 3f;

    public final Texture texture = new Texture(Gdx.files.internal("Maps/boat1.png"));
    private final Sprite sprite;
    public float x;
    public float y;
    public float xVelocity = 0.0f;
    public float yVelocity = 0.f;
    public float maxSpeed = 3f;
    public float acceleration = 3f;

    private int direction = UP;

    public Player(float x, float y) {
        batch = new SpriteBatch();


        sprite = new Sprite(texture);

        position.x = x;
        position.y = y;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public Vector2 getPosition() {
        return new Vector2(x,y);
    }

    public void draw() {
        batch.begin();
        if(direction == RIGHT) sprite.setRotation(270);
        if(direction == LEFT) sprite.setRotation(90);
        if(direction == UP) sprite.setRotation(0);
        if(direction == DOWN) sprite.setRotation(180);

        sprite.setPosition(x, y);
        sprite.draw(batch);

        batch.end();
    }

    public void update (final float delta) {
        movement(delta);

        if(xVelocity > 0) {
            direction = RIGHT;
        }
        if(xVelocity < 0) {
            direction = LEFT;
        }
        if(yVelocity < 0) {
            direction = DOWN;
        }
        if(yVelocity > 0) {
            direction = UP;
        }

        x = x + xVelocity;
        y = y + yVelocity;
    }

    private void movement(final float delta) {
        // Horizontal movement
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean horizontal = right || left;
        boolean vertical = up || down;
        boolean diagonal = horizontal && vertical;

        final float DEFAULT_INC = 0.1f;
        float incrementAmount = 0.1f;
        maxSpeed = MAX_SPEED;


        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(diagonal) {
                maxSpeed *= 0.707;
                incrementAmount = 0.05f;
            }
            xVelocity += acceleration * delta;
            if (xVelocity > maxSpeed) {
                xVelocity = maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(diagonal) {
                maxSpeed *= 0.707;
                incrementAmount = 0.05f;
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

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(diagonal) {
                maxSpeed *= 0.707;
                incrementAmount = 0.05f;
            }
            yVelocity += acceleration * delta;
            if (yVelocity > maxSpeed) {
                yVelocity = maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(diagonal) {
                maxSpeed *= 0.707;
                incrementAmount = 0.05f;
            }
            yVelocity -= acceleration * delta;
            if(yVelocity < -maxSpeed) {
                yVelocity = -maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else {
            // neither are pressed
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
}
