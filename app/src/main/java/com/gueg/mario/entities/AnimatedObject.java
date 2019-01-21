package com.gueg.mario.entities;

import android.content.res.Resources;

import com.gueg.mario.components.Animations;
import com.gueg.mario.components.GraphicsComponent;

public class AnimatedObject extends GameObject {

    private static final int OBJECT_FRAME_DURATION = 300;

    private GraphicsComponent _graphics;
    // Necessary for cloning (defining drawableRect with a resId)
    private int _resId;

    private AnimatedObject(Resources res, int resId, GraphicsComponent graphics, boolean gravity, boolean solid) {
        setRes(res);
        _graphics = graphics;
        setGravity(gravity);
        setSolid(solid);
        setDrawableRect(resId);
    }


    // used by GameObjectFactory
    @SuppressWarnings("unused")
    AnimatedObject() {}

    void setSprites(Integer[] sprites) {
        _graphics = new GraphicsComponent(this, new Animations<>(sprites), OBJECT_FRAME_DURATION);
        _resId = sprites[0];
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
        return new AnimatedObject(_res, _resId, _graphics, isAffectedByGravity(), isSolid());
    }
}
