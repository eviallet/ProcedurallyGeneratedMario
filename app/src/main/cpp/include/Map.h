#ifndef MAP_H
#define MAP_H

#include "tile.h"

class Map {
public:
	Map(int w, int h);
	void fill(int type);
	void setTile(int row, int col, int type);
    Tile getTile(int row, int col) const;
    int getWidth() const;
    int getHeight() const;
	int ** toIntArray() const;
	int getGroundHeight(int x);
	int getFreeTileHeight(int x);
	int count(int type);
	bool areaContainsOnly(int xMin, int yMin, int xMax, int yMax, int types[] = 0, int length = 0);
	bool areaContainsNo(int xMin, int yMin, int xMax, int yMax, int types[], int length);
private:
	Tile** _map;
    int _width, _height;
};


#endif //MAP_H
