#include "map.h"

Map::Map(int w, int h) 
	: _height(h), _width(w) {
	_map = new Tile*[_height];
	for (int i = 0; i < _height; i++)
		_map[i] = new Tile[_width];
}

void Map::fill(int type) {
    for (int y = 0; y < _height; y++)
		for (int x = 0; x < _width; x++)
			_map[y][x].setType(type);
}

void Map::setTile(int x, int y, int type) {
    _map[_height - 1 - y][x].setType(type);
}

Tile Map::getTile(int x, int y) const {
    return _map[_height - 1 - y][x];
}

int Map::getWidth() const {
	return _width;
}

int Map::getHeight() const {
    return _height;
}

int** Map::toIntArray() const {
	int **map = new int*[_height];
	for (int i = 0; i < _height; i++)
		map[i] = new int[_width];

	for (int y = 0; y < _height; y++)
		for (int x = 0; x < _width; x++)
			map[y][x] = static_cast<int>(_map[y][x]);
	return map;
}

/*
	Run through the column starting from 0 and return the first non-ground tile
*/
int Map::getGroundHeight(int x) {
	for (int y = 0; y < _height; y++)
		if (!getTile(x, y).isGround())
			return y;
	return -1;
}

/*
	Run through the column starting from 0 and return the first empty tile
	Useful to put block or enemis on top of platforms connected with the ground
*/
int Map::getFreeTileHeight(int x) {
	for (int y = 0; y < _height; y++)
		if ((int)getTile(x, y) == Tiles::NONE)
			return y;
	return -1;
}

/*
	Return the number of %type tiles contained in the map
*/
int Map::count(int type) {
	int cnt = 0;
	for (int y = 0; y < _height; y++)
		for (int x = 0; x < _width; x++)
			if ((int)getTile(x, y) == type)
				cnt++;
	return cnt;
}

bool Map::areaContainsOnly(int xMin, int yMin, int xMax, int yMax, int types[], int length) {
	for (int x = xMin; x <= xMax; x++)
		for (int y = yMin; y <= yMax; y++) {
			if (length == 0) { // is area empty ?
				if ((int)getTile(x, y) != Tiles::NONE)
					return false;
			}
			else {
				bool typeFound = false;
				for (int i = 0; i < length; i++)
					if ((int)getTile(x, y) == types[i])
						typeFound = true;
				if (!typeFound)
					return false;
			}
		}
	return true;
}

bool Map::areaContainsNo(int xMin, int yMin, int xMax, int yMax, int types[], int length) {
	for (int x = xMin; x <= xMax; x++)
		for (int y = yMin; y <= yMax; y++) {
			if (length == 0) { // is area filled ?
				if ((int)getTile(x, y) == Tiles::NONE)
					return false;
			}
			else for (int i = 0; i < length; i++)
				if ((int)getTile(x, y) == types[i])
					return false;
		}
	return true;
}