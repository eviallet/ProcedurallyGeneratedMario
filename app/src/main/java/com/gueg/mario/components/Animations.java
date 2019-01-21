package com.gueg.mario.components;

import android.annotation.SuppressLint;

import java.util.HashMap;

@SuppressLint("UseSparseArrays")
public class Animations<T> {

    private HashMap<T, Integer[]> _anim;
    private T _lastState;
    private int _resIndex = 0;

    /**
     * @param resId multiple sprites ids
     */
    public Animations(Integer resId[]) {
        _lastState = (T)(Integer)0;
        _anim = new HashMap<>(1);
        _anim.put(_lastState, resId);
    }

    /**
     * @param wrapper : HashMap< Integer, Integer[] > with first Int being states and Int[] related resIds
     */
    public Animations(HashMap<T, Integer[]> wrapper) {
        _lastState = (T)(Integer)0;
        _anim = wrapper;
    }

    void nextFrame() {
        if(++_resIndex >= _anim.get(_lastState).length)
        _resIndex = 0;
    }

    void nextFrameForState(T state) {
        if(state == _lastState) {
            if(++_resIndex >= _anim.get(state).length)
                _resIndex = 0;
        } else {
            _lastState = state;
            _resIndex = 0;
        }
    }

    int getCurrentResId() {
        return _anim.get(_lastState)[_resIndex];
    }
}
