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

    private DebugCameraConfig debugCameraConfig;

    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;

    public DebugCameraController() {
        debugCameraConfig = new DebugCameraConfig();
        log.info("Config fromDebugCameraConfig= "+debugCameraConfig );
    }


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

        float moveSpeed = debugCameraConfig.getMoveSpeed()*delta;
        float zoomSpeed = debugCameraConfig.getZoomSpeed();

        if(debugCameraConfig.leftKey()){
            moveLedt(moveSpeed);

        }else if(debugCameraConfig.rightKey()){
            moveRight(moveSpeed);

        }else if(debugCameraConfig.upKey()){
            moveUp(moveSpeed);

        }else if(debugCameraConfig.downKey()){
            moveDown(moveSpeed);

        }


        if(debugCameraConfig.zoomInKey()){
            setZoomIn(zoomSpeed);
        }else if(debugCameraConfig.zoomOutKey()){
            setZoomOut(zoomSpeed);
        }


        if(debugCameraConfig.resetKey()){
            reset();
        }

        if(debugCameraConfig.logKey()){
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
        this.zoom = MathUtils.clamp(v,debugCameraConfig.getMaxZoomIn(),debugCameraConfig.getMaxZoomOut());

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
