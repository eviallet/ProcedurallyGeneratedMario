package com.gueg.mario.input;

public class Input {

    public boolean _walkLeft;
    public boolean _walkRight;
    public boolean _run;
    public boolean _jump;

    public Input() {
        _walkLeft = false;
        _walkRight = false;
        _run = false;
        _jump = false;
    }

    public void clear() {
        _walkLeft = false;
        _walkRight = false;
        _run = false;
        _jump = false;
    }


    public interface InputListener {
        void onInputUpdated(Input iput);
    }


}
