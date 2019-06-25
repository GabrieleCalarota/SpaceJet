package com.example.gabriele.spacejet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.gabriele.spacejet.R;

import java.util.Random;

/**
 * Created by Gabriele on 03/06/2017.
 */

public class Hearth {

    private int x;
    private int y;
    private int screenY;
    private int screenX;
    private int speed;
    private Bitmap bitmap;
    private boolean active;
    private Rect detectCollision;

    private final int MAX_SPEED = 30;
    public Hearth(Context context,int screenX,int screenY){
        x = screenX;

        speed = MAX_SPEED;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hearth1);
        active = false;

        this.screenX = screenX;
        this.screenY = screenY;
        y = generateY();
        detectCollision = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());
    }

    public void update(Player p){
        if (active){
            this.x -= speed;
            if (this.x < (-bitmap.getWidth()/4)){
                this.active = false;
                x = screenX;
                y = generateY();
            }
            else {
                if (Collision.isCollisionDetected(p.getBitmap(),p.getX(),p.getY(),bitmap,x,y)) {
                    this.active = false;
                    x = screenX;
                    y = generateY();
                    p.increaseLife();
                }
            }
            detectCollision.left = x;
            detectCollision.top = y;
            detectCollision.right = x + bitmap.getWidth();
            detectCollision.bottom = y + bitmap.getHeight();

        }
    }

    private int generateY() {
        Random r = new Random();
        return r.nextInt(screenY-bitmap.getHeight());
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
