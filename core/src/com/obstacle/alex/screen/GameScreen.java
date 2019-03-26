package com.obstacle.alex.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacle.alex.config.GameConfig;
import com.obstacle.alex.util.GdxUtils;
import com.obstacle.alex.util.ViewportUtils;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    @Override
    public void show () { // show it like create initialize game and load resources
        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT,camera);
        this.renderer = new ShapeRenderer();
    }

    @Override
    public void render (float delta) {
        GdxUtils.clearScreen();
        drewDebug();
    }

    @Override
    public void dispose () {
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
        ViewportUtils.drawGrid(viewport,renderer);
    }
}
