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
            if (isOnTopOf(obj))
                map.put(AT_BOTTOM, obj);
            else if (isAtLeftOf(obj))
                map.put(AT_RIGHT, obj);
            else if (isAtRightOf(obj))
                map.put(AT_LEFT, obj);
            else if (isBelow(obj))
                map.put(AT_TOP, obj);
        }

        return map;
    }

    private boolean isOnTopOf(GameObject obj) {
        return obj!=null && obj.isSolid() &&
                _obj.getPos().bottom + _obj.getVelocityY() >= obj.getPos().top &&
                _obj.getPos().bottom + _obj.getVelocityY() <= obj.getPos().bottom &&
                _obj.getPos().centerX() > obj.getPos().left - MAX_SHIFT_X &&
                _obj.getPos().centerX() < obj.getPos().right + MAX_SHIFT_X;
    }

    private boolean isBelow(GameObject obj) {
        return obj!=null && obj.isSolid() &&
                _obj.getPos().top >= obj.getPos().bottom - _obj.getVelocityY() &&
                _obj.getPos().top <= obj.getPos().bottom - _obj.getVelocityY() &&
                _obj.getPos().centerX() > obj.getPos().centerX() - obj.getPos().width()/2 - MAX_SHIFT_X &&
                _obj.getPos().centerX() < obj.getPos().centerX() + obj.getPos().width()/2 + MAX_SHIFT_X;
    }

    private boolean isAtLeftOf(GameObject obj) {
        return obj!=null && obj.isSolid() &&
                ((obj.getPos().bottom >= _obj.getPos().bottom-2 && obj.getPos().top <= _obj.getPos().bottom-2)||(obj.getPos().bottom >= _obj.getPos().top-2 && obj.getPos().top <= _obj.getPos().top-2)) &&
                _obj.getPos().right + _obj.getVelocityX() >= obj.getPos().left && _obj.getPos().right + _obj.getVelocityX() <= obj.getPos().right;
    }

    private boolean isAtRightOf(GameObject obj) {
        return obj!=null && obj.isSolid() &&
                ((obj.getPos().bottom >= _obj.getPos().bottom-2 && obj.getPos().top <= _obj.getPos().bottom-2)||(obj.getPos().bottom >= _obj.getPos().top-2 && obj.getPos().top <= _obj.getPos().top-2)) &&
                _obj.getPos().left + _obj.getVelocityX() <= obj.getPos().right && _obj.getPos().left + _obj.getVelocityX() >= obj.getPos().left;
    }



    public interface Collideable {
        void setObjectsAround(HashMap<Integer, GameObject> objectsAround);
        boolean hasCollisionOccured();
        void setCollisionOccured();
        void clearCollisionFlag();
    }
}
