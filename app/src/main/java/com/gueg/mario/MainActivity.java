package com.gueg.mario;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import com.twicecircled.spritebatcher.Drawer;
import com.twicecircled.spritebatcher.SpriteBatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;


@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity implements Drawer {


    private static final int MAX_FPS = 45;

    private long animTicker = System.currentTimeMillis();
    private static final long FRAME_DURATION = 50;
    private long animTicker2 = System.currentTimeMillis();
    private static final long FRAME_DURATION_2 = 200;

    private double SCROLL_LEFT_POS;
    private double SCROLL_RIGHT_POS;

    private Rect r1;
    private Rect r2;
    private Rect r3;


    boolean moving = false;
    boolean running =false;
    float pos;
    boolean direction = true;
    boolean jumping = false;
    private static final float JUMP = -30.0f;

    private List<GameObject> objects;
    private List<Ennemy> ennemies;
    private UnanimatedObject background1;
    private UnanimatedObject background2;
    private UnanimatedObject background3;
    private Mario mario;

    private int primaryPointerId;
    private int runningPointerId;
    private int jumpingPointerId;


    // TOUCH EVENTS
    private View.OnTouchListener OnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getPointerCount()==1) {
                primaryPointerId = event.getPointerId(event.getActionIndex());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        moving = false;
                        running = false;
                        if (jumping && mario.getVelocity() < JUMP / 2) {
                            mario.setVelocity(JUMP / 2);
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        if (event.getY() > getScreenRect().centerY()) {
                            moving = true;
                            pos = event.getX();
                        } else {
                            if (!mario.isFalling() && !jumping) {
                                jumping = true;
                                mario.setVelocity(JUMP);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getY() < getScreenRect().centerY()) {
                            if (!mario.isFalling() && !jumping) {
                                jumping = true;
                                mario.setVelocity(JUMP);
                            }
                        } else {
                            if (jumping && mario.getVelocity() < JUMP / 2) {
                                mario.setVelocity(JUMP / 2);
                            }
                        }
                        break;
                }
            }
            else {
                MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
                event.getPointerCoords(event.getActionIndex(),coords);
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (coords.getAxisValue(1) > getScreenRect().centerY()) {
                            if(!moving) {
                                moving = true;
                                primaryPointerId = event.getPointerId(event.getActionIndex());
                            } else {
                                running = true;
                                runningPointerId = event.getPointerId(event.getActionIndex());
                            }
                        } else {
                            if (!mario.isFalling() && !jumping) {
                                jumping = true;
                                jumpingPointerId = event.getPointerId(event.getActionIndex());
                                mario.setVelocity(JUMP);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if(event.getPointerId(event.getActionIndex())==primaryPointerId)
                            moving = false;
                        else if (jumping && mario.getVelocity() < JUMP / 2 && jumpingPointerId==event.getPointerId(event.getActionIndex()))
                            mario.setVelocity(JUMP / 2);
                        else if(running && runningPointerId == event.getPointerId(event.getActionIndex()))
                            running = false;
                        break;
                }
            }

            return true;
        }
    };



    // MAIN LOOP
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            // TICKER
            if (System.currentTimeMillis() - animTicker >= FRAME_DURATION) {
                animTicker = System.currentTimeMillis();
                mario.nextFrame();
                for(GameObject obj : objects)
                    if(obj instanceof AnimatedObject)
                        ((AnimatedObject) obj).nextFrame();
            }
            if (System.currentTimeMillis() - animTicker2 >= FRAME_DURATION_2) {
                animTicker2 = System.currentTimeMillis();
                for(Ennemy en : ennemies)
                    en.nextFrame();
            }

            // GRAVITY
            for(GameObject obj : objects) {
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




            for(Ennemy en : ennemies) {
                if (en.getGravityState()) {
                    en.applyGravity();

                    boolean applyGravity = true;
                    GameObject left = null;
                    GameObject right = null;
                    GameObject bottom = null;
                    boolean canMove = true;


                    HashMap<Integer,GameObject> map = getObjectsAround(en);
                    if(map!=null) {
                        if((left = map.get(AT_LEFT))!=null || (right = map.get(AT_RIGHT))!=null)
                            canMove = false;
                        if((bottom = map.get(AT_BOTTOM))!=null)
                            applyGravity = false;
                    }


                    if(applyGravity) {
                        en.moveY();
                    } else {
                        en.setPos(en.getPos().left,bottom.getPos().top-en.getSize()[1]);
                        en.setVelocity(0);
                    }

                    if(canMove)
                        en.moveX();
                    else {
                        if(left!=null) {
                            en.setPos(left.getPos().right, en.getPos().top);
                        }
                        if(right!=null) {
                            en.setPos(right.getPos().left-en.getSize()[0], en.getPos().top);
                        }

                        en.changeDirection();
                        en.nextFrame();
                    }

                } else  // billball
                    en.moveX(); // TODO flying koopas
            }



            // MARIO

            if(moving)
                moving(pos);
            else
                mario.slowDown();

            if(jumping&&mario.getVelocity()>0)
                jumping = false;

            mario.applyGravity();

            boolean applyGravity = true;
            GameObject top;
            GameObject left = null;
            GameObject right = null;
            GameObject bottom = null;
            boolean canMove = true;


            HashMap<Integer,GameObject> map = getObjectsAround(mario);
            if(map!=null) {
                if((top = map.get(AT_TOP))!=null) {
                    if (top instanceof Ennemy)
                        mario.getDirection(); // TODO life
                    else if (mario.getVelocity() < 0) {
                        mario.setVelocity(0); // TODO top.triggerAction();
                        jumping = false;
                    }
                }
                if((left = map.get(AT_LEFT))!=null || (right = map.get(AT_RIGHT))!=null)
                    if( (left != null && left instanceof Ennemy) || (right != null && right instanceof Ennemy))
                        mario.getDirection(); // TODO life
                    else
                        canMove = false;
                if((bottom = map.get(AT_BOTTOM))!=null) {
                    if(bottom instanceof Ennemy && mario.getVelocity() >=0) {
                        jumping = true;
                        mario.setVelocity(-12); // TODO kill ennemy
                    }
                    else
                        applyGravity = false;
                }

            }


            if(applyGravity) {
                mario.moveY();
            } else {
                mario.setPos(mario.getPos().left,bottom.getPos().top-mario.getSize()[1]);
                mario.setVelocity(0);
            }

            if(canMove)
                mario.moveX();
            else {
                if(left!=null) {
                    mario.setPos(left.getPos().right, mario.getPos().top);
                    mario.slowDownQuickly();
                }
                if(right!=null) {
                    mario.setPos(right.getPos().left - mario.getSize()[0], mario.getPos().top);
                    mario.slowDownQuickly();
                }
            }

            // DEBUG
            if(mario.getPos().top > getScreenRect().bottom)
                mario.setPos(mario.getPos().left, getScreenRect().height()-GameObject.BASE_HEIGHT*9);






            // SCROLLING
            if(mario.getPos().right > SCROLL_RIGHT_POS) {
                mario.setPos(((int) SCROLL_RIGHT_POS - GameObject.BASE_WIDTH), mario.getPos().top);
                r1.offset(-mario.getSpeed()/6,0);
                r2.offset(-mario.getSpeed()/6,0);
                r3.offset(-mario.getSpeed()/6,0);
                for (GameObject obj : objects)
                    obj.setOffset(mario.getDirection(), mario.getSpeed());
                for(Ennemy en : ennemies)
                    en.setOffset(mario.getDirection(), mario.getSpeed());
            }
            else if(mario.getPos().left < SCROLL_LEFT_POS -1) {
                mario.setPos((int) SCROLL_LEFT_POS, mario.getPos().top);
                r1.offset(-mario.getSpeed()/6,0);
                r2.offset(-mario.getSpeed()/6,0);
                r3.offset(-mario.getSpeed()/6,0);
                for (GameObject obj : objects)
                    obj.setOffset(mario.getDirection(), -mario.getSpeed());
                for(Ennemy en : ennemies)
                    en.setOffset(mario.getDirection(), -mario.getSpeed());

            }

            if(r1.right<=getScreenRect().left) {
                r1 = getScreenRect();
                r2 = getScreenRect();
                r2.offset(getScreenRect().width(),0);
                r3 = getScreenRect();
                r3.offset(-getScreenRect().width(),0);
            } else if(r1.left>=getScreenRect().right) {
                r1 = getScreenRect();
                r2 = getScreenRect();
                r2.offset(getScreenRect().width(),0);
                r3 = getScreenRect();
                r3.offset(-getScreenRect().width(),0);
            }

        }

    };









    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView surface = new GLSurfaceView(this);
        setContentView(surface);
        initFullScreen();

        surface.setOnTouchListener(OnTouchListener);

        SCROLL_LEFT_POS = getScreenRect().width()*0.2;
        SCROLL_RIGHT_POS = getScreenRect().width()*0.4;


        Resources res = getResources();

        // BACKGROUND POS

        r1 = getScreenRect();
        r2 = getScreenRect();
        r2.offset(getScreenRect().width(),0);
        r3 = getScreenRect();
        r3.offset(-getScreenRect().width(),0);

        background1 = new UnanimatedObject(res,R.drawable.bkg_0,false);
        background1.setPos(r1.left,r1.top);
        background2 = new UnanimatedObject(res,R.drawable.bkg_0,false);
        background2.setPos(r2.left,r2.top);
        background3 = new UnanimatedObject(res,R.drawable.bkg_0,false);
        background3.setPos(r3.left,r3.top);

        // OBJECTS

        objects = new ArrayList<>();
        objects.add(new UnanimatedObject(res,R.drawable.block_0,true,500,600));
        objects.add(new UnanimatedObject(res,R.drawable.block_0,true,(int) SCROLL_LEFT_POS + 3*GameObject.BASE_WIDTH , getScreenRect().height()-GameObject.BASE_HEIGHT*2));
        objects.add(new UnanimatedObject(res,R.drawable.block_0,true,(int) SCROLL_LEFT_POS + 6*GameObject.BASE_WIDTH , getScreenRect().height()-GameObject.BASE_HEIGHT*4));


        // ENNEMIES
        // Default ennemies
        Ennemy goomba = new Ennemy(res, new int[]{
                R.drawable.goomba_0,
                R.drawable.goomba_1,
                R.drawable.goomba_2,
                R.drawable.goomba_3},
                Ennemy.DEFAULT_SPEED,
                true);
        goomba.setPos(getScreenRect().right-GameObject.BASE_WIDTH*2,getScreenRect().height()-GameObject.BASE_HEIGHT*2);

        Ennemy billball = new Ennemy(res, new int[]{
                R.drawable.billball_0,
                R.drawable.billball_1},
                Ennemy.BILLBALL_SPEED,
                false);
        billball.setPos(getScreenRect().right+GameObject.BASE_WIDTH,getScreenRect().height()-GameObject.BASE_HEIGHT*8);

        ennemies = new ArrayList<>();
        ennemies.add(goomba);
        ennemies.add(billball);

        // GROUND

        for(int i =0; i<getScreenRect().width(); i+=GameObject.BASE_WIDTH) {
            objects.add(new UnanimatedObject(res,R.drawable.ground_0,true,i,getScreenRect().height()-GameObject.BASE_HEIGHT));
        }


        // MARIO

        mario = new Mario(res,new int[] {
                R.drawable.walking_0,
                R.drawable.walking_1,
                R.drawable.jumping_0,
                R.drawable.falling_0,
                R.drawable.running_0,
                R.drawable.running_1,
                // ^ left
                R.drawable.walking_2,
                R.drawable.walking_3,
                R.drawable.jumping_1,
                R.drawable.falling_1,
                R.drawable.running_2,
                R.drawable.running_3,
                // ^ right
        });
        mario.setPos((int) SCROLL_LEFT_POS + GameObject.BASE_WIDTH , getScreenRect().height()-GameObject.BASE_HEIGHT*2);


        // SPRITE BATCHER

        // arrière plan
        // ...
        // premier plan
        int[] resourcesIds = new int[] {
                R.drawable.bkg_0,
                R.drawable.ground_0,
                R.drawable.goomba_0,
                R.drawable.goomba_1,
                R.drawable.goomba_2,
                R.drawable.goomba_3,
                R.drawable.walking_0,
                R.drawable.walking_1,
                R.drawable.walking_2,
                R.drawable.walking_3,
                R.drawable.jumping_0,
                R.drawable.jumping_1,
                R.drawable.falling_0,
                R.drawable.falling_1,
                R.drawable.running_0,
                R.drawable.running_1,
                R.drawable.running_2,
                R.drawable.running_3,
                R.drawable.block_0,
                R.drawable.billball_0,
                R.drawable.billball_1,
                R.string.font
        };

        SpriteBatcher sb = new SpriteBatcher(this, resourcesIds, this);
        sb.setMaxFPS(MAX_FPS);
        surface.setRenderer(sb);

        handler.post(runnable);
    }

    FPSCounter fps = new FPSCounter();

    @Override
    public void onDrawFrame(GL10 gl, SpriteBatcher sb) {

        // CONSTANTS
        sb.drawText(R.string.font,"FPS : ",300,40,1f);
        sb.drawText(R.string.font,fps.logFrame(),350,40,1f);



        // DRAW
        sb.draw(mario);
        sb.draw(background1, r1);
        sb.draw(background2, r2);
        sb.draw(background3, r3);

        // TODO if sort de l'ecran
        for(GameObject obj : objects)
            sb.draw(obj);
        for(Ennemy en : ennemies)
            sb.draw(en);


        handler.post(runnable);
    }



    private void moving(float x) {
        direction=(x>=getScreenRect().width()/2);

        if (direction)
            mario.moveRight(running);
        else
            mario.moveLeft(running);
    }







    private class FPSCounter {
        long startTime = System.nanoTime();
        int frames = 0;
        int bkp = 0;

        public String logFrame() {
            frames++;
            if(System.nanoTime() - startTime >= 1000000000) {
                bkp = frames;
                frames = 0;
                startTime = System.nanoTime();
            }
            return Integer.toString(bkp);
        }
    }

    private Rect getScreenRect() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int navBarSize = 0;
        if (resourceId > 0) {
            navBarSize = getResources().getDimensionPixelSize(resourceId) + 10;
        }
        return new Rect(0,0,metrics.widthPixels+navBarSize,metrics.heightPixels);
    }


    private static final int AT_TOP = 0;
    private static final int AT_LEFT = 1;
    private static final int AT_RIGHT = 2;
    private static final int AT_BOTTOM = 3;

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer,GameObject> getObjectsAround(GameObject o) {
         HashMap<Integer,GameObject> map = new HashMap<>();
        for(GameObject obj : objects) {
            if (o.isOnTopOf(obj))
                map.put(AT_BOTTOM, obj);
            else if (o.isAtLeftOf(obj))
                map.put(AT_RIGHT, obj);
            else if (o.isAtRightOf(obj))
                map.put(AT_LEFT, obj);
            else if (o.isBelow(obj))
                map.put(AT_TOP, obj);
        }

        for(Ennemy en : ennemies) {
            if (o.isOnTopOf(en))
                map.put(AT_BOTTOM, en);
            else if (o.isAtLeftOf(en))
                map.put(AT_RIGHT, en);
            else if (o.isAtRightOf(en))
                map.put(AT_LEFT, en);
            else if (o.isBelow(en))
                map.put(AT_TOP, en);
        }

        return map;
    }





    private void initFullScreen() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(uiOptions);
            }
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }



}