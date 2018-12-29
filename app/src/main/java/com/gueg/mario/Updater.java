package com.gueg.mario;

import java.util.HashMap;

import static com.gueg.mario.GameObject.AT_BOTTOM;
import static com.gueg.mario.GameObject.AT_LEFT;
import static com.gueg.mario.GameObject.AT_RIGHT;
import static com.gueg.mario.GameObject.AT_TOP;
import static com.gueg.mario.MainActivity.bkg;
import static com.gueg.mario.MainActivity.enemies;
import static com.gueg.mario.MainActivity.mario;
import static com.gueg.mario.MainActivity.objects;
import static com.gueg.mario.MainActivity.screenRect;

public class Updater {

    private double SCROLL_LEFT_POS;
    private double SCROLL_RIGHT_POS;

    private long animTicker = System.currentTimeMillis();
    private static final long FRAME_DURATION = 50;
    private long animTicker2 = System.currentTimeMillis();
    private static final long FRAME_DURATION_2 = 200;


    Updater() {
        SCROLL_LEFT_POS = screenRect.width()*0.2;
        SCROLL_RIGHT_POS = screenRect.width()*0.4;
    }

    public void update() {

        class UpdateRunnable implements Runnable {

            @Override
            public void run() {

                // TICKER
                if (System.currentTimeMillis() - animTicker >= FRAME_DURATION) {
                    animTicker = System.currentTimeMillis();
                    mario.nextFrame();
                    for (GameObject obj : objects)
                        if (obj instanceof AnimatedObject)
                            ((AnimatedObject) obj).nextFrame();
                }
                if (System.currentTimeMillis() - animTicker2 >= FRAME_DURATION_2) {
                    animTicker2 = System.currentTimeMillis();
                    for (Enemy en : enemies)
                        en.nextFrame();
                }

                // GRAVITY
                for (GameObject obj : objects) {
                    if (obj.getGravityState()) {
                        obj.applyGravity();
                        for (GameObject obj2 : objects) {
                            if (obj.isOnTopOf(obj2)) {
                                obj.setPos(obj.getPos().left, obj2.getPos().top - obj.getSize()[0]);
                                obj.setVelocity(0);
                            }
                        }
                    }
                }


                for (Enemy en : enemies) {
                    if (en.getGravityState()) {
                        en.applyGravity();

                        boolean applyGravity = true;
                        GameObject left = null;
                        GameObject right = null;
                        GameObject bottom = null;
                        boolean canMove = true;


                        HashMap<Integer, GameObject> map = GameObject.getObjectsAround(objects, enemies, en);
                        if (map != null) {
                            if ((left = map.get(AT_LEFT)) != null || (right = map.get(AT_RIGHT)) != null)
                                canMove = false;
                            if ((bottom = map.get(AT_BOTTOM)) != null)
                                applyGravity = false;
                        }


                        if (applyGravity) {
                            en.moveY();
                        } else {
                            en.setPos(en.getPos().left, bottom.getPos().top - en.getSize()[1]);
                            en.setVelocity(0);
                        }

                        if (canMove)
                            en.moveX();
                        else {
                            if (left != null) {
                                en.setPos(left.getPos().right, en.getPos().top);
                            }
                            if (right != null) {
                                en.setPos(right.getPos().left - en.getSize()[0], en.getPos().top);
                            }

                            en.changeDirection();
                            en.nextFrame();
                        }

                    } else  // billball
                        en.moveX(); // TODO flying koopas
                }


                // MARIO

                if (mario.moving)
                    mario.move(screenRect);
                else
                    mario.slowDown();

                if (mario.jumping && mario.getVelocity() > 0)
                    mario.jumping = false;

                mario.applyGravity();

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
                            mario.getDirection(); // TODO life
                        else if (mario.getVelocity() < 0) {
                            mario.setVelocity(0); // TODO top.triggerAction();
                            mario.jumping = false;
                        }
                    }
                    if ((left = map.get(AT_LEFT)) != null || (right = map.get(AT_RIGHT)) != null)
                        if ((left != null && left instanceof Enemy) || (right != null && right instanceof Enemy))
                            mario.getDirection(); // TODO life
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
                if (mario.getPos().right > SCROLL_RIGHT_POS) {
                    mario.setPos(((int) SCROLL_RIGHT_POS - GameObject.BASE_WIDTH), mario.getPos().top);
                    bkg.r1.offset(-mario.getSpeed() / 6, 0);
                    bkg.r2.offset(-mario.getSpeed() / 6, 0);
                    bkg.r3.offset(-mario.getSpeed() / 6, 0);
                    for (GameObject obj : objects)
                        obj.setOffset(mario.getDirection(), mario.getSpeed());
                    for (Enemy en : enemies)
                        en.setOffset(mario.getDirection(), mario.getSpeed());
                } else if (mario.getPos().left < SCROLL_LEFT_POS - 1) {
                    mario.setPos((int) SCROLL_LEFT_POS, mario.getPos().top);
                    bkg.r1.offset(-mario.getSpeed() / 6, 0);
                    bkg.r2.offset(-mario.getSpeed() / 6, 0);
                    bkg.r3.offset(-mario.getSpeed() / 6, 0);
                    for (GameObject obj : objects)
                        obj.setOffset(mario.getDirection(), -mario.getSpeed());
                    for (Enemy en : enemies)
                        en.setOffset(mario.getDirection(), -mario.getSpeed());

                }

                if (bkg.r1.right <= screenRect.left) {
                    bkg.r1 = screenRect;
                    bkg.r2 = screenRect;
                    bkg.r2.offset(screenRect.width(), 0);
                    bkg.r3 = screenRect;
                    bkg.r3.offset(-screenRect.width(), 0);
                } else if (bkg.r1.left >= screenRect.right) {
                    bkg.r1 = screenRect;
                    bkg.r2 = screenRect;
                    bkg.r2.offset(screenRect.width(), 0);
                    bkg.r3 = screenRect;
                    bkg.r3.offset(-screenRect.width(), 0);
                }

            }
        }
        Thread t = new Thread(new UpdateRunnable());
        t.start();
    }



}
