package com.onthewifi.casacalarota.spacejet;

import java.util.Random;

/**
 * Created by Gabriele on 01/06/2017.
 */

public class Star {
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    private final int starSpeedRange = 5;
    private final int minStarSpeed = 1;

    public Star(int screenX,int screenY){
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(starSpeedRange) + minStarSpeed;

        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }

    public void update(int playerSpeed){
        //x -=playerSpeed;
        x -=speed;
        if (x < 0){
            x =maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(starSpeedRange) + minStarSpeed;
        }
    }

    public float getStarWidth(){
        float minX = 1.0f;
        float maxX = 4.0f;
        Random rand = new Random();
        float finalX = rand.nextFloat() * (maxX-minX) + minX;
        return finalX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
