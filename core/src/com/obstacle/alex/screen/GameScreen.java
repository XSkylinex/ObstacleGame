package com.obstacle.alex.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacle.alex.assets.AssetPaths;
import com.obstacle.alex.config.GameConfig;
import com.obstacle.alex.entity.ObstacleEnemy;
import com.obstacle.alex.entity.Player;
import com.obstacle.alex.util.GdxUtils;
import com.obstacle.alex.util.ViewportUtils;
import com.obstacle.alex.util.debug.DebugCameraController;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private static final Logger log = new Logger(GameScreen.class.getName(),Logger.DEBUG);

    private Player player;
    private ArrayList<ObstacleEnemy> obstacleEnemies = new ArrayList<ObstacleEnemy>();
    private float obstacleEnemyTime;

    private DebugCameraController debugCameraController;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private SpriteBatch batch;
    private final GlyphLayout layout = new GlyphLayout();
    private BitmapFont font;

    private int lives = GameConfig.LIVE_START;

    private float scoreTimer;
    private int score;

    private int playerDisplayScore;

    @Override
    public void show () { // show it like create initialize game and load resources
        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT,camera);
        this.renderer = new ShapeRenderer();
        this.player = new Player();

        float startPlayerX = GameConfig.WORLD_WIDTH/2f;
        float startPlayerY = 1;

        player.setPosition(startPlayerX,startPlayerY);

        this.debugCameraController = new DebugCameraController();
        this.debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X,GameConfig.WORLD_CENTER_Y);


        this.hudCamera = new OrthographicCamera();
        this.hudViewport = new FillViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT,hudCamera);
        this.batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal(AssetPaths.UI_FONT));
    }

    @Override
    public void render (float delta) {

        this.debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);
        update(delta);

        GdxUtils.clearScreen();
        renderUI();

        renderDebug();
    }

    @Override
    public void dispose () {
        renderer.dispose();
        batch.dispose();
        font.dispose();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        this.hudViewport.update(width,height,true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() { // if i hide my screen should dispose
        dispose();
    }

    private void drewDebug(){
        player.drawDebug(renderer);
        for (ObstacleEnemy obstacleEnemy: obstacleEnemies) {
            obstacleEnemy.drawDebug(renderer);
        }
    }


    private void renderDebug(){
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        drewDebug();
        renderer.end();
        ViewportUtils.drawGrid(viewport,renderer);
    }


    private void update(float delta){
        if(isGameOver()){

        }
        updatePayer();
        updateEnemyObstacles(delta);
        updateScore(delta);
        updatePlayerDisplayScore(delta);

        if(isPlayerColliadingTureOrFalse()){
            this.lives--;
        }

        updateScore(delta);
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

    private void renderUI(){
        this.batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesFont = "LIVES: " + lives;
        layout.setText(font,livesFont);

        font.draw(batch,livesFont,20,GameConfig.HUD_HEIGHT - layout.height);

        String scoreFont = "Points: "+playerDisplayScore;
        layout.setText(font,scoreFont);
        font.draw(batch,scoreFont,
                GameConfig.HUD_WIDTH - layout.width - 20,
                GameConfig.HUD_HEIGHT - layout.height);


        batch.end();
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

}
