package com.gueg.mario.objects;

import android.content.res.Resources;
import android.util.Log;

public class AnimatedObject extends GameObject {

    protected int[] _resId;
    private int _index = 0;

    enum Frames {
        WALK,
        RUN
    }


    public AnimatedObject(Resources res, int[] resId, boolean gravity) {
        super(res, resId[0], gravity);
        _resId = resId;
    }

    public AnimatedObject(Resources res, int[] resId, boolean gravity, boolean solid) {
        this(res, resId, gravity);
        setSolid(solid);
    }

    /**
     * Blocks and static objects
     */
    public void nextFrame() {
        _index++;
        if(_index==_resId.length)
            _index=0;
        super.setResId(_resId[_index]);
    }

    /**
     * Ennemies
     * @param maxIndex
     */
    public void nextFrame(int maxIndex) {
        _index++;
        if(_index>=maxIndex)
            _index=0;
        if(getDirection())
            setResId(_resId[_index]);
        else
            setResId(_resId[_index+_resId.length/2]);
    }

    /**
     * Mario
     * @param state
     */
    protected void nextFrame(Frames state) {
        if(state.equals(Frames.WALK)) {
            if(_index>=2)
                _index=0;
            _index++;
            if (_index >= 2)
                _index = 0;
        } else if(state.equals(Frames.RUN)) {
            if(_index<4||_index>=6)
                _index=4;
            _index++;
            if (_index >= 6)
                _index = 4;
        }


        if(getDirection())
            setResId(_resId[_index]);
        else
            setResId(_resId[_index+_resId.length/2]);
    }



    protected void setFrame(int index, boolean direction) {
        _index = index;

        if(!(_index+_resId.length/2 > _resId.length)) {
            if (direction==RIGHT)
                setResId(_resId[_index]);
            else
                setResId(_resId[_index + _resId.length / 2]);
        }
        else
            Log.w("AnimatedObject","setFrame : bad index. ("+index+"/"+_index);
    }

    @Override
    public AnimatedObject clone() {
        return new AnimatedObject(_res, _resId, _gravity);
    }


}