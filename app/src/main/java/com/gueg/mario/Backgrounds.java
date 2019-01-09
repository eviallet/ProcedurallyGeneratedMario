package com.gueg.mario;

import android.content.res.Resources;
import android.graphics.Rect;

import com.gueg.mario.entities.GameObjectFactory;
import com.gueg.mario.entities.UnanimatedObject;

public class Backgrounds {

    private Rect screenRect;

    UnanimatedObject background1;
    UnanimatedObject background2;
    UnanimatedObject background3;

    /*
        ____________                 ____________
         R1 |R2| R3      --->         R1 R|2 R|3
        ------------    Scrolling    ------------
             ^
           Camera
     */

    Backgrounds(Resources res, Rect screenRect) {
        this.screenRect = screenRect;

        UnanimatedObject background = (UnanimatedObject)new GameObjectFactory<>(UnanimatedObject.class, res)
                .setSprite(R.drawable.bkg_0)
                .build();
        background.setSizePx(screenRect.width(), screenRect.height());

        background1 = background.clone();
        background2 = (UnanimatedObject)background.spawnAtPos(screenRect.width(), 0);
        background3 = (UnanimatedObject)background.spawnAtPos(-screenRect.width(), 0);
    }

    public void offset(int x) {

        background1.setOffset(-x / 6);
        background2.setOffset(-x / 6);
        background3.setOffset(-x / 6);

        // Roll the backgrounds
        if (background1.getPos().right <= screenRect.left ||
                background1.getPos().left >= screenRect.right) {
            background1.setXPos(0);
            background2.setXPos(screenRect.width());
            background2.setXPos(-screenRect.width());
        }
    }


}
