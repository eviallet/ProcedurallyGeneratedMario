package com.gueg.mario.entities;


import android.content.res.Resources;

public class UnanimatedObject extends GameObject {

    private int _resId;

    private UnanimatedObject(Resources res, int resId, int[] size, boolean isSolid, int hitboxDir, int hitboxSpan) {
        setRes(res);
        _resId = resId;
        setSizePx(size[X], size[Y]);
        setSolid(isSolid);
        setHitboxDir(hitboxDir);
        setHitboxSpan(hitboxSpan);
        setDrawableRect(_resId);
    }

    // used by GameObjectFactory
    @SuppressWarnings("unused")
    UnanimatedObject() {}

    void setSprite(int resId) {
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
        return new UnanimatedObject(_res, _resId, getSize(), isSolid(), getHitboxDir(), getHitboxSpan());
    }
}
