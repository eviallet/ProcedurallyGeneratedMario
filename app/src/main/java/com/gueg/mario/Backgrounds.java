package com.gueg.mario;

import android.content.res.Resources;
import android.graphics.Rect;

import com.gueg.mario.objects.UnanimatedObject;

import static com.gueg.mario.MainActivity.screenRect;

public class Backgrounds {

    Rect r1;
    Rect r2;
    Rect r3;

    UnanimatedObject background1;
    UnanimatedObject background2;
    UnanimatedObject background3;

    Backgrounds(Resources res) {
        r1 = screenRect;
        r2 = screenRect;
        r2.offset(screenRect.width(),0);
        r3 = screenRect;
        r3.offset(-screenRect.width(),0);


        background1 = new UnanimatedObject(res,R.drawable.bkg_0,false);
        background1.setPos(r1.left,r1.top);
        background2 = new UnanimatedObject(res,R.drawable.bkg_0,false);
        background2.setPos(r2.left,r2.top);
        background3 = new UnanimatedObject(res,R.drawable.bkg_0,false);
        background3.setPos(r3.left,r3.top);
    }


}
