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

import com.twicecircled.spritebatcher.Drawer;
import com.twicecircled.spritebatcher.SpriteBatcher;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

// TODO remove all that static stuff
@SuppressWarnings({"ConstantConditions", "FieldCanBeLocal"})
public class MainActivity extends AppCompatActivity implements Drawer {


    private static final int MAX_FPS = 60;

    private final int MAP_WIDTH = 130;
    private final int MAP_HEIGHT = 10;


    private Spawners _spawner;

    static Rect screenRect;

    static ArrayList<GameObject> objects = new ArrayList<>();
    static ArrayList<Enemy> enemies = new ArrayList<>();
    static Mario mario;
    static Backgrounds bkg;

    FPSCounter fps = new FPSCounter();

    Updater updater;




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

        loadMap();


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
        mario.setPos(GameObject.BASE_WIDTH * 3 , screenRect.height()-GameObject.BASE_HEIGHT*2);

        bkg = new Backgrounds(res);

        updater = new Updater();

        surface.setOnTouchListener(new InputProcessor());

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

        updater.update();
    }



    private void loadMap() {
        int map [][] = Generator.generateMap(MAP_WIDTH, MAP_HEIGHT, 0);
        for (int h = 0; h < MAP_HEIGHT; h++) {
            for (int w = 0; w < MAP_WIDTH; w++) {
                int t = map[h][w];
                if (t != Tiles.NONE.getVal()) {
                    GameObject spawner = _spawner.at(t);
                    if(spawner != null) {
                        if (spawner instanceof Enemy)
                            enemies.add((Enemy)spawner.spawnAtPos(GameObject.BASE_WIDTH * w, GameObject.BASE_HEIGHT * h));
                        else
                            objects.add(spawner.spawnAtPos(GameObject.BASE_WIDTH * w, GameObject.BASE_HEIGHT * h));
                    }
                }
            }
        }
    }



    @Override
    public void onDrawFrame(GL10 gl, SpriteBatcher sb) {
        // CONSTANTS
        sb.drawText(R.string.font,"FPS : "+fps.logFrame(),300,40,1f);

        // DRAW
        sb.draw(mario);

        // TODO if sort de l'ecran
        /*
        for(GameObject obj : objects)
            sb.draw(obj);
        for(Enemy en : enemies)
            sb.draw(en);*/
        for(int i=0; i<objects.size()/10; i++)
            sb.draw(objects.get(i));

        sb.draw(bkg.background1, bkg.r1);
        sb.draw(bkg.background2, bkg.r2);
        sb.draw(bkg.background3, bkg.r3);

        updater.update();
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