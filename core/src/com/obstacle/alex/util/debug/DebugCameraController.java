package com.obstacle.alex.util.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.obstacle.alex.screen.GameScreen;

public class DebugCameraController {

    private static final Logger log = new Logger(GameScreen.class.getName(),Logger.DEBUG);



    private static final int LEFT_KEY = Input.Keys.A;
    private static final int RIGHT_KEY = Input.Keys.D;

    private static final int UP_KEY = Input.Keys.W;
    private static final int DOWN_KEY = Input.Keys.S;

    private static final float MOVE_SPEED = 20.0f;

    private static final int ZOOM_IN = Input.Keys.COMMA;
    private static final int ZOOM_OUT = Input.Keys.PERIOD;

    private static final int ZOOM_RESET = Input.Keys.BACKSPACE;
    private static final int LOG_KEY = Input.Keys.ENTER;

    private static final float ZOOM_OUT_FAST = 2.0f;
    private static final float ZOOM_IN_MAX_FAST = 0.20f;
    private static final float ZOOM_OUT_MAX_FAST = 30f;


    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;


    public void setStartPosition(float x,float y){
        this.startPosition.set(x,y);
        this.position.set(x,y);
    }

    public void applyTo(OrthographicCamera camera){
        camera.position.set(position,0);
        camera.zoom =zoom;
        camera.update();
    }


    public void handleDebugInput(float delta){
        if(Gdx.app.getType() != Application.ApplicationType.Desktop){
            return;
        }

        float moveSpeed = MOVE_SPEED*delta;
        float zoomSpeed = ZOOM_OUT_FAST;

        if(Gdx.input.isKeyPressed(LEFT_KEY)){
            moveLedt(moveSpeed);

        }else if(Gdx.input.isKeyPressed(RIGHT_KEY)){
            moveRight(moveSpeed);

        }else if(Gdx.input.isKeyPressed(UP_KEY)){
            moveUp(moveSpeed);

        }else if(Gdx.input.isKeyPressed(DOWN_KEY)){
            moveDown(moveSpeed);

        }


        if(Gdx.input.isKeyPressed(ZOOM_IN)){
            setZoomIn(zoomSpeed);
        }else if(Gdx.input.isKeyPressed(ZOOM_OUT)){
            setZoomOut(zoomSpeed);
        }


        if(Gdx.input.isKeyPressed(ZOOM_RESET)){
            reset();
        }

        if(Gdx.input.isKeyPressed(LOG_KEY)){
            logDebug();
        }

    }

    private void setPosition(float x, float y){
        this.position.set(x,y);
    }

    private void moveCamera(float xSpeed , float ySpeed){
        this.setPosition(position.x+xSpeed,position.y+ySpeed);
    }

    private void moveLedt(float speed){
        moveCamera(-speed,0);
    }
    private void moveRight(float speed){
        moveCamera(speed,0);
    }

    private void moveUp(float speed){
        moveCamera(0,speed);
    }

    private void moveDown(float speed){
        moveCamera(0,-speed);
    }

    private void setZoom(float v){
        this.zoom = MathUtils.clamp(v,ZOOM_IN_MAX_FAST,ZOOM_OUT_MAX_FAST);

    }


    private void setZoomIn(float zoomSpeed){
        setZoom(zoom+zoomSpeed);
    }

    private void setZoomOut(float zoomOutSpeed){
        setZoom(zoom-zoomOutSpeed);
    }

    private void reset(){
        position.set(startPosition);
        setZoom(1.0f);
    }

    private void logDebug(){
        log.debug("where=" + position + "zoom=" + zoom);
    }



}
