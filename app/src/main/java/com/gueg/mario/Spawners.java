package com.gueg.mario;

import android.content.res.Resources;

import java.util.HashMap;

public class Spawners {

    private HashMap<Integer, GameObject> _spawners = new HashMap<>();

    UnanimatedObject ground;
    UnanimatedObject block;
    Enemy goomba;
    Enemy billball;

    public Spawners(Resources res) {
        ground = new UnanimatedObject(res,
                R.drawable.ground_0,
                true);
        _spawners.put(Tiles.UP.getVal(), ground);
        block = new UnanimatedObject(res,
                R.drawable.block_0,
                true);
        _spawners.put(Tiles.BRICK.getVal(), block);
        goomba = new Enemy(res, new int[]{
                R.drawable.goomba_0,
                R.drawable.goomba_1,
                R.drawable.goomba_2,
                R.drawable.goomba_3},
                Enemy.DEFAULT_SPEED,
                true);
        _spawners.put(Tiles.GOOMBA.getVal(), goomba);
        billball = new Enemy(res, new int[]{
                R.drawable.billball_0,
                R.drawable.billball_1},
                Enemy.BILLBALL_SPEED,
                false);
        _spawners.put(Tiles.BILL_BALL.getVal(), billball);
    }

    public GameObject at(int i) {
        return _spawners.get(i);
    }
}
