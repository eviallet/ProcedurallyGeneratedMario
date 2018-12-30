package com.gueg.mario.objects;

import android.content.res.Resources;
import android.graphics.Rect;

public class Mario extends AnimatedObject {

    public static final int MAX_SPEED_X_WALKING = 12;
    public static final int MAX_SPEED_X_RUNNING = 20;

    private boolean _direction = RIGHT;
    public boolean moving = false;
    public boolean running =false;
    public boolean jumping =false;
    public float downPos = 0;

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
            super.nextFrame(Frames.WALK);
        else if(Math.abs(_velocityX) > MAX_SPEED_X_WALKING) // running
            super.nextFrame(Frames.RUN);
        else
            setFrame(0,_direction); // idle
    }


    public void move(Rect screenRect) {
        _direction=(downPos>=screenRect.width()/2);

        if (_direction)
            moveRight(running);
        else
            moveLeft(running);
    }

    private void moveLeft(boolean running) {
        _direction = LEFT;
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

    private void moveRight(boolean running) {
        _direction = RIGHT;
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
            if (getDirection() == RIGHT)
                _velocityX--;
            else
                _velocityX++;
        }
    }

    public void slowDownQuickly() {
        if(_velocityX!=0) {
            if (getDirection() == RIGHT) {
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
