package com.obstacle.alex.util.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;

public class DebugCameraConfig {

    private static final Logger log = new Logger(DebugCameraConfig.class.getName(),Logger.DEBUG);

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



    private static final String MAX_ZOOM_IN_JSON = "maxZoomIn";
    private static final String MAX_ZOOM_OUT_JSON = "maxZoomOut";
    private static final String MOVE_SPEED_JSON = "moveSpeed";
    private static final String ZOOM_SPEED_JSON = "zoomSpeed";

    private static final String LEFT_KEY_JSON= "leftKey";
    private static final String RIGHT_KEY_JSON = "rightKey";
    private static final String UP_KEY_JSON = "upKey";
    private static final String DOWN_KEY_JSON = "downKey";

    private static final String ZOOM_IN_KEY_JSON = "zoomInKey";
    private static final String ZOOM_OUT_KEY_JSON = "zoomOutKey";
    private static final String RESET_KEY_JSON = "resetKey";
    private static final String LOG_KEY_JSON = "logKey";


    private static final String FILE_CONFIG_PATH = "debug/debugCameraConfig.json";

    private float maxZoomIn;
    private float maxZoomOut;
    private float moveSpeed;
    private float zoomSpeed;

    private int upKey;
    private int leftKey;
    private int downKey;
    private int rightKey;

    private int zoomInKey;
    private int zoomOutKey;

    private int resetKey;
    private int logKey;

    private FileHandle fileHandle;

    public DebugCameraConfig() {
        startConfig();
    }


    private void startConfig(){
        fileHandle = Gdx.files.internal(FILE_CONFIG_PATH);


        if(fileHandle.exists()){
            loadFromJsonFile();
        }else{
            log.info("file not exist" + FILE_CONFIG_PATH);
            setupConfig();
        }


    }

    private void loadFromJsonFile(){
        try{
            JsonReader jsonReader = new JsonReader();
            JsonValue value = jsonReader.parse(fileHandle);
            maxZoomIn = value.getFloat(MAX_ZOOM_IN_JSON,ZOOM_OUT_MAX_FAST);
            maxZoomIn = value.getFloat(MAX_ZOOM_OUT_JSON,ZOOM_OUT_MAX_FAST);
            maxZoomIn = value.getFloat(MOVE_SPEED_JSON,MOVE_SPEED);
            maxZoomIn = value.getFloat(ZOOM_SPEED_JSON,ZOOM_OUT_FAST);

            leftKey = getInputKeyFromJson(value,LEFT_KEY_JSON,LEFT_KEY);

            rightKey = getInputKeyFromJson(value,RESET_KEY_JSON,RIGHT_KEY);

            upKey = getInputKeyFromJson(value,UP_KEY_JSON,UP_KEY);

            downKey = getInputKeyFromJson(value,DOWN_KEY_JSON,DOWN_KEY);


            zoomOutKey =  getInputKeyFromJson(value,ZOOM_IN_KEY_JSON,ZOOM_IN);
            zoomInKey =  getInputKeyFromJson(value,ZOOM_OUT_KEY_JSON,ZOOM_OUT);
            resetKey = getInputKeyFromJson(value,RESET_KEY_JSON,ZOOM_RESET);
            logKey =  getInputKeyFromJson(value,LOG_KEY_JSON,LOG_KEY);


        }
        catch (Exception e){
            log.error("Problem read json",e);
            setupConfig();
        }
    }

    private void setupConfig(){
        this.maxZoomIn = ZOOM_IN_MAX_FAST;
        this.maxZoomOut = ZOOM_OUT_MAX_FAST;
        this.moveSpeed = MOVE_SPEED;
        this.zoomSpeed = ZOOM_OUT_FAST;

        this.leftKey = LEFT_KEY;
        this.rightKey = RIGHT_KEY;
        this.upKey = UP_KEY;
        this.downKey = DOWN_KEY;

        this.zoomInKey = ZOOM_IN;
        this.zoomOutKey = ZOOM_OUT;

        this.resetKey = ZOOM_RESET;
        this.logKey = LOG_KEY;


    }


    private static int getInputKeyFromJson(JsonValue jsonValue , String name , int input){
        String key = jsonValue.getString(name,Input.Keys.toString(input));
        return Input.Keys.valueOf(key);
    }


    public float getMaxZoomIn() {
        return maxZoomIn;
    }

    public float getMaxZoomOut() {
        return maxZoomOut;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getZoomSpeed() {
        return zoomSpeed;
    }


    public boolean leftKey(){
        return Gdx.input.isKeyPressed(leftKey);
    }
    public boolean rightKey(){
        return Gdx.input.isKeyPressed(rightKey);
    }
    public boolean upKey(){
        return Gdx.input.isKeyPressed(upKey);
    }
    public boolean downKey(){
        return Gdx.input.isKeyPressed(downKey);
    }
    public boolean zoomInKey(){
        return Gdx.input.isKeyPressed(zoomInKey);
    }
    public boolean zoomOutKey(){
        return Gdx.input.isKeyPressed(zoomOutKey);
    }
    public boolean resetKey(){
        return Gdx.input.isKeyPressed(resetKey);
    }
    public boolean logKey(){
        return Gdx.input.isKeyPressed(logKey);
    }

    @Override
    public String toString() {

        String separator = System.getProperty("line separator");
        return "DebugCameraConfig{" + separator+
                "maxZoomIn=" + maxZoomIn +separator+
                ", maxZoomOut=" + maxZoomOut +separator+
                ", moveSpeed=" + moveSpeed +separator+
                ", zoomSpeed=" + zoomSpeed +separator+
                ", upKey=" + Input.Keys.toString(upKey) +separator+
                ", leftKey=" + Input.Keys.toString(leftKey) +separator+
                ", downKey=" + Input.Keys.toString(downKey) +separator+
                ", rightKey=" + Input.Keys.toString(rightKey) +separator+
                ", zoomInKey=" + Input.Keys.toString(zoomInKey) +separator+
                ", zoomOutKey=" + Input.Keys.toString(zoomOutKey) +separator+
                ", resetKey=" + Input.Keys.toString(resetKey) +separator+
                ", logKey=" + Input.Keys.toString(logKey) +separator+
                '}';
    }
}
