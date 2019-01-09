package com.gueg.mario.entities;

import android.content.res.Resources;

import com.gueg.mario.components.Animations;
import com.gueg.mario.components.GraphicsComponent;

public class AnimatedObject extends GameObject {

    private static final int OBJECT_FRAME_DURATION = 400;

    private GraphicsComponent _graphics;
    private Integer[] _resId;
    public static final int DEFAULT_STATE = 0;

    public AnimatedObject(Resources res, Integer resId[], boolean gravity, boolean solid) {
        super(res, gravity);
        _resId = resId;
        setSolid(solid);

        _graphics = new GraphicsComponent(this, new Animations<>(DEFAULT_STATE, resId), OBJECT_FRAME_DURATION);
    }


    @Override
    public void update() {
        _graphics.update();
    }

    @Override
    public int getResId() {
        return _graphics.getResId();
    }

    @Override
    public GameObject clone() {
        return new AnimatedObject(_res, _resId, isAffectedByGravity(), isSolid());
    }
}
