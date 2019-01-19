package com.gueg.mario;

import android.annotation.SuppressLint;

import com.gueg.mario.entities.CollideableGameObject;
import com.gueg.mario.entities.GameObject;

import java.util.HashMap;

import static com.gueg.mario.entities.GameObject.AT_BOTTOM;
import static com.gueg.mario.entities.GameObject.AT_LEFT;
import static com.gueg.mario.entities.GameObject.AT_RIGHT;
import static com.gueg.mario.entities.GameObject.AT_TOP;
import static com.gueg.mario.entities.GameObject.BASE_WIDTH;

public class CollisionDetector {

    private static final int MAX_SHIFT_X = BASE_WIDTH/8;

    private SynchronizedArrayList<GameObject> _objects;
    private GameObject _obj;

    public CollisionDetector(SynchronizedArrayList<GameObject> objects) {
        _objects = objects;
    }

    public void update() {
        for(GameObject obj : _objects) {
            if(obj instanceof CollideableGameObject) {
                _obj = obj;

                ((CollideableGameObject)_obj).setObjectsAround(getObjectsAround());
            }
        }
    }



    @SuppressLint("UseSparseArrays")
    private HashMap<Integer,GameObject> getObjectsAround() {
        HashMap<Integer,GameObject> map = new HashMap<>();
        for(GameObject obj : _objects) {
            if(obj!=_obj) {
                if ((obj.getHitboxDir()==-1||obj.getHitboxDir()==AT_TOP)&&isOnTopOf(obj))
                    map.put(AT_BOTTOM, obj);
                else if ((obj.getHitboxDir()==-1||obj.getHitboxDir()==AT_LEFT)&&isAtLeftOf(obj))
                    map.put(AT_RIGHT, obj);
                else if ((obj.getHitboxDir()==-1||obj.getHitboxDir()==AT_RIGHT)&&isAtRightOf(obj))
                    map.put(AT_LEFT, obj);
                else if ((obj.getHitboxDir()==-1||obj.getHitboxDir()==AT_BOTTOM)&&isBelow(obj))
                    map.put(AT_TOP, obj);
            }
        }

        return map;
    }

    private boolean isOnTopOf(GameObject obj) {
        if(obj==null || !obj.isSolid())
            return false;

        switch (obj.getHitboxDir()) {
            case AT_TOP:
                return  _obj.getPos().bottom + _obj.getVelocityY() >= obj.getPos().top &&
                        _obj.getPos().bottom + _obj.getVelocityY() <= obj.getPos().top + obj.getHitboxSpan() &&
                        _obj.getPos().centerX() > obj.getPos().left - MAX_SHIFT_X &&
                        _obj.getPos().centerX() < obj.getPos().right + MAX_SHIFT_X;
            default:
                return  _obj.getPos().bottom + _obj.getVelocityY() >= obj.getPos().top &&
                        _obj.getPos().bottom + _obj.getVelocityY() <= obj.getPos().bottom &&
                        _obj.getPos().centerX() > obj.getPos().left - MAX_SHIFT_X &&
                        _obj.getPos().centerX() < obj.getPos().right + MAX_SHIFT_X;
        }
    }

    private boolean isBelow(GameObject obj) {
        if(obj==null || !obj.isSolid())
            return false;

        switch (obj.getHitboxDir()) {
            case AT_BOTTOM:
                return  _obj.getPos().top + _obj.getVelocityY() >= obj.getPos().bottom - obj.getHitboxSpan() &&
                        _obj.getPos().top + _obj.getVelocityY() <= obj.getPos().bottom &&
                        _obj.getPos().centerX() > obj.getPos().left - MAX_SHIFT_X &&
                        _obj.getPos().centerX() < obj.getPos().right + MAX_SHIFT_X;
            default:
                return  _obj.getPos().top + _obj.getVelocityY() >= obj.getPos().top &&
                        _obj.getPos().top + _obj.getVelocityY() <= obj.getPos().bottom &&
                        _obj.getPos().centerX() > obj.getPos().left - MAX_SHIFT_X &&
                        _obj.getPos().centerX() < obj.getPos().right + MAX_SHIFT_X;
        }
    }

    private boolean isAtLeftOf(GameObject obj) {
        if(obj==null || !obj.isSolid())
            return false;

        switch (obj.getHitboxDir()) {
            case AT_LEFT:
                return ((obj.getPos().bottom > _obj.getPos().bottom && obj.getPos().top < _obj.getPos().bottom)||(obj.getPos().bottom > _obj.getPos().top && obj.getPos().top < _obj.getPos().top)) &&
                        _obj.getPos().right + _obj.getVelocityX() >= obj.getPos().left && _obj.getPos().right + _obj.getVelocityX() <= obj.getPos().left + obj.getHitboxSpan();
            default:
                return ((obj.getPos().bottom > _obj.getPos().bottom && obj.getPos().top < _obj.getPos().bottom)||(obj.getPos().bottom > _obj.getPos().top && obj.getPos().top < _obj.getPos().top)) &&
                        _obj.getPos().right + _obj.getVelocityX() >= obj.getPos().left && _obj.getPos().right + _obj.getVelocityX() <= obj.getPos().right;

        }
    }

    private boolean isAtRightOf(GameObject obj) {
        if(obj==null || !obj.isSolid())
            return false;

        switch (obj.getHitboxDir()) {
            case AT_LEFT:
                return  ((obj.getPos().bottom > _obj.getPos().bottom && obj.getPos().top < _obj.getPos().bottom)||(obj.getPos().bottom > _obj.getPos().top && obj.getPos().top < _obj.getPos().top)) &&
                        _obj.getPos().left + _obj.getVelocityX() <= obj.getPos().right && _obj.getPos().left + _obj.getVelocityX() >= obj.getPos().right - obj.getHitboxSpan();
            default:
                return  ((obj.getPos().bottom > _obj.getPos().bottom && obj.getPos().top < _obj.getPos().bottom)||(obj.getPos().bottom > _obj.getPos().top && obj.getPos().top < _obj.getPos().top)) &&
                        _obj.getPos().left + _obj.getVelocityX() <= obj.getPos().right && _obj.getPos().left + _obj.getVelocityX() >= obj.getPos().left;

        }
    }



    public interface CollisionDetectorListener {
        void setObjectsAround(HashMap<Integer, GameObject> objectsAround);
        boolean hasCollisionOccured();
        void setCollisionOccured();
        void clearCollisionFlag();
    }
}
