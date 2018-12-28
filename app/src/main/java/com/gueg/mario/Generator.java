package com.gueg.mario;

public class Generator {
    static {
        System.loadLibrary("level-generator");
    }

    public static native int[][] generateMap(int w, int h, int worldType);
}
