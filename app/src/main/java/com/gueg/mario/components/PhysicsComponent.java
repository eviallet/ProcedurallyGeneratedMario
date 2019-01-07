package com.gueg.mario.components;

import com.gueg.mario.CollisionDetector;
import com.gueg.mario.CollisionResolver;
import com.gueg.mario.entities.CollideableGameObject;
import com.gueg.mario.entities.GameObject;

import java.util.HashMap;

import static com.gueg.mario.entities.GameObject.AT_BOTTOM;
import static com.gueg.mario.entities.GameObject.AT_LEFT;
import static com.gueg.mario.entities.GameObject.AT_RIGHT;
import static com.gueg.mario.entities.GameObject.AT_TOP;
import static com.gueg.mario.entities.GameObject.LEFT;
import static com.gueg.mario.entities.GameObject.RIGHT;

public class PhysicsComponent extends Component {
    private static final int GRAVITY = 2;
    private static final int MAX_VELOCITY_Y = 20;

    private HashMap<Integer, GameObject> _objectsAround;


    public PhysicsComponent(GameObject obj) {
        super(obj);
    }

    @Override
    public void update() {
        if(_obj.isAffectedByGravity())
            applyGravity();

        if(_obj instanceof CollideableGameObject) {

            GameObject other;
            //Horizontal collisions
            if ((_obj.getVelocityDirection() == LEFT && ((other = _objectsAround.get(AT_LEFT)) != null)) ||
                    (_obj.getVelocityDirection() == RIGHT && ((other = _objectsAround.get(AT_RIGHT)) != null)))
                CollisionResolver.clipToHorizontalObject(_obj, other);
            else
                _obj.applyVelocityX();

            // Vertical collisions
            if ((_obj.getVelocityY() < 0 && ((other = _objectsAround.get(AT_TOP)) != null)) ||
                    (_obj.getVelocityY() > 0 && ((other = _objectsAround.get(AT_BOTTOM)) != null)))
                CollisionResolver.clipToVerticalObject(_obj, other);
            else
                _obj.applyVelocityY();
        }
    }

    public void setObjectsAround(HashMap<Integer, GameObject> objectsAround) {
        _objectsAround = objectsAround;
    }

    private void applyGravity() {
        int velocityY = _obj.getVelocityY();
        velocityY += GRAVITY;
        if(velocityY > MAX_VELOCITY_Y)
            velocityY = MAX_VELOCITY_Y;
        _obj.setVelocityY(velocityY);
    }
}
