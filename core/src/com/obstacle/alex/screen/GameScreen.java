package com.obstacle.alex.screen;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    private GameController controller;
    private GameRender render;
    @Override
    public void show() {
        this.controller = new GameController();
        this.render = new GameRender(controller);
    }

    @Override
    public void render(float delta) {
        this.controller.update(delta);
        this.render.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        this.render.resize(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();

    }

    @Override
    public void dispose() {
        this.render.dispose();
    }
}
