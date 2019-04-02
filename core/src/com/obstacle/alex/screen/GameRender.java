package com.obstacle.alex.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
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

public class GameRender implements Disposable {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private static final Logger log = new Logger(GameRender.class.getName(),Logger.DEBUG);
    private DebugCameraController debugCameraController;
    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private SpriteBatch batch;
    private final GlyphLayout layout = new GlyphLayout();
    private BitmapFont font;

    private final GameController controller;

    public GameRender(GameController controller) {
        this.controller =controller;
        initAll();
    }

    private void initAll(){
        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT,camera);
        this.renderer = new ShapeRenderer();

        this.debugCameraController = new DebugCameraController();
        this.debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X,GameConfig.WORLD_CENTER_Y);


        this.hudCamera = new OrthographicCamera();
        this.hudViewport = new FillViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT,hudCamera);
        this.batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal(AssetPaths.UI_FONT));
    }

    public void render (float delta) {

        this.debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);
        GdxUtils.clearScreen();
        renderUI();
        renderDebug();
    }


    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }

    public void resize(int width, int height) {
        viewport.update(width,height,true);
        this.hudViewport.update(width,height,true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    private void drewDebug(){
        Player player = this.controller.getPlayer();
        ArrayList<ObstacleEnemy> obstacleEnemies = this.controller.getObstacleEnemies();
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




    private void renderUI(){
        this.batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesFont = "LIVES: " + this.controller.getLives();
        layout.setText(font,livesFont);

        font.draw(batch,livesFont,20,GameConfig.HUD_HEIGHT - layout.height);

        String scoreFont = "Points: "+this.controller.getPlayerDisplayScore();
        layout.setText(font,scoreFont);
        font.draw(batch,scoreFont,
                GameConfig.HUD_WIDTH - layout.width - 20,
                GameConfig.HUD_HEIGHT - layout.height);


        batch.end();
    }

    public void hide() { // if i hide my screen should dispose
        dispose();
    }


}
