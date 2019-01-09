package com.gueg.mario;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.gueg.mario.entities.AnimatedObject;
import com.gueg.mario.entities.Enemy;
import com.gueg.mario.entities.GameObject;
import com.gueg.mario.entities.UnanimatedObject;

import java.util.HashMap;

import static com.gueg.mario.entities.GameObject.LEFT;
import static com.gueg.mario.entities.GameObject.RIGHT;

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

        HashMap<Integer, Integer[]> sprites = new HashMap<>();
        sprites.put(
                LEFT,
                new Integer[]{
                        R.drawable.goomba_2,
                        R.drawable.goomba_3
                });
        sprites.put(
                RIGHT,
                new Integer[]{
                        R.drawable.goomba_0,
                        R.drawable.goomba_1
                });
        Enemy goomba = new Enemy(res,
                Enemy.DEFAULT_SPEED,
                true,
                sprites);
        _spawners.put(Tiles.GOOMBA.getVal(), goomba);

        sprites.clear();
        sprites.put(
                LEFT,
                new Integer[]{
                        R.drawable.billball_1
                });
        sprites.put(
                RIGHT,
                new Integer[]{
                        R.drawable.billball_0,
                });

        Enemy billball = new Enemy(res,
                Enemy.BILLBALL_SPEED,
                false,
                sprites);
        _spawners.put(Tiles.BILL_BALL.getVal(), billball);
    }

    public GameObject at(int i) {
        return _spawners.get(i);
    }
}
