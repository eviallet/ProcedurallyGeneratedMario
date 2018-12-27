package com.gueg.mario;


import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public abstract class GameObject {

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
        _pos = new Rect(0,0,_size[0],_size[1]);
    }

    public void setSolid(boolean state) {
        _solid = state;
    }

    public boolean isSolid() {
        return _solid;
    }

    protected void setResId(int resId) {
        _resId = resId;
    }

    public void setSize(int x, int y) {
        _size[0]=x*BASE_WIDTH;
        _size[1]=y*BASE_HEIGHT;
        _pos.set(_pos.left, _pos.top, _pos.left +_size[0], _pos.top + _size[1]);
    }

    public int[] getSize() {
        return _size;
    }

    public Rect getDrawableRect() {
        Drawable d = _res.getDrawable(_resId);
        if(d!=null)
            return new Rect(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        else
            return new Rect(0,0,0,0);
    }

    public int getResId() {
        return _resId;
    }

    public void setPos(int x, int y) {
        _pos.set(x,y,x+_size[0],y+_size[1]);
    }
    public Rect getPos() {
        return _pos;
    }

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








}
