package com.gueg.mario.objects;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public abstract class GameObject implements Cloneable {

    private static final int X = 0;
    private static final int Y = 1;

    public static final boolean LEFT = false;
    public static final boolean RIGHT = true;

    public static final int BASE_WIDTH = 100;
    public static final int BASE_HEIGHT = 100;
    public static final int GRAVITY = 2;

    protected Resources _res;
    protected int _resId;

    protected Rect _pos;

    protected int[] _size;

    protected boolean _solid;
    protected boolean _gravity;

    protected int _velocityX = 0;
    protected int _velocityY = 0;

    public GameObject(Resources res, int resId, boolean gravity) {
        _res = res;
        _resId = resId;
        _solid = false;
        _gravity = gravity;
        _size = new int[] {BASE_WIDTH,BASE_HEIGHT};
        _pos = new Rect(0, 0, _size[X], _size[Y]);
    }

    public void setSolid(boolean solid) {
        _solid = solid;
    }

    public boolean isSolid() {
        return _solid;
    }

    protected void setResId(int resId) {
        _resId = resId;
    }

    public void setSize(int x, int y) {
        _size[X] = x * BASE_WIDTH;
        _size[Y] = y * BASE_HEIGHT;
        _pos.set(_pos.left, _pos.top, _pos.left +_size[X], _pos.top + _size[Y]);
    }
    public int[] getSize() {
        return _size;
    }

    public Rect getDrawableRect() {
        Drawable d = _res.getDrawable(_resId);
        if(d!=null)
            return new Rect(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        else
            return new Rect(0, 0, 0, 0);
    }

    public int getResId() {
        return _resId;
    }

    public void setPos(int x, int y) {
        _pos.set(x, y, x + _size[X], y + _size[Y]);
    }
    public Rect getPos() {
        return _pos;
    }

    public GameObject spawnAtPos(int x, int y) {
        GameObject clone = clone();
        clone.setPos(x, y);
        return clone;
    }

    @Override
    public abstract GameObject clone();

    public void setOffset(boolean direction, int speed) {
        if(direction)
            _pos.offset(-speed,0);
        else
            _pos.offset(speed,0);
    }

    private int MAX_VELOCITY_Y = 20;
    public void setVelocity(float velocity) {
        _velocityY = (int) velocity;
    }

    public void applyGravity() {
        _velocityY+=GRAVITY;
        if(_velocityY>MAX_VELOCITY_Y)
            _velocityY = MAX_VELOCITY_Y;
    }

    public int getVelocity() {
        return _velocityY;
    }

    public boolean isFalling() {
        return _velocityY!=0;
    }

    public boolean getGravityState() {
        return _gravity;
    }

    public void moveX() {
        _pos.offset(_velocityX,0);
    }

    public void moveY() {
        _pos.offset(0, _velocityY);
    }


    public int getSpeed() {
        return _velocityX;
    }

    public boolean getDirection() {
        return _velocityX >= 0;
    }


    public void setSpeed(int speed) {
        _velocityX = speed;
    }


    public boolean isOnScreen(Rect visibleScreen) {
        return Rect.intersects(_pos, visibleScreen);
    }


    private static int MAX_SHIFT_X = BASE_WIDTH/4;
    public boolean isOnTopOf(GameObject obj) {
        return obj!=null && obj.isSolid() &&
               _pos.bottom + _velocityY >= obj.getPos().top &&
               _pos.bottom + _velocityY <= obj.getPos().bottom &&
               _pos.centerX() > obj.getPos().left - MAX_SHIFT_X &&
               _pos.centerX() < obj.getPos().right + MAX_SHIFT_X;
    }

    public boolean isBelow(GameObject obj) {
        return obj!=null && obj.isSolid() &&
               _pos.top >= obj.getPos().bottom - _velocityY &&
               _pos.top <= obj.getPos().bottom - _velocityY &&
               _pos.centerX() > obj.getPos().centerX() - obj.getPos().width()/2 - MAX_SHIFT_X &&
               _pos.centerX() < obj.getPos().centerX() + obj.getPos().width()/2 + MAX_SHIFT_X;
    }

    public boolean isAtLeftOf(GameObject obj) {
        return obj!=null && obj.isSolid() &&
                ((obj.getPos().bottom >= _pos.bottom-2 && obj.getPos().top <= _pos.bottom-2)||(obj.getPos().bottom >= _pos.top-2 && obj.getPos().top <= _pos.top-2)) &&
               _pos.right + _velocityX >= obj.getPos().left && _pos.right + _velocityX <= obj.getPos().right;
    }

    public boolean isAtRightOf(GameObject obj) {
        return obj!=null && obj.isSolid() &&
                ((obj.getPos().bottom >= _pos.bottom-2 && obj.getPos().top <= _pos.bottom-2)||(obj.getPos().bottom >= _pos.top-2 && obj.getPos().top <= _pos.top-2)) &&
               _pos.left + _velocityX <= obj.getPos().right && _pos.left + _velocityX >= obj.getPos().left;
    }


    public static final int AT_TOP = 0;
    public static final int AT_LEFT = 1;
    public static final int AT_RIGHT = 2;
    public static final int AT_BOTTOM = 3;

    @SuppressLint("UseSparseArrays")
    public static HashMap<Integer,GameObject> getObjectsAround(ArrayList<GameObject> objects, ArrayList<Enemy> enemies, GameObject o) {
        HashMap<Integer,GameObject> map = new HashMap<>();
        for(GameObject obj : objects) {
            if (o.isOnTopOf(obj))
                map.put(AT_BOTTOM, obj);
            else if (o.isAtLeftOf(obj))
                map.put(AT_RIGHT, obj);
            else if (o.isAtRightOf(obj))
                map.put(AT_LEFT, obj);
            else if (o.isBelow(obj))
                map.put(AT_TOP, obj);
        }

        for(Enemy en : enemies) {
            if (o.isOnTopOf(en))
                map.put(AT_BOTTOM, en);
            else if (o.isAtLeftOf(en))
                map.put(AT_RIGHT, en);
            else if (o.isAtRightOf(en))
                map.put(AT_LEFT, en);
            else if (o.isBelow(en))
                map.put(AT_TOP, en);
        }

        return map;
    }






}
