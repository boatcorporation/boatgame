package com.boatcorp.boatgame.frameworks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.boatcorp.boatgame.entities.Player;

public class Hud {
    private Stage stage;
    private FitViewport stageViewport;
    private BitmapFont font;
    private Label pointScore;
    private Player player;
    private ProgressBar.ProgressBarStyle healthBarStyle;
    private ProgressBar healthBar;

    private Drawable getColouredDrawable(float width, float height, Color color) {
        Pixmap pixmap = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();
        return drawable;
    }

    public Hud(SpriteBatch spriteBatch, Player player) {
        this.player = player;
        stageViewport = new FitViewport(640, 480);
        stage = new Stage(stageViewport, spriteBatch);
        font = new BitmapFont(Gdx.files.internal("fonts/korg.fnt"), Gdx.files.internal("fonts/korg.png"), false);

        pointScore = new Label("Points: " + PointSystem.getPoints(), new Label.LabelStyle(font, Color.WHITE));
        pointScore.setWrap(true);
        pointScore.setFontScale(0.5f);
        pointScore.setPosition(8, 40);
        pointScore.setText("Points: " + PointSystem.getPoints());

        healthBarStyle = new ProgressBar.ProgressBarStyle();

        healthBar = new ProgressBar(0, player.getMaxHealth(), 1f, false, healthBarStyle);
        healthBar.setPosition(8, 20);
        healthBar.setValue(player.getMaxHealth());

        int height = 10;
        healthBarStyle.background = getColouredDrawable(player.getMaxHealth(), height+4, Color.WHITE);
        healthBarStyle.knob = getColouredDrawable(player.getMaxHealth(), height, Color.RED);
        healthBarStyle.knobBefore = getColouredDrawable(player.getHealth(), height, Color.RED);
        healthBarStyle.knobBefore.setLeftWidth(2);
        healthBarStyle.knobBefore.setRightWidth(2);
        healthBarStyle.background.setLeftWidth(2);
        healthBarStyle.background.setRightWidth(2);



        stage.addActor(pointScore);
        stage.addActor(healthBar);
    }

    public Stage getStage() { return stage; }

    public Label getPointScore() { return pointScore; }

    public void setPointScore(String newText) {
        pointScore.setText(newText);
    }

    public void setHealthValue(float newValue) {
        healthBar.setValue(newValue);
    }

    /*
    public void updateHealth() {
        healthBar.draw(new Vector2(8, 20), player.getMaxHealth(), player.getHealth(), 2);
    }

     */

    public void dispose() {
        stage.dispose();
    }

}
