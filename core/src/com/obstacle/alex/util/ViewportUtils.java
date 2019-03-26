package com.obstacle.alex.util;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportUtils {
    private static final Logger log = new Logger(ViewportUtils.class.getName(),Logger.DEBUG);

    private static final int ORIGINAL_CELL_SIZE = 1; // OR DEFAULT


    public static void drawGrid(Viewport viewport, ShapeRenderer renderer){
        drawGrid(viewport,renderer,ORIGINAL_CELL_SIZE);

    }
    public static void drawGrid(Viewport viewport ,ShapeRenderer renderer,int cellSize){

        //check parameters
        if(viewport == null){
            throw new IllegalArgumentException("Viewport parameters error.");
        }

        if(renderer == null){
            throw new IllegalArgumentException("Renderer parameters error.");
        }


        if(cellSize < ORIGINAL_CELL_SIZE){
            cellSize = ORIGINAL_CELL_SIZE;
        }

        Color oldColor = new Color(renderer.getColor());
        int worldWidth= (int)viewport.getWorldWidth();
        int worldHeight =(int)viewport.getWorldHeight();
        int doubleWorldHeight = worldHeight*2;
        int doubleWorldWidth = worldWidth*2;

        renderer.setProjectionMatrix(viewport.getCamera().combined);



        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);


        for(int x = -doubleWorldWidth ; x< doubleWorldWidth; x+=cellSize){
            renderer.line(x,-doubleWorldHeight,x,doubleWorldHeight);
        }


        for(int y = -doubleWorldHeight ; y <doubleWorldHeight ; y+=cellSize){
            renderer.line(-doubleWorldWidth,y,doubleWorldWidth,y);
        }

        renderer.setColor(Color.RED);
        renderer.line(0,-doubleWorldHeight,0,doubleWorldHeight);
        renderer.line(-doubleWorldWidth,0,doubleWorldWidth,0);

        renderer.setColor(Color.GREEN);
        renderer.line(0,worldHeight,worldWidth,worldHeight);
        renderer.line(worldWidth,0,worldWidth,worldHeight);

        renderer.end();
        renderer.setColor(oldColor);


    }

    public static void debugPixelPerUnit(Viewport viewport){
        if(viewport == null){
            throw new IllegalArgumentException("Viewport parameters error.");
        }

        float screenWidth = viewport.getScreenWidth();
        float screenHight = viewport.getScreenHeight();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();


        //PPU = pixel per world unit
        float xPPU = screenWidth/worldWidth;
        float yPPU = screenHight/worldHeight;


        log.debug("x PPU= "+ xPPU + "yPPU= " + yPPU);
    }
}
