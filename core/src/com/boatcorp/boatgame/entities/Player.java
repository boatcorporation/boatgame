package com.boatcorp.boatgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.boatcorp.boatgame.screens.PlayScreen;

public class Player extends Sprite {
    private float x;
    private float y;
    private Vector2 position;

    public Player(float X,float Y){

    }

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y = y + 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y = y - 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x = x - 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x = x + 4;
        }
    }
    public Vector2 getPosition(){
        Vector2 v = new Vector2(x,y);
        return v;
    }

}
