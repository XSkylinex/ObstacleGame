package com.obstacle.alex.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.obstacle.alex.config.GameConfig;

public class Player extends GameObjectBase {
    private static final float BOUNDS_RADIUS = 0.4f;
    private static final float SIZE = BOUNDS_RADIUS*2;


    public Player(){
        super(BOUNDS_RADIUS);
    }

    public void update(){
        float xSpeed = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            xSpeed = GameConfig.PLAYER_X_SPEED;
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            xSpeed = -GameConfig.PLAYER_X_SPEED;
        }

        setX(getX()+xSpeed);
        updateBounds();
    }

    public float getWidth() {
        return SIZE;
    }
}
