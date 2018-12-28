#include "Tile.h"


Tile::Tile(int type) {
	_type = type;
}

void Tile::setType(int type) {
	_type = type;
}

int Tile::getType() const {
	return _type;
}

bool Tile::isGround() {
	return Tiles::Ground::FIRST_GROUND <= _type && _type < Tiles::INVALID_GROUND;
}

bool Tile::isGround(int type) {
	return Tiles::Ground::FIRST_GROUND <= type && type < Tiles::INVALID_GROUND;
}

bool Tile::isStandable() {
	return _type == Tiles::Ground::UP || _type == Tiles::Terrain::PLATFORM || Tiles::Terrain::PLATFORM_UP_RIGHT || Tiles::Terrain::PLATFORM_UP_LEFT;
}


