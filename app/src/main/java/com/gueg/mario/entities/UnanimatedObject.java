package com.gueg.mario.entities;


import android.content.res.Resources;

public class UnanimatedObject extends GameObject {

    private int _resId;

    private UnanimatedObject(Resources res, int resId, boolean isSolid) {
        setRes(res);
        _resId = resId;
        setSolid(isSolid);
    }

    // used by GameObjectFactory
    @SuppressWarnings("unused")
    UnanimatedObject() {}

    public void setSprite(int resId) {
        _resId = resId;
    }

    @Override
    public void update() {}

    @Override
    public int getResId() {
        return _resId;
    }

    @Override
    public UnanimatedObject clone() {
        return new UnanimatedObject(_res, _resId, isSolid());
    }
}
