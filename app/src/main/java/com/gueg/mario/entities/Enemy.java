package com.gueg.mario.entities;

import android.content.res.Resources;

import com.gueg.mario.R;
import com.gueg.mario.components.Animations;
import com.gueg.mario.components.GraphicsComponent;
import com.gueg.mario.components.PhysicsComponent;

import java.util.HashMap;


public class Enemy extends CollideableGameObject {

    private static final int ENEMY_FRAME_DURATION = 400;
    public static final int DEFAULT_SPEED = 6;
    public static final int BILLBALL_SPEED = 14;

    private GraphicsComponent _graphics;
    private PhysicsComponent _physics;
    // required for cloning


    public Enemy(Resources res, int speed, boolean gravity) {
        super(res, gravity);
        setVelocityX(-speed); // going left by default

        HashMap<Integer, Integer[]> sprites = new HashMap<>();
        sprites.put(
                LEFT,
                new Integer[]{
                        R.drawable.goomba_2,
                        R.drawable.goomba_3
                });
        sprites.put(
                RIGHT,
                new Integer[]{
                        R.drawable.goomba_0,
                        R.drawable.goomba_1
                });
        _graphics = new GraphicsComponent(this, new Animations<>(sprites), ENEMY_FRAME_DURATION);
        _physics = new PhysicsComponent(this);
    }


    @Override
    public void setObjectsAround(HashMap<Integer, GameObject> objectsAround) {
        _physics.setObjectsAround(objectsAround);
    }

    @Override
    public void onCollisionXOccured() {
        setVelocityX(-getVelocityX());
    }
    @Override
    public void onCollisionYOccured() {
    }

    @Override
    public Enemy clone() {
        return new Enemy(_res, getVelocityX(), isAffectedByGravity());
    }


    public void update() {
        _physics.update();
        _graphics.update();
    }

    @Override
    public int getResId() {
        return _graphics.getResId();
    }


}
