package com.gueg.mario.entities;

import android.content.res.Resources;

import com.gueg.mario.components.Animations;
import com.gueg.mario.components.GraphicsComponent;

public class AnimatedObject extends GameObject {

    private static final int OBJECT_FRAME_DURATION = 400;

    private GraphicsComponent _graphics;
    private Integer[] _resId;
    public static final int DEFAULT_STATE = 0;

    private AnimatedObject(Resources res, Integer resId[], boolean gravity, boolean solid) {
        setRes(res);
        _resId = resId;
        setGravity(gravity);
        setSolid(solid);

        _graphics = new GraphicsComponent(this, new Animations<>(DEFAULT_STATE, _resId), OBJECT_FRAME_DURATION);
    }

    // used by GameObjectFactory
    @SuppressWarnings("unused")
    AnimatedObject() {}

    void setSprites(Integer[] sprites) {
        _resId = sprites;
        _graphics = new GraphicsComponent(this, new Animations<>(DEFAULT_STATE, sprites), OBJECT_FRAME_DURATION);
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
