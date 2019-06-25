package com.example.gabriele.spacejet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Gabriele on 01/06/2017.
 */

public class lifeHearth {
    private Bitmap bitmap;

    private int x;
    private int y;
    private int screenX;

    public lifeHearth(Context context, int screenX, int screenY, int num,Player player){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hearth2);

        this.screenX = screenX;
        x = screenX-(bitmap.getWidth()*(player.getLife()-num));
        y = 0;

    }



    public int getX(int num, Player player) {
        return screenX-(bitmap.getWidth()*(player.getLife()-num));
    }

    public int getY() {
        return y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
