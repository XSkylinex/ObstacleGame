package com.obstacle.alex.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class ObstacleEnemy {
    private static final float BOUNDS_RADIUS = 0.4f;
    private static final float SIZE = BOUNDS_RADIUS*2;


    private float x;
    private float y;
    private float ySpeed = 0.1f;

    private Circle bounds;

    public ObstacleEnemy(){
        bounds = new Circle(x,y,BOUNDS_RADIUS);
    }

    public void setPosition(float x , float y){
        this.x = x;
        this.y = y;
        updateBounds();

    }

    public void drawDebug(ShapeRenderer renderer){
        renderer.circle(bounds.x,bounds.y,bounds.radius,30);
    }

    private void updateBounds(){
        bounds.setPosition(x,y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update(){
        setPosition(x,y-ySpeed);
    }

    public float getWidth() {
        return SIZE;
    }
}
