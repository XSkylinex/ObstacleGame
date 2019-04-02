package com.obstacle.alex.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.obstacle.alex.config.GameConfig;

public class ObstacleEnemy extends GameObjectBase{
    private static final float BOUNDS_RADIUS = 0.4f;
    private static final float SIZE = BOUNDS_RADIUS*2;

    private float ySpeed = GameConfig.MEDIUM_ENEMY_SPEED;
    private boolean hits;


    public ObstacleEnemy(){
        super(BOUNDS_RADIUS);
    }


    public void update(){
        setY(getY()-ySpeed);
    }

    //public float getWidth() {
      //  return SIZE;
  //  }

    public boolean isPlayerColliding(Player player){
        Circle circle = player.getBounds();
        //check if playerbounds overlap
        boolean overlaps = Intersector.overlaps(circle,this.getBounds());

        this.hits = overlaps;

        return overlaps;
    }

    public boolean isNotHits(){
        return !hits;
    }

    public void setYenemySpeed(float enemySpeed){
        ySpeed = enemySpeed;
    }
}
