package com.onthewifi.casacalarota.spacejet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Gabriele on 01/06/2017.
 */

public class Boom {
    private Bitmap bitmap;

    private int x;
    private int y;

    public Boom(Context context){
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.boom2);

        x = -5000;
        y = -5000;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setdefaultX(){
        this.x = -5000;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
