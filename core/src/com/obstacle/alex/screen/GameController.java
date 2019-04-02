package com.obstacle.alex.screen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.obstacle.alex.config.GameConfig;
import com.obstacle.alex.entity.ObstacleEnemy;
import com.obstacle.alex.entity.Player;

import java.util.ArrayList;

public class GameController {
    private static final Logger log = new Logger(GameController.class.getName(),Logger.DEBUG);

    private Player player;
    private ArrayList<ObstacleEnemy> obstacleEnemies = new ArrayList<ObstacleEnemy>();
    private float obstacleEnemyTime;
    private int lives = GameConfig.LIVE_START;
    private float scoreTimer;
    private int score;
    private int playerDisplayScore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;


    public GameController() {
        initAll();
    }


    private void initAll(){
        this.player = new Player();
        float startPlayerX = GameConfig.WORLD_WIDTH/2f;
        float startPlayerY = 1;
        player.setPosition(startPlayerX,startPlayerY);

    }

    public void update(float delta){
        if(isGameOver()){

        }
        updatePayer();
        updateEnemyObstacles(delta);
        updateScore(delta);
        updatePlayerDisplayScore(delta);

        if(isPlayerColliadingTureOrFalse()){
            this.lives--;
        }
    }

    private void updatePayer(){
        player.update();
        // block player from leave the world
        stopPlayerLeaveWorld();


    }

    private void stopPlayerLeaveWorld(){
        float playerX = MathUtils.clamp(
                player.getX(), // value
                player.getWidth()/2f, // less to min
                GameConfig.WORLD_WIDTH - player.getWidth()/2f ); // more the maximum

        if(playerX < player.getWidth()/2f){
            playerX = player.getWidth()/2f;
        }else if(playerX > GameConfig.WORLD_WIDTH - player.getWidth()/2f){
            playerX = GameConfig.WORLD_WIDTH - player.getWidth()/2f;
        }

        player.setPosition(playerX,player.getY());
    }


    private void updateEnemyObstacles(float delta){
        for(ObstacleEnemy obstacleEnemies : obstacleEnemies){
            obstacleEnemies.update();
        }
        createNewEnemyObstacles(delta);
    }

    private void createNewEnemyObstacles(float delta){
        this.obstacleEnemyTime +=delta;
        if(this.obstacleEnemyTime > GameConfig.ENEMY_SPAWN_TIME){
            float min = 0f;
            float max = GameConfig.WORLD_WIDTH;
            float enemyObstacleX = MathUtils.random(min,max);
            float enemyObstacleY = GameConfig.WORLD_HEIGHT;

            ObstacleEnemy obstacleEnemy = new ObstacleEnemy();

            obstacleEnemy.setYenemySpeed(difficultyLevel.getEnemySpeed());

            obstacleEnemy.setPosition(enemyObstacleX,enemyObstacleY);

            this.obstacleEnemies.add(obstacleEnemy);
            this.obstacleEnemyTime = 0f;
        }
    }

    private Boolean isPlayerColliadingTureOrFalse(){

        for(ObstacleEnemy obstacleEnemy : obstacleEnemies){
            if(obstacleEnemy.isNotHits() && obstacleEnemy.isPlayerColliding(player)){
                return true;
            }
        }
        return false;
    }

    private void updateScore(float delta){
        this.scoreTimer += delta;

        if(scoreTimer >= GameConfig.SCORE_MAX_TIME){
            score+=MathUtils.random(1,5);
            scoreTimer = 0.0f;
        }
    }

    private void updatePlayerDisplayScore(float delta){
        if(playerDisplayScore < score){
            playerDisplayScore = Math.min(
                    score,
                    playerDisplayScore+(int)(60*delta)
            );
        }
    }

    private Boolean isGameOver(){
        return lives<=0;
    }


    public Player getPlayer() {
        return player;
    }

    public ArrayList<ObstacleEnemy> getObstacleEnemies() {
        return obstacleEnemies;
    }

    public int getLives() {
        return lives;
    }

    public int getPlayerDisplayScore() {
        return playerDisplayScore;
    }
}
