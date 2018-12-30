package com.gueg.mario.objects;

import android.content.res.Resources;

public class Enemy extends AnimatedObject {

    public static int DEFAULT_SPEED = 6;
    public static int BILLBALL_SPEED = 14;
    private int _maxIndex;

    public Enemy(Resources res, int[] resId, int speed, boolean gravity) {
        super(res, resId, gravity);
        _velocityX = -speed; // going left by default
        _maxIndex = resId.length/2;
    }

    public Enemy(Resources res, int[] resId, int speed, boolean gravity, int sizeX, int sizeY) {
        this(res, resId, speed, gravity);
        setSize(sizeX, sizeY);
    }

    @Override
    public void nextFrame() {
        nextFrame(_maxIndex);
    }

    public boolean getDirection() {
        return _velocityX >= 0;
    }

    public void changeDirection() {
        _velocityX = -_velocityX;
    }

    @Override
    public Enemy clone() {
        return new Enemy(_res, _resId, _velocityX, _gravity);
    }


}
