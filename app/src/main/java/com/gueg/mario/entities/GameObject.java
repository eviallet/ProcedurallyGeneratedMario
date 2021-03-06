package com.gueg.mario.entities;


import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

@SuppressWarnings("ALL")
public abstract class GameObject implements Cloneable {

    public static final int X = 0;
    public static final int Y = 1;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public static final int BASE_WIDTH = 100;
    public static final int BASE_HEIGHT = 100;

    public static final int AT_TOP = 0;
    public static final int AT_LEFT = 1;
    public static final int AT_RIGHT = 2;
    public static final int AT_BOTTOM = 3;

    public static final int HITBOX_SPAN_TOP = 20;
    public static final int HITBOX_SPAN_BOTTOM = 30;

    protected Resources _res;
    private int _resId;

    private int[] _size = new int[] {BASE_WIDTH,BASE_HEIGHT};
    private Rect _pos = new Rect(0, 0, _size[X], _size[Y]);

    private Rect _drawableRect;

    private boolean _solid = false;
    private int _hitboxDir = -1;
    private int _hitboxSpan = 0;

    private boolean _gravity = false;
    private int _velocityX = 0;
    private int _velocityY = 0;

    GameObject() {}


    public abstract void update();
    public abstract int getResId();

    public Rect getDrawableRect() {
        if(_drawableRect==null) throw new AssertionError();
        return _drawableRect;
    }

    protected void setDrawableRect(int resId) {
        // necessary for equals
        _resId = resId;

        Drawable d = _res.getDrawable(resId);
        if(d!=null)
            _drawableRect = new Rect(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        else
            _drawableRect = new Rect(0, 0, 0, 0);
    }

    public void setRes(Resources res) {
        _res = res;
    }
    public int getDefaultResId() {
        return _resId;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof GameObject))
            return false;
        return ((GameObject) other).getDefaultResId()==getDefaultResId() && ((GameObject) other).getPos().equals(getPos());
    }

    public void setGravity(boolean gravity) {
        _gravity = gravity;
    }
    public boolean isAffectedByGravity() {
        return _gravity;
    }

    public void setSolid(boolean solid) {
        _solid = solid;
    }
    public boolean isSolid() {
        return _solid;
    }
	
	public void setHitboxDir(int hitboxDir) {
		_hitboxDir = hitboxDir;
	}
	public int getHitboxDir() {
		return _hitboxDir;
	}
	public void setHitboxSpan(int hitboxSpan) {
        _hitboxSpan = hitboxSpan;
    }
    public int getHitboxSpan() {
        return _hitboxSpan;
    }

    public void setSize(int x, int y) {
        _size[X] = x * BASE_WIDTH;
        _size[Y] = y * BASE_HEIGHT;
        _pos.set(_pos.left, _pos.top, _pos.left +_size[X], _pos.top + _size[Y]);
    }
    public void setSizePx(int x, int y) {
        _size[X] = x;
        _size[Y] = y;
        _pos.set(_pos.left, _pos.top, _pos.left +_size[X], _pos.top + _size[Y]);
    }
    public int[] getSize() {
        return _size;
    }


    public void setPos(int x, int y) {
        _pos.set(x, y, x + _size[X], y + _size[Y]);
    }
    public void setXPos(int x) {
        _pos.set(x, _pos.top, x + _size[X], _pos.top + _size[Y]);
    }
    public void setGridPos(int x, int y, Rect screenRect) {
        _pos.set(x * BASE_WIDTH, (screenRect.height()/BASE_HEIGHT - y) * BASE_HEIGHT, x * BASE_WIDTH + _size[X], (screenRect.height()/BASE_HEIGHT  - y)  * BASE_HEIGHT + _size[Y]);
    }
    public void setYPos(int y) {
        _pos.set(_pos.left, y, _pos.left + _size[X], y + _size[Y]);
    }
    public Rect getPos() {
        return _pos;
    }

    @Override public abstract GameObject clone();

    public GameObject spawnAtPos(int x, int y) {
        GameObject clone = clone();
        clone.setPos(x, y);
        return clone;
    }

    public GameObject spawnAtGridPos(int x, int y, Rect screenRect) {
        GameObject clone = clone();
        clone.setGridPos(x, y, screenRect);
        return clone;
    }


    public void setOffset(int speed) {
        _pos.offset(speed,0);
    }

    public void setVelocityY(float velocity) {
        _velocityY = (int) velocity;
    }
    public int getVelocityY() {
        return _velocityY;
    }
    public boolean isFalling() {
        return _velocityY > 0;
    }
    public boolean isJumping() {
        return _velocityY < 0;
    }

    public void setVelocityX(int velocityX) {
        _velocityX = velocityX;
    }
    public int getVelocityX() {
        return _velocityX;
    }

    public int getVelocityDirection() {
        return _velocityX >= 0 ? 1 : 0;
    }

    public void applyVelocityX() {
        _pos.offset(_velocityX,0);
    }
    public void applyVelocityY() {
        _pos.offset(0, _velocityY);
    }



    public boolean isOnScreen(Rect visibleScreen) {
        return Rect.intersects(_pos, visibleScreen);
    }







}
