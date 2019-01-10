package com.gueg.mario.components;

import com.gueg.mario.entities.AnimatedObject;
import com.gueg.mario.entities.Enemy;
import com.gueg.mario.entities.GameObject;
import com.gueg.mario.entities.Mario;

import static com.gueg.mario.entities.AnimatedObject.DEFAULT_STATE;

public class GraphicsComponent extends Component {

    private long _ticker = System.currentTimeMillis();
    private Animations _animations;
    private int _frameDuration;

    public GraphicsComponent(GameObject obj, Animations animations, int frameDuration) {
        super(obj);
        _animations = animations;
        _frameDuration = frameDuration;
    }

    public void update() {
        if (System.currentTimeMillis() - _ticker >= _frameDuration) {
            _ticker = System.currentTimeMillis();
            if(_obj instanceof Mario)
                _animations.nextFrameForState(((Mario) _obj).getState());
            if(_obj instanceof Enemy)
                _animations.nextFrameForState(_obj.getVelocityDirection());
            if(_obj instanceof AnimatedObject)
                _animations.nextFrameForState(DEFAULT_STATE);
        }
    }

    public int getResId() {
        return _animations.getCurrentResId();
    }
}
