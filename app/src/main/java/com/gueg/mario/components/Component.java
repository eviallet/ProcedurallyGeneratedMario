package com.gueg.mario.components;

import com.gueg.mario.entities.GameObject;

public abstract class Component {

    GameObject _obj;

    public Component(GameObject obj) {
        _obj = obj;
    }

    public abstract void update();
}
