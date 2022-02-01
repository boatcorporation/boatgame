package com.boatcorp.boatgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.boatcorp.boatgame.frameworks.HealthBar;
import com.boatcorp.boatgame.frameworks.PointSystem;

import java.util.ArrayList;

public class Player {
    private final SpriteBatch batch;
    private final Texture texture = new Texture(Gdx.files.internal("Entities/boat1.png"));
    private final Sprite sprite;
    private final HealthBar health;
    private final float maxHealth;
    private float currentHealth;
    private final ArrayList<Bullet> bullets;
    private final OrthographicCamera cam;

    enum Direction {
        RIGHT,
        LEFT,
        UP,
        DOWN,
        UP_RIGHT,
        UP_LEFT,
        DOWN_RIGHT,
        DOWN_LEFT,
    }

    private final float ACCELERATION = 3f;
    private static final float MAX_SPEED = 3f;
    private Direction direction = Direction.RIGHT;
    
    private final Vector2 position;
    private final Vector2 velocity;



    public Player(OrthographicCamera camera) {
        position = new Vector2(100,100);
        velocity = new Vector2(0,0);
        batch = new SpriteBatch();
        sprite = new Sprite(texture);
        health = new HealthBar();
        bullets = new ArrayList<>();
        maxHealth = 100;
        currentHealth = 100;
        cam = camera;
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void draw() {
        batch.begin();

        if(direction == Direction.UP) sprite.setRotation(0);
        if(direction == Direction.DOWN) sprite.setRotation(180);
        if(direction == Direction.RIGHT) sprite.setRotation(270);
        if(direction == Direction.LEFT) sprite.setRotation(90);
        if(direction == Direction.UP_RIGHT) sprite.setRotation(330);
        if(direction == Direction.UP_LEFT) sprite.setRotation(45);
        if(direction == Direction.DOWN_RIGHT) sprite.setRotation(225);
        if(direction == Direction.DOWN_LEFT) sprite.setRotation(135);

        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);

        batch.end();

        health.draw(new Vector2(8, 20), maxHealth, currentHealth, 2);
    }

    public void update (final float delta) {
        movement(delta);

        if(velocity.x > 0) {
            // moving right
            if(velocity.y > 0) {
                // moving up
                direction = Direction.UP_RIGHT;
            }
            // moving down or no angle
            else if(velocity.y < 0) {
                // moving down
                direction = Direction.DOWN_RIGHT;
            }
            else {
                // just right
                direction = Direction.RIGHT;
            }
        }
        if(velocity.x < 0) {
            // moving left
            if(velocity.y > 0) {
                // moving up
                direction = Direction.UP_LEFT;
            }
            // moving down or no angle
            else if(velocity.y < 0) {
                // moving down
                direction = Direction.DOWN_LEFT;
            }
            else {
                // just left
                direction = Direction.LEFT;
            }
        }
        if(velocity.x == 0) {
            if(velocity.y < 0) {
                direction = Direction.DOWN;
            }
            else {
                direction = Direction.UP;
            }
        }

        x = MathUtils.clamp(x + xVelocity, 0, 1307 + ((Gdx.graphics.getWidth() - 640) * 0.19f));
        y = MathUtils.clamp(y + yVelocity, 0, 1307 + ((Gdx.graphics.getHeight() - 480) * 0.19f));

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
        float maxSpeed = MAX_SPEED;

        // Horizontal movement
        if(right) {
            if(diagonal) {
                maxSpeed *= diagConstant;
                incrementAmount *= 0.5f;
            }
            velocity.x += ACCELERATION * delta;
            if (velocity.x > maxSpeed) {
                velocity.x = maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else if(left) {
            if(diagonal) {
                maxSpeed *= diagConstant;
                incrementAmount *= 0.5f;
            }
            velocity.x -= ACCELERATION * delta;
            if(velocity.x < -maxSpeed) {
                velocity.x = -maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else {
            // neither are pressed
            if(velocity.x < 0) {
                velocity.x += 2*ACCELERATION * delta;
                if(velocity.x >= 0) {
                    velocity.x = 0.0f;
                }
            }
            else {
                velocity.x -= 2*ACCELERATION * delta;
                if (velocity.x <= 0) {
                    velocity.x = 0.0f;
                }
            }
        }

        // Vertical movement

        if(up) {
            if(diagonal) {
                maxSpeed *= diagConstant;
                incrementAmount *= 0.5f;
            }
            velocity.y += ACCELERATION * delta;
            if (velocity.y > maxSpeed) {
                velocity.y = maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else if(down) {
            if(diagonal) {
                maxSpeed *= diagConstant;
                incrementAmount *= 0.5f;
            }
            velocity.y -= ACCELERATION * delta;
            if(velocity.y < -maxSpeed) {
                velocity.y = -maxSpeed;
            }
            PointSystem.incrementPoint(incrementAmount);
        }
        else {
            // Neither are pressed
            if(velocity.y < 0) {
                velocity.y += 2*ACCELERATION * delta;
                if(velocity.y >= 0) {
                    velocity.y = 0.0f;
                }
            }
            else {
                velocity.y -= 2*ACCELERATION * delta;
                if (velocity.y <= 0) {
                    velocity.y = 0.0f;
                }
            }
        }
    }

    public float getHealth() {
        return currentHealth;
    }

    public Vector2 getVelocity() { return velocity.cpy(); }

    public void takeDamage(int damage) {
        if (this.getHealth()> 0) {
            currentHealth -= damage;
        }
    }

    public void combat(Matrix4 camera, ArrayList<College> colleges) {
        if (Gdx.input.isTouched() || !bullets.isEmpty()) {
            if (bullets.isEmpty()) {
                Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                Vector3 newPosition = cam.unproject(mousePosition);
                float velX = newPosition.x - position.x;
                float velY = newPosition.y - position.y;
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
