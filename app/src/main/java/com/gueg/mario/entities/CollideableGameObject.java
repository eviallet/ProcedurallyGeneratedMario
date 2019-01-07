package com.gueg.mario.entities;

import android.content.res.Resources;

import com.gueg.mario.CollisionDetector;

import java.util.HashMap;


public abstract class CollideableGameObject extends GameObject implements CollisionDetector.Collideable {

    private boolean _collisionOccured = false;

    public CollideableGameObject(Resources res, boolean gravity) {
        super(res, gravity);
    }


    @Override
    public boolean hasCollisionOccured() {
        return _collisionOccured;
    }
    @Override
    public void setCollisionOccured() {
        _collisionOccured = true;
    }
    @Override
    public void clearCollisionFlag() {
        _collisionOccured = false;
    }
    public abstract void setObjectsAround(HashMap<Integer, GameObject> objectsAround);
}
