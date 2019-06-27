package com.onthewifi.casacalarota.spacejet;

public class Bullet {
    public static float radius = 15.0f;
    private int x;
    private int y;
    private int speed;
    private int maxX;

    private final int bulletSpeed = 10;
    static final float bulletWidth = 30.0f;
    static final int numberOfBullet = 100;

    Bullet(int posX, int height, int screenX){
        maxX = screenX;
        //maxY = screenY;
        //minX = 0;
        //minY = 0;

        //Random generator = new Random();
        speed = bulletSpeed;

        x = posX;
        y = height;
    }

    boolean update(){
        //x -=playerSpeed;
        x +=speed;
        /*From left to right*/
        return x <= maxX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
