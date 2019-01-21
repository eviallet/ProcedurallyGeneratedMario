package com.gueg.mario.entities;

import android.annotation.SuppressLint;
import android.content.res.Resources;

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
    // required for factorying
    private HashMap<Integer, Integer[]> _sprites;


    private Enemy(Resources res, int resId, int speed, boolean gravity, GraphicsComponent graphics, PhysicsComponent physics) {
        setRes(res);
        setVelocityX(speed);
        setGravity(gravity);

        _graphics = graphics;
        setDrawableRect(resId);
        _physics = physics;
    }


    // used by GameObjectFactory
    @SuppressWarnings("unused")
    Enemy() {
        _physics = new PhysicsComponent(this);
    }

    @SuppressLint("UseSparseArrays")
    void putSprites(Integer state, Integer[] sprites) {
        if(_sprites == null)
            _sprites = new HashMap<>();
        _sprites.put(state, sprites);
    }

    void commitSprites() {
        _graphics = new GraphicsComponent(this, new Animations<>(_sprites), ENEMY_FRAME_DURATION);
    }

    void setSpeed(int speed) {
        setVelocityX(-speed); // going left by default
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
        return new Enemy(_res, _sprites.get(0)[0], getVelocityX(), isAffectedByGravity(), _graphics, _physics);
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
