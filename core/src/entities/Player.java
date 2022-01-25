package entities;

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

    private int RIGHT = 0;
    private int DOWN = 1;
    private int LEFT = 2;
    private int UP = 3;

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
        horizontalMovement(delta);
        verticalMovement(delta);

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

    private void horizontalMovement(final float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xVelocity += acceleration * delta;
            if (xVelocity > maxSpeed) {
                xVelocity = maxSpeed;
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xVelocity -= acceleration * delta;
            if(xVelocity < -maxSpeed) {
                xVelocity = -maxSpeed;
            }
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
    }

    private void verticalMovement(final float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yVelocity += acceleration * delta;
            if (yVelocity > maxSpeed) {
                yVelocity = maxSpeed;
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yVelocity -= acceleration * delta;
            if(yVelocity < -maxSpeed) {
                yVelocity = -maxSpeed;
            }
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
