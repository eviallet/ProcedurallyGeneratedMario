#ifndef TILE_H
#define TILE_H

#include <iostream>

namespace Tiles {
	enum Types {
		NONE,
		GroundType=1000,
		PropType=2000,
		BlockType=3000,
		ObjectType=4000,
		TerrainType=5000,
		EnemyType=6000
	};
	
	enum Ground {
		FIRST_GROUND = GroundType,
		GND = FIRST_GROUND,
		UP,
		LEFT,
		RIGHT,
		DOWN,
		UP_LEFT,
		UP_RIGHT,
		INVALID_GROUND
	};
	
	enum Props {
		FIRST_PROP = PropType,
		BUSH = FIRST_PROP,
		SIGN,
		INVALID_PROP
	};

	enum Blocks {
		FIRST_BLOCK = BlockType,
		QUESTION = FIRST_BLOCK,
		BRICK,
		INVALID_BLOCK
	};
	
	enum Objects {
		FIRST_OBJECT = ObjectType,
		COIN = FIRST_OBJECT,
		FLAG,
		PIPE,		
		INVALID_OBJECT
	};

	enum Terrain {
		FIRST_TERRAIN = TerrainType,
		PLATFORM = FIRST_TERRAIN,
		PLATFORM_GND,
		PLATFORM_LEFT,
		PLATFORM_RIGHT,
		PLATFORM_UP_LEFT,
		PLATFORM_UP_RIGHT,
		SLOPE_DOWN_LEFT_1,
		SLOPE_DOWN_LEFT_1_GND,
		SLOPE_DOWN_RIGHT_1,
		SLOPE_DOWN_RIGHT_1_GND,
		INVALID_TERRAIN
	};

	enum Enemies {
		FIRST_ENEMY = EnemyType,
		GOOMBA = FIRST_ENEMY,
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
		INVALID_ENEMY
	};
};



class Tile {
public:
	Tile(int type = Tiles::NONE);
	void setType(int type);
	int getType() const;
	bool isGround();
	static bool isGround(int type);
	bool isStandable();
	operator int(){
		return _type;
	}
private:
	int _type;
};

inline bool operator==(const Tile &left, const Tile &right) {
	return left.getType() == right.getType() || (Tile::isGround(left.getType()) && Tile::isGround(right.getType()));
}
inline bool operator!=(const Tile &left, const Tile &right) {
	return !operator==(left, right);
}

#endif // TILE_H
