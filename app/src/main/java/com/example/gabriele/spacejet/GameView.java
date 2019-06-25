package com.example.gabriele.spacejet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Thread.sleep;

/**
 * Created by Gabriele on 01/06/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;

    private Thread gameThread = null;

    private Player player;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private ArrayList<Star> stars = new ArrayList<Star>();

    private ArrayList<Enemy> enemies;
    private ArrayList<lifeHearth> life;

    private int enemyCount = 1;

    private Boom boom;

    private Activity game;

    private int x_score;
    private int y_score;
    private int digits;
    private int screenX;
    private int screenY;

    private MediaPlayer mp;

    private int rocks_count = 2;
    private int life_count = 1;

    private ArrayList<Rocks> rocks;
    private ArrayList<Hearth> hearths;

    private final int maxEnemies = 4;
    private final int MAX_ROCKS = 12;

    public GameView(Context context, int screenX, int screenY){
        super(context);

        player = new Player(context,screenX,screenY);

        surfaceHolder = getHolder();
        paint = new Paint();
        this.game = (Activity) context;

        this.screenX = screenX;
        this.screenY = screenY;

        digits = 0;

        mp = MediaPlayer.create(context,R.raw.swish);


        int starNums = 100;
        for (int i=0;i<starNums;i++){
            Star s = new Star(screenX,screenY);
            stars.add(s);
        }
        enemies = new ArrayList<Enemy>();
        for (int i = 0; i<enemyCount; i++){
            enemies.add(new Enemy(context,screenX,screenY));
        }

        boom = new Boom(context);
        life = new ArrayList<lifeHearth>();
        for (int i = 0; i<player.getLife();i++){
            life.add(new lifeHearth(context,screenX,screenY,i,player));
        }

        x_score = screenX - (life.get(0).getBitmap().getWidth()*2);
        y_score = life.get(0).getBitmap().getHeight()+15;

        rocks = new ArrayList<Rocks>();
        for (int i = 0; i<rocks_count; i++){
            rocks.add(new Rocks(context,screenX,screenY));
        }
       hearths = new ArrayList<Hearth>();
        for (int i = 0; i<life_count; i++){
            hearths.add(new Hearth(context,screenX,screenY));
        }
    }
    @Override
    public void run(){
        while(playing){
            update();
            draw();
            control();
        }
    }
    private void update(){
        player.update();
        boom.setdefaultX();
        for (Star s: stars){
            s.update(player.getSpeed());
        }
        int enemy_now = enemyCount;
        for (int i =0;i<enemy_now;i++){
            enemies.get(i).update(player.getSpeed(),player);



            //if (Rect.intersects(player.getDetectCollision(),enemies.get(i).getDetectCollision())){
              if (Collision.isCollisionDetected(player.getBitmap(),player.getX(),player.getY(),enemies.get(i).getBitmap(),enemies.get(i).getX(),enemies.get(i).getY())){
                //mp.stop();
                //mp.reset();
                if (mp.isPlaying()) {
                    mp.pause();
                    mp.seekTo(0);
                }
                mp.start();
                boom.setX(enemies.get(i).getX());
                boom.setY(enemies.get(i).getY());
                player.updateScore();
                player.updateSpeed();
                if ((player.getScore() % 5 == 0) && (enemyCount<maxEnemies)){
                    enemyCount++;
                    enemies.add(new Enemy(this.getContext(),screenX,screenY));}

                  enemies.get(i).regenerateEnemy();
                if ((player.getScore()/pow(10,(digits+1))) >= 1){
                    digits++;
                    System.out.println(digits);
                    x_score -= (screenX*3/100)*(digits);
                }
            }
        }

        for (int i=0;i<rocks_count;i++){
            if (rocks.get(i).isActive()){
                rocks.get(i).update(player);
                if (player.isDead()){
                    loose();
                }
            }
            else{
                Random r = new Random();
                int j = r.nextInt(1000);
                if (j<5){
                    rocks.get(i).setActive(true);
                }
                j = r.nextInt(1000);
                if (j<3 && rocks_count != MAX_ROCKS){
                    rocks.add(new Rocks(getContext(),screenX,screenY));
                    rocks_count++;
                }
            }
        }

        for (int i=0;i<life_count;i++){
            if (hearths.get(i).isActive()){
                int old_life = player.getLife();
                hearths.get(i).update(player);
                if (old_life != player.getLife())
                    life.add(new lifeHearth(getContext(),screenX,screenY,old_life,player));
            }
            else{
                Random r = new Random();
                int j = r.nextInt(10000);
                if (j<(10/(player.getLife()+1))) {
                    hearths.get(i).setActive(true);
                }
            }
        }

    }

    private void draw(){
        if (surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            paint.setColor(Color.WHITE);
            for (Star s: stars){
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(),s.getY(),paint);
            }

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint
            );
            for (int i = 0; i<enemyCount;i++){
                canvas.drawBitmap(
                        enemies.get(i).getBitmap(),
                        enemies.get(i).getX(),
                        enemies.get(i).getY(),
                        paint
                );
            }

            for (int i = 0; i<rocks_count;i++){
                if (rocks.get(i).isActive()){
                    canvas.drawBitmap(
                            rocks.get(i).getBitmap(),
                            rocks.get(i).getX(),
                            rocks.get(i).getY(),
                            paint
                    );
                }
            }

            for (int i = 0; i<life_count;i++){
                if (hearths.get(i).isActive()){
                    canvas.drawBitmap(
                            hearths.get(i).getBitmap(),
                            hearths.get(i).getX(),
                            hearths.get(i).getY(),
                            paint
                    );
                }
            }



            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );

            for (int i=0;i<player.getLife();i++){
                canvas.drawBitmap(
                        life.get(i).getBitmap(),
                        life.get(i).getX(i,player),
                        life.get(i).getY(),
                        paint
                );
            }

            paint.setTextSize(20);
            canvas.drawText("Score: "+player.getScore(),
                            x_score,
                            y_score,
                            paint
                    );

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction() & motionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }
    private void control(){
        try{
            sleep(17);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void loose(){
        playing = false;
        Intent intent = new Intent();
        intent.putExtra("score",player.getScore());
        game.setResult(Activity.RESULT_OK,intent);
        game.finish();
    }


    public void pause(){
        playing = false;
        try{
            gameThread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}