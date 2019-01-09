package com.gueg.mario;

import com.gueg.mario.entities.GameObject;


import static com.gueg.mario.entities.GameObject.LEFT;
import static com.gueg.mario.entities.GameObject.X;
import static com.gueg.mario.entities.GameObject.Y;

public class CollisionResolver {


    public static void clipToHorizontalObject(GameObject obj, GameObject other) {
        if(obj.getVelocityDirection() == LEFT)
            obj.setXPos(other.getPos().right);
        else
            obj.setXPos(other.getPos().left - obj.getSize()[X]);
    }

    public static void clipToVerticalObject(GameObject obj, GameObject other) {
        if(obj.getVelocityY() > 0)  // falling
            obj.setYPos(other.getPos().top - obj.getSize()[Y]);
        else                        // jumping
            obj.setYPos(other.getPos().bottom);
    }

    public interface CollisionResolverListener {
        void onCollisionXOccured();
        void onCollisionYOccured();
    }
}
