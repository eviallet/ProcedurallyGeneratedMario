package com.gueg.mario;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import static com.gueg.mario.MainActivity.mario;
import static com.gueg.mario.MainActivity.screenRect;

public class InputProcessor implements View.OnTouchListener {

    private int primaryPointerId;
    private int runningPointerId;
    private int jumpingPointerId;


    private static final float JUMP = -30.0f;

    // TOUCH EVENTS
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getPointerCount()==1) {
            primaryPointerId = event.getPointerId(event.getActionIndex());
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    mario.moving = false;
                    mario.running = false;
                    if (mario.jumping && mario.getVelocity() < JUMP / 2) {
                        mario.setVelocity(JUMP / 2);
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    mario.downPos = event.getX();
                    if (event.getY() > screenRect.centerY()) {
                        mario.moving = true;
                    } else {
                        if (!mario.isFalling() && !mario.jumping) {
                            mario.jumping = true;
                            mario.setVelocity(JUMP);
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getY() < screenRect.centerY()) {
                        if (!mario.isFalling() && !mario.jumping) {
                            mario.jumping = true;
                            mario.setVelocity(JUMP);
                        }
                    } else {
                        if (mario.jumping && mario.getVelocity() < JUMP / 2) {
                            mario.setVelocity(JUMP / 2);
                        }
                    }
                    break;
            }
        }
        else {
            MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
            event.getPointerCoords(event.getActionIndex(),coords);
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (coords.getAxisValue(1) > screenRect.centerY()) {
                        if(!mario.moving) {
                            mario.moving = true;
                            primaryPointerId = event.getPointerId(event.getActionIndex());
                        } else {
                            mario.running = true;
                            runningPointerId = event.getPointerId(event.getActionIndex());
                        }
                    } else {
                        if (!mario.isFalling() && !mario.jumping) {
                            mario.jumping = true;
                            jumpingPointerId = event.getPointerId(event.getActionIndex());
                            mario.setVelocity(JUMP);
                        }
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    if(event.getPointerId(event.getActionIndex())==primaryPointerId)
                        mario.moving = false;
                    else if (mario.jumping && mario.getVelocity() < JUMP / 2 && jumpingPointerId==event.getPointerId(event.getActionIndex()))
                        mario.setVelocity(JUMP / 2);
                    else if(mario.running && runningPointerId == event.getPointerId(event.getActionIndex()))
                        mario.running = false;
                    break;
            }
        }

        return true;
    }

}
