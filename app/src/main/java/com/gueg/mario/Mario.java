package com.gueg.mario;

import android.content.res.Resources;

public class Mario extends AnimatedObject {

    public static final int MAX_SPEED_X_WALKING = 12;
    public static final int MAX_SPEED_X_RUNNING = 20;

    private boolean _direction = true;

    public Mario(Resources res, int[] resId) {
        super(res,resId,true);
    }

    @Override
    public void nextFrame() {
        if (_velocityY < 0) // jumping
            setFrame(2,_direction);
        else if(_velocityY > 0) // falling
            setFrame(3,_direction);
        else if(Math.abs(_velocityX)>0 && Math.abs(_velocityX) <= MAX_SPEED_X_WALKING) // walking
            super.nextFrame(FRAME_WALK);
        else if(Math.abs(_velocityX) > MAX_SPEED_X_WALKING) // running
            super.nextFrame(FRAME_RUN);
        else
            setFrame(0,_direction); // idle
    }

    public void moveLeft(boolean running) {
        _direction = false;
        if(_velocityX>0)
            _velocityX = 0;
        _velocityX--;
        if(running) {
            if (_velocityX < -MAX_SPEED_X_RUNNING)
                _velocityX = -MAX_SPEED_X_RUNNING;
        } else {
            if (_velocityX < -MAX_SPEED_X_WALKING)
            _velocityX = -MAX_SPEED_X_WALKING;
        }
    }

    public void moveRight(boolean running) {
        _direction = true;
        if(_velocityX<0)
            _velocityX = 0;
        _velocityX++;
        if(running) {
            if (_velocityX > MAX_SPEED_X_RUNNING)
                _velocityX = MAX_SPEED_X_RUNNING;
        } else {
            if (_velocityX > MAX_SPEED_X_WALKING)
                _velocityX = MAX_SPEED_X_WALKING;
        }
    }

    public void slowDown() {
        if(_velocityX!=0) {
            if (getDirection())
                _velocityX--;
            else
                _velocityX++;
        }
    }

    public void slowDownQuickly() {
        if(_velocityX!=0) {
            if (getDirection()) {
                _velocityX -= 3;
                if(_velocityX<0)
                    _velocityX = 0;
            }
            else {
                _velocityX += 3;
                if(_velocityX>0)
                    _velocityX = 0;
            }
        }
    }


}
