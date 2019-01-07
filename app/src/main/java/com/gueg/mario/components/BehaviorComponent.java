package com.gueg.mario.components;

import com.gueg.mario.entities.GameObject;

public class BehaviorComponent extends Component {

    private Behavior _behavior;


    public BehaviorComponent(GameObject obj, Behavior behavior) {
        super(obj);
        _behavior = behavior;
    }

    public void update() {
        _behavior.onNewFrame(_obj);
    }


    public interface Behavior<T extends GameObject> {
        void onNewFrame(T obj);
    }

}
