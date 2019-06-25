package com.example.gabriele.spacejet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Gabriele on 01/06/2017.
 */



public class Enemy {
    private final int enemySpeedRange = 15;
    private int enemyMinSpeed = 5;
    private final int enemyMaxMinSpeed = 20;
    private Bitmap bitmap;

    private int x;
    private int y;

    private int speed = 1;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;

    private Rect detectCollision;

    public Enemy(Context context,int screenX,int screenY){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy2);

        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(enemySpeedRange) + enemyMinSpeed;
        x = screenX;
        y = generator.nextInt(maxY - bitmap.getHeight());

        detectCollision = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());
    }

    public void regenerateEnemy(){
        Random generator = new Random();
        increseMinSpeed();
        speed = generator.nextInt(enemySpeedRange) + enemyMinSpeed;
        x = maxX;
        y = generator.nextInt(maxY - bitmap.getHeight());
    }

    public void update(int playerSpeed,Player player){
        //x -=playerSpeed;

        x -=speed;
        /*Quando il 25% della lunghezza della navicella è oltre la sinistra dello schermo, la faccio sparire*/
        if (x < (minX - (bitmap.getWidth()/4))){
            regenerateEnemy();
            //player.decreaseLife();
            player.stopBonus();
            player.decreaseSpeed();
            System.out.println("Player decrease life\n");
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public void increseMinSpeed(){
        if (enemyMinSpeed < enemyMaxMinSpeed)
            enemyMinSpeed++;
    }
    public void setX(int x){
        this.x = x;
    }

    public Rect getDetectCollision(){
        return detectCollision;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
