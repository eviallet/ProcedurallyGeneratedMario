package com.gueg.mario;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.gueg.mario.entities.AnimatedObject;
import com.gueg.mario.entities.Enemy;
import com.gueg.mario.entities.GameObject;
import com.gueg.mario.entities.GameObjectFactory;
import com.gueg.mario.entities.UnanimatedObject;

import java.util.HashMap;

import static com.gueg.mario.entities.GameObject.LEFT;
import static com.gueg.mario.entities.GameObject.RIGHT;

public class Spawners {

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, GameObject> _spawners = new HashMap<>();


    public Spawners(Resources res) {

        // GROUND

        GameObject ground = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.ground_0)
                .setSolid(true)
                .build();
        _spawners.put(Tiles.UP.getVal(), ground);

        // PROPS

        // ...

        // BLOCKS

        GameObject block = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.block_0)
                .setSolid(true)
                .build();
        _spawners.put(Tiles.BRICK.getVal(), block);


        GameObject qblock = new GameObjectFactory<>(AnimatedObject.class, res)
                .setSolid(true)
                .setSprites(new Integer[] {
                        R.drawable.qblock_0,
                        R.drawable.qblock_1,
                        R.drawable.qblock_2,
                        R.drawable.qblock_3})
                .build();
        _spawners.put(Tiles.QUESTION.getVal(), qblock);

        // OBJECTS

        // ...

        // TERRAINS

        // ...

        // ENEMIES

        GameObject goomba = new GameObjectFactory<>(Enemy.class, res)
                .setSpeed(Enemy.DEFAULT_SPEED)
                .setGravity(true)
                .putSprites(LEFT, new Integer[] {
                        R.drawable.goomba_2,
                        R.drawable.goomba_3})
                .putSprites(RIGHT, new Integer[] {
                        R.drawable.goomba_0,
                        R.drawable.goomba_1})
                .build();
        _spawners.put(Tiles.GOOMBA.getVal(), goomba);


        GameObject billball = new GameObjectFactory<>(Enemy.class, res)
                .setSpeed(Enemy.BILLBALL_SPEED)
                .putSprites(LEFT, new Integer[] {R.drawable.billball_1})
                .putSprites(RIGHT,new Integer[] {R.drawable.billball_0})
                .build();
        _spawners.put(Tiles.BILL_BALL.getVal(), billball);
    }

    public GameObject at(int i) {
        return _spawners.get(i);
    }
}
