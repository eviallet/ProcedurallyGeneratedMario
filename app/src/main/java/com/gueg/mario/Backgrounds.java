package com.gueg.mario;

import android.content.res.Resources;
import android.graphics.Rect;

import com.gueg.mario.entities.GameObjectFactory;
import com.gueg.mario.entities.UnanimatedObject;

public class Backgrounds {

    private Rect screenRect;
    Rect r1;
    Rect r2;
    Rect r3;

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

        r1 = screenRect;
        r2 = screenRect;
        r2.offset(screenRect.width(),0);
        r3 = screenRect;
        r3.offset(-screenRect.width(),0);

        GameObjectFactory<UnanimatedObject> factory = new GameObjectFactory<>(UnanimatedObject.class, res);
        factory.setSprite(R.drawable.bkg_0);
        background1 = factory.build();
        background1.setSize(screenRect.width(), screenRect.height());
        background1.setPos(r1.left,r1.top);
        background2 = factory.build();
        background2.setSize(screenRect.width(), screenRect.height());
        background2.setPos(r2.left,r2.top);
        background3 = factory.build();
        background3.setSize(screenRect.width(), screenRect.height());
        background3.setPos(r3.left,r3.top);
    }

    public void offset(int x) {

        r1.offset(-x / 6, 0);
        r2.offset(-x / 6, 0);
        r3.offset(-x / 6, 0);

        // Roll the backgrounds
        if (r1.right <= screenRect.left) {
            r1 = screenRect;
            r2 = screenRect;
            r2.offset(screenRect.width(), 0);
            r3 = screenRect;
            r3.offset(-screenRect.width(), 0);
        } else if (r1.left >= screenRect.right) {
            r1 = screenRect;
            r2 = screenRect;
            r2.offset(screenRect.width(), 0);
            r3 = screenRect;
            r3.offset(-screenRect.width(), 0);
        }
    }


}
