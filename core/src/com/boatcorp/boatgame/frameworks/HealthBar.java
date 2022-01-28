package com.boatcorp.boatgame.frameworks;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

public class HealthBar {
    private final ShapeRenderer shapeRenderer;

    public HealthBar() {
        shapeRenderer = new ShapeRenderer();
    }

    public void draw(@NotNull Vector2 position, float maxHealth, float currentHealth) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        int lineWidth = 8;
        Gdx.gl20.glLineWidth(lineWidth);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(position.x, position.y, position.x + 100, position.y);
        shapeRenderer.end();
    }
}
