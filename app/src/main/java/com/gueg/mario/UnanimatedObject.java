package com.gueg.mario;

import android.content.res.Resources;

public class UnanimatedObject extends GameObject {

    public UnanimatedObject(Resources res, int resId, boolean solid) {
        super(res,resId,false);
        setSolid(solid);
    }

    @Override
    public UnanimatedObject clone() {
        return new UnanimatedObject(_res, _resId, _solid);
    }
}
