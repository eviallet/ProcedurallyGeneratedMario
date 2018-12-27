package com.gueg.mario;

import android.content.res.Resources;

public class Ennemy extends AnimatedObject {

    public static int DEFAULT_SPEED = 6;
    public static int BILLBALL_SPEED = 14;
    private int _maxIndex;

    public Ennemy(Resources res, int[] resId, int speed, boolean gravity) {
        super(res,resId,gravity);
        _velocityX = -speed; // going left by default
        _maxIndex = resId.length/2;
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

}
