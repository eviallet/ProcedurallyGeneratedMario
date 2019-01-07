package com.gueg.mario.entities;

import android.content.res.Resources;

public class UnanimatedObject extends GameObject {

    private int _resId;

    public UnanimatedObject(Resources res, int resId, boolean solid) {
        super(res, false);
        setSolid(solid);

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
