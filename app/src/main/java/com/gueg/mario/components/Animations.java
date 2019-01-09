package com.gueg.mario.components;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.HashMap;

@SuppressLint("UseSparseArrays")
public class Animations<T> {

    private HashMap<T, Integer[]> _anim;
    private T _lastState;
    private int _resIndex = 0;

    /**
     *
     * @param resId multiple sprites ids
     */
    public Animations(T def, Integer resId[]) {
        _lastState = def;
        _anim = new HashMap<>(1);
        _anim.put(_lastState, resId);
    }

    /**
     *
     * @param wrapper : HashMap< Integer, Integer[] > with first Int being states and Int[] related resIds
     *                 example call:
     *                 HashMap<Integer, Integer[]> sprites = new HashMap<>();
     *                 sprites.put(
     *                   LEFT,
     *                   new Integer[]{
     *                   R.drawable.goomba_2,
     *                   R.drawable.goomba_3
     *                 });
     *                 Animations _anim = new Animations(sprites, 100);
     *
     */
    public Animations(HashMap<T, Integer[]> wrapper) {
        _anim = wrapper;
    }

    public void nextFrameForState(T state) {
        if(state == _lastState) {
            if(++_resIndex >= _anim.get(state).length)
                _resIndex = 0;
        } else {
            _lastState = state;
            _resIndex = 0;
        }
    }

    public int getCurrentResId() {
        Log.d(":-:","anim array length : "+_anim.size()+" index is : "+_lastState+" ; length : "+_anim.get(_lastState).length);
        return _anim.get(_lastState)[_resIndex];
    }
}
