package com.obstacle.alex.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

public class ObstacleEnemy extends GameObjectBase{
    private static final float BOUNDS_RADIUS = 0.4f;
    private static final float SIZE = BOUNDS_RADIUS*2;

    private float ySpeed = 0.1f;


    public ObstacleEnemy(){
        super(BOUNDS_RADIUS);
    }


    public void update(){
        setY(getY()-ySpeed);
    }

    public float getWidth() {
        return SIZE;
    }

    public Boolean isPlayerColliding(Player player){
        Circle circle = player.getBounds();
        //check if playerbounds overlap
        return Intersector.overlaps(player.getBounds(),this.getBounds());
    }
}
