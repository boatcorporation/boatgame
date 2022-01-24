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
    private final Vector2 mousePosition = new Vector2();

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

    public Vector2 getMousePosition(){
        mousePosition.x = Gdx.input.getX();
        mousePosition.y = Gdx.input.getY();
        return mousePosition;
    }

    public void moveToMousePosition(){
        if ((x < mousePosition.x) & (y < mousePosition.y)){
            do {
                x = x + 5;
                y = y + 5;        
            }while((x < mousePosition.x) & (y < mousePosition.y));
        }
        else if ((x < mousePosition.x) & (y > mousePosition.y)){
            do {
                x = x + 5;
                y = y - 5;
            }while((x < mousePosition.x) & (y > mousePosition.y));
        }
        else if ((x > mousePosition.x) & (y < mousePosition.y)){
            do {
                x = x - 5;
                y = y + 5;
            }while((x > mousePosition.x) & (y < mousePosition.y));
        }
        else if ((x > mousePosition.x) & (y > mousePosition.y)) {
            do {
                x = x - 5;
                y = y - 5;
            } while ((x > mousePosition.x) & (y > mousePosition.y));
        }
    }

    public Vector2 getPosition(){
        Vector2 v = new Vector2(x,y);
        return v;
    }

}
