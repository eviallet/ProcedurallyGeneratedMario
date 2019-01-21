package com.gueg.mario;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.gueg.mario.components.MarioBehavior;
import com.gueg.mario.entities.GameObject;
import com.gueg.mario.entities.Mario;
import com.gueg.mario.input.InputProcessor;
import com.twicecircled.spritebatcher.Drawer;
import com.twicecircled.spritebatcher.SpriteBatcher;

import javax.microedition.khronos.opengles.GL10;

@SuppressWarnings({"ConstantConditions", "FieldCanBeLocal"})
public class MainActivity extends AppCompatActivity implements Drawer {


    private static final int MAX_FPS = 60;

    private final int MAP_WIDTH = 130;
    private final int MAP_HEIGHT = 10;

    private Rect screenRect;
    private Rect visibleScreen = new Rect();

    private Mario _mario;
    private Spawners _spawner;
    private Backgrounds _bkg;
    private SynchronizedArrayList<GameObject> _objects;
    private CollisionDetector _collisions;

    private FPSCounter fps = new FPSCounter();



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView surface = new GLSurfaceView(this);
        setContentView(surface);
        initFullScreen();

        loadScreenRect();

        Resources res = getResources();

        _spawner = new Spawners(res);

        _bkg = new Backgrounds(res, screenRect);

        _objects = new SynchronizedArrayList<>();

        _mario = new Mario(res, new MarioBehavior.MarioEvents() {
            @Override
            public void onCameraMoved(Rect pos, int speed) {
                _bkg.offset(speed);
                for (int i = 1; i < _objects.size(); i++)
                    _objects.get(i).setOffset(-_mario.getVelocityX());
                for (int i = 1; i < _objects.getDisabledSize(); i++)
                    _objects.getDisabledAt(i).setOffset(-_mario.getVelocityX());
                _bkg.offset(-_mario.getVelocityX()/6);
            }
        }, screenRect);
        _mario.setGridPos(2, 1, screenRect);

        _objects.add(_mario);

        _collisions = new CollisionDetector(_objects);

        surface.setOnTouchListener(new InputProcessor(screenRect, _mario.getInputListener()));

        loadMap();

        // SPRITE BATCHER

        // arrière plan
        // ...
        // premier plan
        int[] resourcesIds = new int[] {
                R.drawable.bkg_0,
                // GROUND
                R.drawable.overworld_ground_gnd,
                R.drawable.overworld_ground_up,
                R.drawable.overworld_ground_up_left,
                R.drawable.overworld_ground_up_right,
                // PLATFORMS
                R.drawable.overworld_ground_above_up,
                R.drawable.overworld_ground_above_up_left,
                R.drawable.overworld_ground_above_up_right,
                R.drawable.overworld_ground_above_left,
                R.drawable.overworld_ground_above_right,
                // ENEMIES
                // - goomba
                R.drawable.goomba_0,
                R.drawable.goomba_1,
                R.drawable.goomba_2,
                R.drawable.goomba_3,
                // - billball
                R.drawable.billball_0,
                R.drawable.billball_1,
                // MARIO
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
                // BLOCKS
                R.drawable.block_0,
                R.drawable.qblock_0,
                R.drawable.qblock_1,
                R.drawable.qblock_2,
                R.drawable.qblock_3,
                // FONTS
                R.string.font
        };

        SpriteBatcher sb = new SpriteBatcher(this, resourcesIds, this);
        sb.setMaxFPS(MAX_FPS);
        surface.setRenderer(sb);
    }



    private void loadMap() {
        int map [][] = Generator.generateMap(MAP_WIDTH, MAP_HEIGHT, 0);
        for (int h = 0; h < MAP_HEIGHT; h++) {
            for (int w = 0; w < MAP_WIDTH; w++) {
                int t = map[h][w];
                if (t != Tiles.NONE.getVal()) {
                    GameObject spawner = _spawner.at(t);
                    if(spawner != null)
                        _objects.add(spawner.spawnAtGridPos(w, MAP_HEIGHT - h - 1, screenRect));
                }
            }
        }
    }



    @Override
    public void onDrawFrame(GL10 gl, SpriteBatcher sb) {
        _collisions.update();

        _mario.update();

        visibleScreen.left = _mario.getPos().left - screenRect.width();
        visibleScreen.right = _mario.getPos().right + screenRect.width();
        visibleScreen.top = _mario.getPos().top - screenRect.height();
        visibleScreen.bottom = _mario.getPos().bottom + screenRect.height();

        // ENABLE OR DISABLE OBJECTS WHETHER THEY ARE ON SCREEN
        for(int i = 1; i < _objects.size(); i++) {
            GameObject obj = _objects.get(i);
            if (!obj.isOnScreen(visibleScreen))
                _objects.disable(obj);
        }
        for(int i = 0; i < _objects.getDisabledSize(); i++) {
            GameObject obj = _objects.getDisabledAt(i);
            if (obj.isOnScreen(visibleScreen))
                _objects.enable(obj);

        }


        // UPDATE OBJECTS
        for(int i = 1; i < _objects.size(); i++)
            _objects.get(i).update();


        // DISPLAY FPS
        sb.drawText(R.string.font, "FPS : "+ fps.logFrame(), 300, 40, 1f);

        // DRAW
        for(GameObject obj : _objects)
            sb.draw(obj);

        // BACKGROUNDS
        sb.draw(_bkg.background1, _bkg.background1.getPos());
        sb.draw(_bkg.background2, _bkg.background2.getPos());
        sb.draw(_bkg.background3, _bkg.background3.getPos());
    }






    private class FPSCounter {
        long startTime = System.nanoTime();
        int frames = 0;
        int bkp = 0;

        String logFrame() {
            frames++;
            if(System.nanoTime() - startTime >= 1000000000) {
                bkp = frames;
                frames = 0;
                startTime = System.nanoTime();
            }
            return Integer.toString(bkp);
        }
    }

    private void loadScreenRect() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int navBarSize = 0;
        if (resourceId > 0) {
            navBarSize = getResources().getDimensionPixelSize(resourceId) + 10;
        }
        screenRect = new Rect(0,0,metrics.widthPixels+navBarSize,metrics.heightPixels);
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