#ifndef GENERATOR_H
#define GENERATOR_H

#include <math.h>
#include "Map.h"
#include "Tile.h"
#include "Perlin.h"
#include "Random.h"

constexpr int Y = 0;
constexpr int X = 1;

enum MapType {
    OVERWORLD,
    UNDERGROUND
};

class Generator {
public:
	Generator(Map *map, double xMax = MAX_X, double yMax = MAX_Y, double zMax = MAX_Z);
protected:
	virtual void generate() = 0;
protected:
    static const int DIRECTIONS[][2];
	Perlin *p;
	Map *map;
};

#endif // GENERATOR_H
