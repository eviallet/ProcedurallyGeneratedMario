package com.gueg.mario.components;

import android.graphics.Rect;

import com.gueg.mario.entities.Mario;

import static com.gueg.mario.entities.GameObject.LEFT;
import static com.gueg.mario.entities.GameObject.X;

public class MarioBehavior implements BehaviorComponent.Behavior<Mario> {

    private static double SCROLL_LEFT_POS;
    private static double SCROLL_RIGHT_POS;

    private MarioEvents _listener;

    public MarioBehavior(MarioEvents listener, Rect screenRect) {
        _listener = listener;

        SCROLL_LEFT_POS = screenRect.width()*0.2;
        SCROLL_RIGHT_POS = screenRect.width()*0.4;
    }

    @Override
    public void onNewFrame(Mario mario) {

        // SCROLLING
        if (mario.getPos().right > SCROLL_RIGHT_POS) {
            mario.setXPos(((int) SCROLL_RIGHT_POS - mario.getSize()[X]));
            _listener.onCameraMoved(mario.getPos(), mario.getVelocityX());
        } else if (mario.getPos().left < SCROLL_LEFT_POS - 1) {
            mario.setXPos((int) SCROLL_LEFT_POS);
            _listener.onCameraMoved(mario.getPos(), mario.getVelocityX());
        }

        updateState(mario);
    }



    private void updateState(Mario mario) {
        if (mario.isJumping())
            mario.setState(mario.getVelocityDirection() == LEFT ? Mario.State.JUMP_L : Mario.State.JUMP_R);
        else if(mario.isFalling())
            mario.setState(mario.getVelocityDirection() == LEFT ? Mario.State.FALL_L : Mario.State.FALL_R);
        else if(mario.getVelocityX() == 0) // idle
            mario.setState(mario.getDirection() == LEFT ? Mario.State.IDLE_L : Mario.State.IDLE_R);
        else if(mario.isWalking())
            mario.setState(mario.getVelocityDirection() == LEFT ? Mario.State.WALK_L : Mario.State.WALK_R);
        else // Math.abs(mario.getVelocityX()) > MAX_SPEED_X_WALKING) // running
            mario.setState(mario.getVelocityDirection() == LEFT ? Mario.State.RUN_L : Mario.State.RUN_R);
    }

    public MarioEvents getListener() {
        return _listener;
    }

    public interface MarioEvents {
        void onCameraMoved(Rect pos, int speed);
    }
}
