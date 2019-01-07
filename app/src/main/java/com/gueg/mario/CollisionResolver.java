package com.gueg.mario;

import com.gueg.mario.entities.GameObject;


import static com.gueg.mario.entities.GameObject.LEFT;
import static com.gueg.mario.entities.GameObject.X;
import static com.gueg.mario.entities.GameObject.Y;

public class CollisionResolver {


    public static void clipToHorizontalObject(GameObject obj, GameObject other) {
        if(obj.getVelocityDirection() == LEFT)
            obj.setPos(other.getPos().right, obj.getPos().top);
        else
            obj.setPos(other.getPos().left - obj.getSize()[X], obj.getPos().top);
    }

    public static void clipToVerticalObject(GameObject obj, GameObject other) {
        if(obj.getVelocityY() > 0)  // falling
            obj.setPos(obj.getPos().left, other.getPos().top - obj.getSize()[Y]);
        else                        // jumping
            obj.setPos(obj.getPos().left, other.getPos().bottom);
    }
}
