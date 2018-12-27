package com.gueg.mario;

import android.content.res.Resources;

public class UnanimatedObject extends GameObject {

    public UnanimatedObject(Resources res, int resId, boolean solid) {
        super(res,resId,false);
        setSolid(solid);
    }

    public UnanimatedObject(Resources res, int resId, boolean solid, int x, int y) {
        super(res,resId,false);
        setSolid(solid);
        setPos(x,y);
    }
}
