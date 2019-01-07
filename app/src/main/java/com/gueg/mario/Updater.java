package com.gueg.mario;

public class Updater {

/*


    protected Mario mario;
    protected ArrayList<GameObject> objects;
    protected ArrayList<Enemy> enemies;

    public Updater(Mario mario, ArrayList<GameObject> objects, ArrayList<Enemy> enemies) {
        this.mario = mario;
        this.objects = objects;
        this.enemies = enemies;
    }


    public void loadScrollPos(Rect screenRect) {
    }

    public void update() {

        class UpdateRunnable implements Runnable {

            @Override
            public void run() {


                // MARIO



                boolean applyGravity = true;
                GameObject top;
                GameObject left = null;
                GameObject right = null;
                GameObject bottom = null;
                boolean canMove = true;


                HashMap<Integer, GameObject> map = GameObject.getObjectsAround(objects, enemies,  mario);
                if (map != null) {
                    if ((top = map.get(AT_TOP)) != null) {
                        if (top instanceof Enemy)
                            mario.getVelocityDirection(); // TODO life
                        else if (mario.getVelocity() < 0) {
                            mario.setVelocity(0); // TODO top.triggerAction();
                            mario.jumping = false;
                        }
                    }
                    if ((left = map.get(AT_LEFT)) != null || (right = map.get(AT_RIGHT)) != null)
                        if ((left != null && left instanceof Enemy) || (right != null && right instanceof Enemy))
                            mario.getVelocityDirection(); // TODO life
                        else
                            canMove = false;
                    if ((bottom = map.get(AT_BOTTOM)) != null) {
                        if (bottom instanceof Enemy && mario.getVelocity() >= 0) {
                            mario.jumping = true;
                            mario.setVelocity(-12); // TODO kill ennemy
                        } else
                            applyGravity = false;
                    }

                }


                if (applyGravity) {
                    mario.moveY();
                } else {
                    mario.setPos(mario.getPos().left, bottom.getPos().top - mario.getSize()[1]);
                    mario.setVelocity(0);
                }

                if (canMove)
                    mario.moveX();
                else {
                    if (left != null) {
                        mario.setPos(left.getPos().right, mario.getPos().top);
                        mario.slowDownQuickly();
                    }
                    if (right != null) {
                        mario.setPos(right.getPos().left - mario.getSize()[0], mario.getPos().top);
                        mario.slowDownQuickly();
                    }
                }

                // DEBUG
                if (mario.getPos().top > screenRect.bottom)
                    mario.setPos(mario.getPos().left, screenRect.height() - GameObject.BASE_HEIGHT * 9);


                // SCROLLING

                    bkg.r1.offset(-mario.getSpeed() / 6, 0);
                    bkg.r2.offset(-mario.getSpeed() / 6, 0);
                    bkg.r3.offset(-mario.getSpeed() / 6, 0);
                    for (GameObject obj : objects)
                        obj.setOffset(mario.getVelocityDirection(), mario.getSpeed());
                    for (Enemy en : enemies)
                        en.setOffset(mario.getVelocityDirection(), mario.getSpeed());
                }



            }
        }
        Thread t = new Thread(new UpdateRunnable());
        t.start();
    }

*/

}
