package com.boatcorp.boatgame.frameworks;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

public class HealthBar {
    private final ShapeRenderer health;
    private final ShapeRenderer border;

    public HealthBar() {
        border = new ShapeRenderer();
        health = new ShapeRenderer();
    }

    public void draw(@NotNull Vector2 position, float maxHealth, float currentHealth, float scale) {
        float x = position.x;
        float y = position.y;
        health.begin(ShapeRenderer.ShapeType.Line);

        int lineWidth = 20;
        Gdx.gl20.glLineWidth(lineWidth);

        health.setColor(Color.RED);
        health.line(x, y, x + scale*(maxHealth * currentHealth/maxHealth), y);
        health.end();

        border.begin(ShapeRenderer.ShapeType.Line);

        int borderWidth = 4;
        Gdx.gl20.glLineWidth(borderWidth);

        border.setColor(Color.WHITE);
        border.line(x , y + borderWidth, x + scale*maxHealth, y + borderWidth);
        border.line(x, y - borderWidth, x + scale*maxHealth, y - borderWidth);
        border.line(x , y + borderWidth, x, y - borderWidth);
        border.line(x + scale*maxHealth, y + borderWidth, x + scale*maxHealth, y - borderWidth);
        border.end();
    }

    public void setMatrix(Matrix4 camera) {
        health.setProjectionMatrix(camera);
        border.setProjectionMatrix(camera);
    }


    public void dispose() {
        border.dispose();
        health.dispose();
    }
}
