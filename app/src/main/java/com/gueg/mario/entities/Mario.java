package com.gueg.mario.entities;

import android.content.res.Resources;
import android.graphics.Rect;

import com.gueg.mario.R;
import com.gueg.mario.components.Animations;
import com.gueg.mario.components.GraphicsComponent;
import com.gueg.mario.components.MarioBehavior;
import com.gueg.mario.components.PhysicsComponent;
import com.gueg.mario.input.Input;
import com.gueg.mario.input.InputComponent;

import java.util.HashMap;

public class Mario extends CollideableGameObject {

    public enum State {
        IDLE_L,
        IDLE_R,
        WALK_L,
        WALK_R,
        RUN_L,
        RUN_R,
        JUMP_L,
        JUMP_R,
        FALL_L,
        FALL_R
    }

    private static final int MARIO_FRAME_DURATION = 100;

    private State _state = State.IDLE_R;
    private int _direction = RIGHT;

    private MarioBehavior _behavior;
    private GraphicsComponent _graphics;
    private InputComponent _input;
    private PhysicsComponent _physics;
    // Necessary for cloning
    private Rect _screenRect;

    public Mario(Resources res, MarioBehavior.MarioEvents events, Rect screenRect) {
        setRes(res);
        setGravity(true);
        _screenRect = screenRect;
        _behavior = new MarioBehavior(events, screenRect);
        _physics = new PhysicsComponent(this);
        _input = new InputComponent(this);


        HashMap<State, Integer[]> sprites = new HashMap<>();
        sprites.put(State.IDLE_R, new Integer[]{R.drawable.walking_0});
        sprites.put(State.IDLE_L, new Integer[]{R.drawable.walking_2});
        sprites.put(State.WALK_R, new Integer[]{R.drawable.walking_0, R.drawable.walking_1});
        sprites.put(State.WALK_L, new Integer[]{R.drawable.walking_2, R.drawable.walking_3});
        sprites.put(State.RUN_R, new Integer[]{R.drawable.running_0, R.drawable.running_1});
        sprites.put(State.RUN_L, new Integer[]{R.drawable.running_2, R.drawable.running_3});
        sprites.put(State.JUMP_R, new Integer[]{R.drawable.jumping_0});
        sprites.put(State.JUMP_L, new Integer[]{R.drawable.jumping_1});
        sprites.put(State.FALL_R, new Integer[]{R.drawable.falling_0});
        sprites.put(State.FALL_L, new Integer[]{R.drawable.falling_1});
        _graphics = new GraphicsComponent(this, new Animations<>(sprites), MARIO_FRAME_DURATION);
    }

    // PHYSICS
    @Override
    public void setObjectsAround(HashMap<Integer, GameObject> objectsAround) {
        _physics.setObjectsAround(objectsAround);
    }
    // --- Collisions
    @Override
    public void onCollisionXOccured() {
        _input.slowDownQuickly();
    }
    @Override
    public void onCollisionYOccured() {
        setVelocityY(0);
    }

    // MAIN LOOP
    @Override
    public void update() {
        _input.update();
        _physics.update();
        _behavior.onNewFrame(this);
        _graphics.update();
    }

    @Override
    public int getResId() {
        return _graphics.getResId();
    }

    @Override
    public Mario clone() {
        return new Mario(_res, _behavior.getListener(), _screenRect);
    }

    // GETTERS
    public boolean isFalling() {
        return getVelocityY() > 0;
    }
    public boolean isOnGround() {
        return _physics.isOnGround();
    }
    public boolean isJumping() {
        return getVelocityY() < 0;
    }
    public boolean isWalking() {
        return Math.abs(getVelocityX()) <= InputComponent.MAX_SPEED_X_WALKING;
    }
    public void setDirection(int direction) {
        _direction = direction;
    }
    public int getDirection() {
        return _direction;
    }
    public Input.InputListener getInputListener() {
        return _input;
    }


    // STATE
    public void setState(State state) {
        _state = state;
    }
    public State getState() {
        return _state;
    }



}
