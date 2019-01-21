package com.gueg.mario.input;

import com.gueg.mario.components.Component;
import com.gueg.mario.entities.Mario;

import static com.gueg.mario.entities.GameObject.LEFT;
import static com.gueg.mario.entities.GameObject.RIGHT;

public class InputComponent extends Component implements Input.InputListener {

    private Mario mario;

    private static final float JUMP = -30.0f;
    public static final int MAX_SPEED_X_WALKING = 9;
    public static final int MAX_SPEED_X_RUNNING = 15;

    private Input _input = new Input();
    // Prevent from jumping repeatedly while not releasing the screen
    private boolean _jumpReleased = true;

    public InputComponent(Mario mario) {
        super(mario);
        this.mario = mario;
    }


    @Override
    public void onInputUpdated(Input input) {
        _input = input;
    }

    @Override
    public void update() {
        if(_input._jump && mario.getVelocityY() == 0 && mario.isOnGround() && _jumpReleased) {
            mario.setVelocityY(JUMP);
            _jumpReleased = false;
        } else if(!_input._jump && mario.getVelocityY() < JUMP / 2) {
            mario.setVelocityY(JUMP / 2);
        } else if(!_input._jump && mario.isOnGround() && !_jumpReleased) {
            _jumpReleased = true;
        }

        if(_input._walkLeft) {
            moveLeft(_input._run);
        } else if(_input._walkRight) {
            moveRight(_input._run);
        } else
            slowDown();
    }

    private void moveLeft(boolean running) {
        if(mario.getVelocityDirection() == RIGHT)
            slowDownQuickly();
        mario.setVelocityX(mario.getVelocityX()-1);
        if(running) {
            if (mario.getVelocityX() < -MAX_SPEED_X_RUNNING)
                mario.setVelocityX(-MAX_SPEED_X_RUNNING);
        } else {
            if (mario.getVelocityX() < -MAX_SPEED_X_WALKING)
                mario.setVelocityX(-MAX_SPEED_X_WALKING);
        }
    }

    private void moveRight(boolean running) {
        if(mario.getVelocityDirection() == LEFT)
            slowDownQuickly();
        mario.setVelocityX(mario.getVelocityX() + 1);
        if(running) {
            if (mario.getVelocityX() > MAX_SPEED_X_RUNNING)
                mario.setVelocityX(MAX_SPEED_X_RUNNING);
        } else {
            if (mario.getVelocityX() > MAX_SPEED_X_WALKING)
                mario.setVelocityX(MAX_SPEED_X_WALKING);
        }
    }

    private void slowDown() {
        if(mario.getVelocityX() == 0)
            return;
        if(mario.getVelocityDirection() == RIGHT)
            mario.setVelocityX(mario.getVelocityX() - 1);
        else
            mario.setVelocityX(mario.getVelocityX() + 1);
    }

    public void slowDownQuickly() {
        if(mario.getVelocityX()!=0) {
            if (mario.getVelocityDirection() == RIGHT) {
                mario.setVelocityX(mario.getVelocityX() - 3);
                if(mario.getVelocityDirection() == LEFT)
                    mario.setVelocityX(0);
            }
            else {
                mario.setVelocityX(mario.getVelocityX() + 3);
                if(mario.getVelocityDirection() == RIGHT)
                    mario.setVelocityX(0);
            }
        }
    }


}
