package com.gueg.mario;

import android.content.res.Resources;
import android.util.Log;

public class AnimatedObject extends GameObject {

    private int[] _resId;
    private int _index = 0;

    protected String FRAME_WALK = "w";
    protected String FRAME_RUN = "r";


    public AnimatedObject(Resources res, int[] resId, boolean gravity) {
        super(res,resId[0], gravity);
        _resId = resId;
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
    protected void nextFrame(String state) {
        if(state.equals(FRAME_WALK)) {
            if(_index>=2)
                _index=0;
            _index++;
            if (_index >= 2)
                _index = 0;
        } else if(state.equals(FRAME_RUN)) {
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
            if (direction)
                setResId(_resId[_index]);
            else
                setResId(_resId[_index + _resId.length / 2]);
        }
        else
            Log.w("AnimatedObject","setFrame : bad index. ("+index+"/"+_index);
    }


}
