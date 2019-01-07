package com.gueg.mario.components;

import com.gueg.mario.entities.Enemy;

public class GoombaBehavior implements BehaviorComponent.Behavior<Enemy> {

    @Override
    public void onNewFrame(Enemy goomba) {
        if(goomba.hasCollisionOccured()) {
            goomba.setVelocityX(-goomba.getVelocityX());
            goomba.clearCollisionFlag();
        }
    }

}
