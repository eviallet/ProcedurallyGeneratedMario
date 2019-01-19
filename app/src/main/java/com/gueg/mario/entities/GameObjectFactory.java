package com.gueg.mario.entities;

import android.content.res.Resources;

public class GameObjectFactory<T extends GameObject> {

    private T _obj;

    public GameObjectFactory(Class<T> objectClass, Resources res) {
        _obj = newInstance(objectClass);
        if (_obj == null) throw new AssertionError();
        if (res == null) throw new AssertionError();
        _obj.setRes(res);
    }

    private T newInstance(Class<T> c) {
        try {
            return c.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GameObjectFactory setResources(Resources res) {
        _obj.setRes(res);
        return this;
    }


    public GameObjectFactory setGravity(boolean gravity) {
        _obj.setGravity(gravity);
        return this;
    }

    public GameObjectFactory setSolid(boolean solid) {
        _obj.setSolid(solid);
        return this;
    }

    public GameObjectFactory setHitboxDir(int hitboxDir) {
        _obj.setHitboxDir(hitboxDir);
        return this;
    }

    public GameObjectFactory setHitboxSpan(int hitboxSpan) {
        _obj.setHitboxSpan(hitboxSpan);
        return this;
    }


    public GameObjectFactory setSprite(int resId) {
        if (!(_obj instanceof UnanimatedObject)) throw new AssertionError();
        ((UnanimatedObject)_obj).setSprite(resId);
        return this;
    }

    public GameObjectFactory setSprites(Integer[] sprites) {
        if (!(_obj instanceof AnimatedObject)) throw new AssertionError();
        ((AnimatedObject)_obj).setSprites(sprites);
        return this;
    }

    public GameObjectFactory putSprites(Integer state, Integer[] sprites) {
        if (!(_obj instanceof Enemy)) throw new AssertionError();
        ((Enemy)_obj).putSprites(state, sprites);
        return this;
    }

    public GameObjectFactory setSpeed(int speed) {
        if (!(_obj instanceof Enemy)) throw new AssertionError();
        ((Enemy)_obj).setSpeed(speed);
        return this;
    }


    public T build() {
        if(_obj instanceof Enemy)
            ((Enemy)_obj).commitSprites();

        return _obj;
    }
}
