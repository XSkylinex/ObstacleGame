package com.obstacle.alex.config;

public class GameConfig {

    public static final float WIDTH = 480f;                     //pixels
    public static final float HEIGHT = 800f;                    //pixels
    public static final float WORLD_WIDTH = 6.0f;               //world units
    public static final float WORLD_HEIGHT = 10.0f;             //world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH/2f;  //world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT/2f; //world units



    public static final float ENEMY_SPAWN_TIME = 0.25f; //world units

    public static final float PLAYER_X_SPEED = 0.25f;


    public static final float HUD_WIDTH = 480f;
    public static final float HUD_HEIGHT = 800f;

    public static final int LIVE_START = 3;

    public static final float SCORE_MAX_TIME =1.25f;







    private GameConfig(){}
}
