package com.gueg.mario.input;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class InputProcessor implements View.OnTouchListener {

    /*
    Commands :

    ________________________________________
    |                                       |
    |                 JUMP                  |
    |_______________________________________|  <-- halfScreenY
    |                   |                   |
    |     LEFT/RUN      |    RUN/RIGHT      |
    |                   |                   |
    -----------------------------------------
                        ^
                        |
                   halfScreenX
     */

    private Input _lastInput;
    private int _halfScreenX;
    private int _halfScreenY;

    private int _primaryPointerId;
    private int _runningPointerId;
    private int _jumpingPointerId;

    private Input.InputListener _listener;

    public InputProcessor(Rect screenRect, Input.InputListener listener) {
        _halfScreenX = screenRect.centerX();
        _halfScreenY = screenRect.centerY();
        _lastInput = new Input();
        _listener = listener;
    }

    private static final int LOWER_SCREEN_LEFT = 0;
    private static final int LOWER_SCREEN_RIGHT = 1;
    private static final int UPPER_SCREEN = 2;

    // TOUCH EVENTS
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Input input = _lastInput;

        // SINGLE FINGER ON SCREEN
        if(event.getPointerCount()==1) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    input.clear();
                    _primaryPointerId = -1;
                    _runningPointerId = -1;
                    _jumpingPointerId = -1;
                    break;
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    switch (getScreenRegionTouched(event)) {
                        case LOWER_SCREEN_LEFT:
                            input._walkLeft = true;
                            _primaryPointerId = event.getPointerId(event.getActionIndex());
                            break;
                        case LOWER_SCREEN_RIGHT:
                            input._walkRight = true;
                            _primaryPointerId = event.getPointerId(event.getActionIndex());
                            break;
                        case UPPER_SCREEN:
                            input._jump = true;
                            _jumpingPointerId = event.getPointerId(event.getActionIndex());
                            break;
                    }
                    break;
            }
        }

        // MULTIPLE FINGERS ON SCREEN
        else {
            if(event.getActionIndex() <= 3) {
                MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
                event.getPointerCoords(event.getActionIndex(),coords);
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        // Depending on where the primary pointer is, if there is one...
                        if (_primaryPointerId != -1) {
                            switch (getScreenRegionFromCoords(coords)) {
                                case LOWER_SCREEN_LEFT:
                                case LOWER_SCREEN_RIGHT:
                                    input._run = true;
                                    _runningPointerId = event.getPointerId(event.getActionIndex());
                                    break;
                                case UPPER_SCREEN:
                                    input._jump = true;
                                    _jumpingPointerId = event.getPointerId(event.getActionIndex());
                                    break;
                            }
                        } else {
                            switch (getScreenRegionFromCoords(coords)) {
                                case LOWER_SCREEN_LEFT:
                                    input._walkLeft = true;
                                    _primaryPointerId = event.getPointerId(event.getActionIndex());
                                    break;
                                case LOWER_SCREEN_RIGHT:
                                    input._walkRight = true;
                                    _primaryPointerId = event.getPointerId(event.getActionIndex());
                                    break;
                            }
                        }
                        break; // ACTION_POINTER_DOWN

                    case MotionEvent.ACTION_POINTER_UP:
                        if (event.getActionIndex() == _primaryPointerId) {
                            input._walkLeft = false;
                            input._walkRight = false;
                            input._run = false;
                        } else if (event.getActionIndex() == _runningPointerId) {
                            input._run = false;
                        } else if (event.getActionIndex() == _jumpingPointerId) {
                            input._jump = false;
                        }
                        break; // ACTION_POINTER_UP
                }
            }
        }

        _lastInput = input;
        _listener.onInputUpdated(input);
        return true;
    }

    private int getScreenRegionTouched(MotionEvent e) {
        if(e.getY() < _halfScreenY)
            return UPPER_SCREEN;
        else if(e.getX() <= _halfScreenX)
            return LOWER_SCREEN_LEFT;
        else
            return LOWER_SCREEN_RIGHT;
    }


    private int getScreenRegionFromCoords(MotionEvent.PointerCoords e) {
        if(e.y < _halfScreenY)
            return UPPER_SCREEN;
        else if(e.x <= _halfScreenX)
            return LOWER_SCREEN_LEFT;
        else
            return LOWER_SCREEN_RIGHT;
    }
}
