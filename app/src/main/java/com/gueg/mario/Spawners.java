package com.gueg.mario;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.gueg.mario.entities.AnimatedObject;
import com.gueg.mario.entities.GameObject;
import com.gueg.mario.entities.UnanimatedObject;

import java.util.HashMap;

public class Spawners {

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, GameObject> _spawners = new HashMap<>();


    public Spawners(Resources res) {
        UnanimatedObject ground = new UnanimatedObject(res,
                R.drawable.ground_0,
                true);
        _spawners.put(Tiles.UP.getVal(), ground);
        UnanimatedObject block = new UnanimatedObject(res,
                R.drawable.block_0,
                true);
        _spawners.put(Tiles.BRICK.getVal(), block);
        AnimatedObject qblock = new AnimatedObject(res, new Integer[] {
                R.drawable.qblock_0,
                R.drawable.qblock_1,
                R.drawable.qblock_2,
                R.drawable.qblock_3},
                false,
                true);
        _spawners.put(Tiles.QUESTION.getVal(), qblock);
        /*
        Enemy goomba = new Enemy(res,
                Enemy.DEFAULT_SPEED,
                true,
                MainActivity.objects);
        _spawners.put(Tiles.GOOMBA.getVal(), goomba);*/
        /*Enemy billball = new Enemy(res, new Integer[]{
                R.drawable.billball_0,
                R.drawable.billball_1},
                Enemy.BILLBALL_SPEED,
                false,
                MainActivity.objects);
        _spawners.put(Tiles.BILL_BALL.getVal(), billball);*/
    }

    public GameObject at(int i) {
        return _spawners.get(i);
    }
}
