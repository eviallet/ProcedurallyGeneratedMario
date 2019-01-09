package com.gueg.mario;

import java.util.ArrayList;

public class SynchronizedArrayList<T> extends ArrayList<T> {

    private ArrayList<T> _disabled;

    public SynchronizedArrayList() {
        _disabled = new ArrayList<>();
    }

    public void enable(T obj) {
        if(_disabled.contains(obj)) {
            _disabled.remove(obj);
            add(obj);
        }
    }

    public void disable(T obj) {
        if(contains(obj)) {
            remove(obj);
            _disabled.add(obj);
        }
    }

    public T getDisabledAt(int i) {
        return _disabled.get(i);
    }

    public int getDisabledSize() {
        return _disabled.size();
    }
}
