#include "generator.h"

const int Generator::DIRECTIONS[][2] = { 
	{0,-1}, // LEFT
	{0,1},	// RIGHT
	{-1,0},	// UP
	{1,0}	// DOWN
};

Generator::Generator(Map *map, double xMax, double yMax, double zMax) {
	this->map = map;
	p = new Perlin(xMax, yMax, zMax);
}

