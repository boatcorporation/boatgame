package com.boatcorp.boatgame.frameworks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.boatcorp.boatgame.screens.Constants;

public class Hud {
    private Stage stage;
    private FitViewport stageViewport;
    private BitmapFont font;
    private Label pointScore;

    public Hud(SpriteBatch spriteBatch) {
        stageViewport = new FitViewport(640, 480);
        stage = new Stage(stageViewport, spriteBatch);
        font = new BitmapFont(Gdx.files.internal("fonts/korg.fnt"), Gdx.files.internal("fonts/korg.png"), false);

        pointScore = new Label("Points: " + PointSystem.getPoints(), new Label.LabelStyle(font, Color.WHITE));
        pointScore.setWrap(true);
        pointScore.setFontScale(0.5f);
        pointScore.setPosition(8, 40);
        pointScore.setText("Points: " + PointSystem.getPoints());
        stage.addActor(pointScore);
    }

    public Stage getStage() { return stage; }

    public Label getPointScore() { return pointScore; }

    public void setPointScore(String newText) {
        pointScore.setText(newText);
    }

    public void dispose() {
        stage.dispose();
    }

}
