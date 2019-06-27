package com.onthewifi.casacalarota.spacejet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.onthewifi.casacalarota.spacejet.R;

import java.util.Random;

/**
 * Created by Gabriele on 03/06/2017.
 */

public class Rocks {
    private int x;
    private int y;
    private int screenY;
    private int screenX;
    private int speed;
    private Bitmap bitmap;
    private boolean active;
    private int life;
    static final int MAX_LIFE = 14;
    private Rect detectCollision;

    private final int MAX_SPEED = 10;
    public Rocks(Context context,int screenX,int screenY){
        x = screenX;
        Random r = new Random();
        life = r.nextInt(MAX_LIFE)+1;
        speed = MAX_SPEED;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock);

        this.screenX = screenX;
        this.screenY = screenY;
        y = generateY();
        active = false;
        detectCollision = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());
    }

    private int generateY() {
        Random r = new Random();
        return r.nextInt(screenY-bitmap.getHeight());
    }

    public int getLife() {
        return life;
    }
    public void decreaseLife(){
        if (!isDead())
            life--;
    }

    public boolean isDead(){
        return life<=0;
    }

    public void funeral(){
        this.active = false;
        x = screenX;
        y = generateY();
    }

    public void update(Player p){
        if (active){
            this.x -= speed;
            if (this.x < (-bitmap.getWidth()/4)){
                //p.updateScore();
                funeral();
            }
            else {
                if (Collision.isCollisionDetected(p.getBitmap(),p.getX(),p.getY(),bitmap,x,y)) {
                    funeral();
                    p.decreaseLife();
                }
            }
            detectCollision.left = x;
            detectCollision.top = y;
            detectCollision.right = x + bitmap.getWidth();
            detectCollision.bottom = y + bitmap.getHeight();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Rect getDetectCollision(){
        return detectCollision;
    }
}
