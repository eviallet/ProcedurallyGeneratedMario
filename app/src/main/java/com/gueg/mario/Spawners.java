package com.gueg.mario;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.gueg.mario.objects.AnimatedObject;
import com.gueg.mario.objects.Enemy;
import com.gueg.mario.objects.GameObject;
import com.gueg.mario.objects.UnanimatedObject;

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
        AnimatedObject qblock = new AnimatedObject(res, new int[] {
                R.drawable.qblock_0,
                R.drawable.qblock_1,
                R.drawable.qblock_2,
                R.drawable.qblock_3},
                false,
                true);
        _spawners.put(Tiles.QUESTION.getVal(), qblock);
        Enemy goomba = new Enemy(res, new int[]{
                R.drawable.goomba_0,
                R.drawable.goomba_1,
                R.drawable.goomba_2,
                R.drawable.goomba_3},
                Enemy.DEFAULT_SPEED,
                true);
        _spawners.put(Tiles.GOOMBA.getVal(), goomba);
        Enemy billball = new Enemy(res, new int[]{
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
