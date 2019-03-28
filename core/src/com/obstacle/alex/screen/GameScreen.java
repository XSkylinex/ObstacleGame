package com.obstacle.alex.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacle.alex.config.GameConfig;
import com.obstacle.alex.entity.Player;
import com.obstacle.alex.util.GdxUtils;
import com.obstacle.alex.util.ViewportUtils;
import com.obstacle.alex.util.debug.DebugCameraController;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private static final Logger log = new Logger(GameScreen.class.getName(),Logger.DEBUG);

    private Player player;

    private DebugCameraController debugCameraController;

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
    }

    @Override
    public void render (float delta) {

        this.debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);
        update(delta);

        GdxUtils.clearScreen();

        renderDebug();
    }

    @Override
    public void dispose () {
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
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
    }


    private void renderDebug(){
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        drewDebug();
        renderer.end();
        ViewportUtils.drawGrid(viewport,renderer);
    }


    private void update(float delta){
        updatePlyer();
    }

    private void updatePlyer(){
        log.debug("PlayerX= " +player.getX() +" PlayerY= " + player.getY());
        player.update();
    }
}
