package com.gueg.mario;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.gueg.mario.entities.AnimatedObject;
import com.gueg.mario.entities.Enemy;
import com.gueg.mario.entities.GameObject;
import com.gueg.mario.entities.GameObjectFactory;
import com.gueg.mario.entities.UnanimatedObject;

import java.util.HashMap;

import static com.gueg.mario.entities.GameObject.AT_TOP;
import static com.gueg.mario.entities.GameObject.LEFT;
import static com.gueg.mario.entities.GameObject.RIGHT;

public class Spawners {

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, GameObject> _spawners = new HashMap<>();


    public Spawners(Resources res) {

        // GROUND

        GameObject ground_up = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_up)
                .setSolid(true)
                .build();
        _spawners.put(Tiles.UP.getVal(), ground_up);
        GameObject ground_up_left = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_up_left)
                .setSolid(true)
                .build();
        _spawners.put(Tiles.UP_LEFT.getVal(), ground_up_left);
        GameObject ground_up_right = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_up_right)
                .setSolid(true)
                .build();
        _spawners.put(Tiles.UP_RIGHT.getVal(), ground_up_right);
        GameObject ground = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_gnd)
                .setSolid(true)
                .build();
        _spawners.put(Tiles.GND.getVal(), ground);


        GameObject ground_above_up = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_above_up)
                .setSolid(true)
                .setHitboxDir(AT_TOP)
                .setHitboxSpan(20)
                .build();
        _spawners.put(Tiles.PLATFORM.getVal(), ground_above_up);
        GameObject ground_above_left = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_above_left)
                .build();
        _spawners.put(Tiles.PLATFORM_LEFT.getVal(), ground_above_left);
        GameObject ground_above_right = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_above_right)
                .build();
        _spawners.put(Tiles.PLATFORM_RIGHT.getVal(), ground_above_right);
        GameObject ground_above_up_left = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_above_up_left)
                .setSolid(true)
                .setHitboxDir(AT_TOP)
                .setHitboxSpan(20)
                .build();
        _spawners.put(Tiles.PLATFORM_UP_LEFT.getVal(), ground_above_up_left);
        GameObject ground_above_up_right = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_above_up_right)
                .setSolid(true)
                .setHitboxDir(AT_TOP)
                .setHitboxSpan(20)
                .build();
        _spawners.put(Tiles.PLATFORM_UP_RIGHT.getVal(), ground_above_up_right);
        GameObject ground_above_gnd = new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.overworld_ground_gnd)
                .build();
        _spawners.put(Tiles.PLATFORM_GND.getVal(), ground_above_gnd);

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
