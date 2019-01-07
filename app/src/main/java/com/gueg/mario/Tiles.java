package com.gueg.mario;


public enum Tiles {
    // TYPES
        NONE(0),
        GroundType(1000),
        PropType(2000),
        BlockType(3000),
        ObjectType(4000),
        TerrainType(5000),
        EnemyType(6000),

    // GROUND
        FIRST_GROUND(GroundType),
        GND(FIRST_GROUND),
        UP,
        LEFT,
        RIGHT,
        DOWN,
        UP_LEFT,
        UP_RIGHT,
        INVALID_GROUND,

    // PROPS
        FIRST_PROP(PropType),
        BUSH(FIRST_PROP),
        SIGN,
        INVALID_PROP,

    // BLOCKS
        FIRST_BLOCK(BlockType),
        QUESTION(FIRST_BLOCK),
        BRICK,
        INVALID_BLOCK,

    // OBJECTS
        FIRST_OBJECT(ObjectType),
        COIN(FIRST_OBJECT),
        FLAG,
        PIPE,
        INVALID_OBJECT,

    // TERRAINS
        FIRST_TERRAIN(TerrainType),
        PLATFORM(FIRST_TERRAIN),
        PLATFORM_GND,
        PLATFORM_LEFT,
        PLATFORM_RIGHT,
        PLATFORM_UP_LEFT,
        PLATFORM_UP_RIGHT,
        SLOPE_DOWN_LEFT_1,
        SLOPE_DOWN_LEFT_1_GND,
        SLOPE_DOWN_RIGHT_1,
        SLOPE_DOWN_RIGHT_1_GND,
        INVALID_TERRAIN,

    // ENEMIES
        FIRST_ENEMY(EnemyType),
        GOOMBA(FIRST_ENEMY),
        KOOPA_GREEN,
        KOOPA_RED,
        BOBOMB,
        BOO,
        DINO,
        DINO_FIRE,
        KAMEC,
        KOOPA_CAPE,
        MOLE,
        QUATERBACK,
        FLYING_ENEMIES,
        GOOMBA_FLYING,
        KOOPA_GREEN_FLYING,
        KOOPA_RED_FLYING,
        BOBOMB_PARACHUTE,
        BILL_BALL,
        INVALID_ENEMY;

    private int numVal;

    Tiles() {
        this(ConstructorHelper.last + 1);
    }

    Tiles(int numVal) {
        this.numVal = numVal;
        ConstructorHelper.last = numVal;
    }

    Tiles(Tiles other) {
        this.numVal = other.getVal();
        ConstructorHelper.last = numVal;
    }

    public final int getVal() {
        return numVal;
    }

    private static class ConstructorHelper {
        static int last = 0;
    }

}
