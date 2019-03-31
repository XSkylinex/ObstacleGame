package com.obstacle.alex.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class GameObjectBase {

    private float x;
    private float y;

    private Circle bounds;

    public GameObjectBase(float radius) {
        this.bounds = new Circle(x,y,radius);
    }

    public void setPosition(float x , float y){
        this.x = x;
        this.y = y;
        updateBounds();

    }

    public void drawDebug(ShapeRenderer renderer){
        renderer.circle(bounds.x,bounds.y,bounds.radius,30);
    }

    public void updateBounds(){
        bounds.setPosition(x,y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public Circle getBounds() {
        return bounds;
    }
}
