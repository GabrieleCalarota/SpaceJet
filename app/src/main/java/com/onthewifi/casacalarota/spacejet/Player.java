package com.onthewifi.casacalarota.spacejet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;

/**
 * Created by Gabriele on 01/06/2017.
 */

public class Player {
    private Bitmap bitmap;

    private int x;
    private int y;

    private int life;

    private int speed;
    private int bonus;

    private int score;

    private boolean boosting;
    private final int GRAVITY = -10;
    private final int MAX_LIFE = 10;

    private int maxY;
    private int minY;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private MediaPlayer bloop;
    private MediaPlayer gulp;

    private Rect detectCollision;

    public Player(Context context,int screenX,int screenY){
        x = 75;
        y = 50;
        speed = 1;
        score = 0;
        life = 3;
        bonus = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.player2);
        maxY = screenY - bitmap.getHeight();
        minY = 0;

        boosting = false;

        bloop = MediaPlayer.create(context,R.raw.shutdown);
        gulp = MediaPlayer.create(context,R.raw.gulp);
        detectCollision = new Rect();

        detectCollision = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());
    }

    public void updateSpeed(){
        if (speed < MAX_SPEED)
            speed++;
    }

    public void decreaseSpeed(){
        speed -=3;
        if (speed < MIN_SPEED)
            speed = MIN_SPEED;
    }
    public void setBoosting(){
        boosting = true;
    }
    public void stopBoosting(){
        boosting = false;
    }
    public void update(){
        if (boosting){
            speed += 9;
        } else {
            speed -= 6;
        }
        if (speed > MAX_SPEED)
            speed = MAX_SPEED;
        if (speed < MIN_SPEED)
            speed = MIN_SPEED;

        y -= speed + GRAVITY;
        if (y < minY)
            y = minY;
        if (y > maxY)
            y = maxY;
       // x++;

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x +bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getNoseX(){
        return x + bitmap.getWidth();
    }

    public int getHeight(){
        return bitmap.getHeight();
    }

    public void decreaseLife(){
        life--;
        if (bloop.isPlaying()) {
            bloop.pause();
            bloop.seekTo(0);
        }
        bloop.start();
    }

    public void increaseLife(){
        if (life < MAX_LIFE) {
            life++;
            if (gulp.isPlaying()) {
                gulp.pause();
                gulp.seekTo(0);
            }
            gulp.start();
        }
    }

    public boolean isDead(){
        return (life < 1);
    }

    public void updateScore(){
        score +=bonus;
        bonus++;
    }
    public void stopBonus(){
        bonus = 1;
    }
    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }

    public int getNoseY(){
        return y + getHeight()/2;
    }

}
