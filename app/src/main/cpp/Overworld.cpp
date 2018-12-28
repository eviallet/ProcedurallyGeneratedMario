#include "Overworld.h"

Overworld::Overworld(Map *map) 
	: Generator(map, map->getWidth(), map->getHeight(), MAX_Z) 
{}

void Overworld::generate() {
	map->fill(Tiles::NONE);
	for (int x = 0; x < map->getWidth(); x++)
		map->setTile(x, 0, Tiles::Ground::UP);

	map->setTile(SIGN_POS, 1, Tiles::Props::SIGN);
	map->setTile(map->getWidth() - 1 - FLAG_POS, 1, Tiles::Objects::FLAG);

	randomizeTerrainHoles();
	p->randomize();
	randomizeTerrainPlatforms();
	p->randomize();
	//randomizeSlopes();
	//p->randomize();
	randomizeBlocks();
	p->randomize();
	randomizeEnemies();
}

void Overworld::randomizeTerrainHoles() {
	for (int x = SIGN_POS + 2; x < map->getWidth() - FLAG_POS - 5; x++) {
		if (p->noise(x, Random::uniform(0, (int)MAX_Y), MAX_Z) > p->getStatsAt(90)) {
			int length = Random::uniform(1, 4);
			if (x + length < map->getWidth() + 1) {
				for (int i = 0; i < length; i++)
					map->setTile(x + i, 0, Tiles::NONE);
				map->setTile(x - 1, 0, Tiles::Ground::UP_RIGHT);
				map->setTile(x + length, 0, Tiles::Ground::UP_LEFT);
				x += length + 3;
			}
		}
	}
}

void Overworld::randomizeTerrainPlatforms() {
	// for all map length
	for (int x = SIGN_POS + 2; x < map->getWidth() - FLAG_POS - 5; x++) {
		// if random noise at this point is greater than 90% other points
		if (p->noise(x, Random::uniform(0, (int)MAX_Y), MAX_Z) > p->getStatsAt(90) && x + 5 < map->getWidth()-1) {
			// then, for a random length
			int length = Random::uniform(3, 6);
			// at a random height
			int height = Random::uniform(1, 4);
			int y = map->getGroundHeight(x);
			if (y != -1) {
				y += height;
				// place platforms
				for (int i = 0; i < length; i++) {
					if(i == 0)
						map->setTile(x + i, y, Tiles::Terrain::PLATFORM_UP_LEFT);
					else if(i == length - 1)
						map->setTile(x + i, y, Tiles::Terrain::PLATFORM_UP_RIGHT);
					else
						map->setTile(x + i, y, Tiles::Terrain::PLATFORM);
					// and link them to the ground
					for (int j = 1; j <= y - map->getGroundHeight(x + i); j++) {
						if(i == 0)
							map->setTile(x + i, y - j, Tiles::Terrain::PLATFORM_LEFT);
						else if(i == length - 1)
							map->setTile(x + i, y - j, Tiles::Terrain::PLATFORM_RIGHT);
						else
							map->setTile(x + i, y - j, Tiles::Terrain::PLATFORM_GND);
					}
				}
				x += length + 3;
			}
		}
	}
}


void Overworld::randomizeBlocks() {
	int standable[] = {
		Tiles::Ground::UP,
		Tiles::Ground::UP_LEFT,
		Tiles::Ground::UP_RIGHT,
		Tiles::Terrain::PLATFORM
	};
	int y, blockHeight = 2;
	// for all map length
	for (int x = SIGN_POS + 2; x < map->getWidth() - FLAG_POS - 7; x++) {
		// if random noise at this point is greater than 90% other points
		if (p->noise(x, Random::uniform(0, (int)MAX_Y), MAX_Z) > p->getStatsAt(90)) {
			// and if freeTileHeight + blockHeight is still in bounds
			if ((y = map->getFreeTileHeight(x)) > 0 && y + blockHeight < map->getHeight()) {
				int lastI = 0;
				// then, for a random length
				int length = Random::binomial(6, 0.3);
				for (int i = 0; i < length; i++) {
					// if there is no standable tile near the future block location
					if (map->areaContainsNo(
						x + i,				// xMin
						blockHeight - 1,	// yMin
						x + i,				// xMax
						blockHeight + 1,	// yMax
						standable,
						4))
					{
						// place a question mark block (1/4 odd) or a brick
						Random::dice(1./4) ? map->setTile(x + i, y + blockHeight, Tiles::Blocks::QUESTION) : map->setTile(x + i, y + blockHeight, Tiles::Blocks::BRICK);
						lastI = i;
					}
					//else break;
				}
				// and add 5 to the current position, +(0~4) for the next blocks to start from
				x += lastI + Random::uniform(5, 9);
			}
		}
	}
}

void Overworld::randomizeSlopes() {
	// 1/2 odd of having a slope in the level
	if (Random::dice(1./2)) {
		double maxHeight = abs(Random::gaussian(4, 0.75));
		int length = abs(Random::gaussian(10, 3));
		int xStart = Random::uniform(SIGN_POS + 2, map->getWidth() - FLAG_POS - length);

		// find a suitable place to place the slope, ie an empty rectangle of (xLength + i * maxHeight)
		while (!map->areaContainsOnly(
				xStart,				// xMin
				1,					// yMin
				xStart + length,	// xMax
				(int)maxHeight)		// yMax
			&& xStart < map->getWidth() - FLAG_POS - length)
			xStart++;

		// if such a place have been found
		if (xStart != map->getWidth() - FLAG_POS - length) {
			// get the noise following that path, which will be continuous for a nice curve
			std::vector<double> slope;
			for (int x = xStart; x < xStart + length; x++)
				slope.push_back(abs((double)p->noise(x, 0, MAX_Z)));
			// get the max and min value of that noise
			double min = *std::min_element(slope.begin(), slope.end());
			double max = *std::max_element(slope.begin(), slope.end());
			// convert each point to a usable map height, with respect to maxHeight defined earlier
			for (int i = 0; i < slope.size(); i++)
				slope.at(i) = [&]() -> int { return (slope.at(i) - min) * (maxHeight - 1) / (max - min) + 1; }();
			// then place each point and connect them to the ground
			for (int x = 0; x < length; x++) {
				if (x == 0)
					for (int y = 1; y < slope.at(x) - 1; y++)
						map->setTile(x + xStart, y, Tiles::Terrain::PLATFORM_LEFT);
				else if (x == length - 1)
					for (int y = 1; y < slope.at(x) - 1; y++)
						map->setTile(x + xStart, y, Tiles::Terrain::PLATFORM_RIGHT);
				else
					for (int y = 0; y < slope.at(x) - 1; y++)
						map->setTile(x + xStart, y, Tiles::Ground::GND);

				if (x != length - 1) {
					if (slope.at(x) < slope.at(x + 1)) {
						map->setTile(x + xStart, slope.at(x), Tiles::Terrain::SLOPE_DOWN_LEFT_1);
						map->setTile(x + xStart, slope.at(x) - 1, Tiles::Terrain::SLOPE_DOWN_LEFT_1_GND);
					}
					else if (slope.at(x) > slope.at(x + 1)) {
						map->setTile(x + xStart, slope.at(x), Tiles::Terrain::SLOPE_DOWN_RIGHT_1);
						map->setTile(x + xStart, slope.at(x) - 1, Tiles::Terrain::SLOPE_DOWN_RIGHT_1_GND);
					}
					else
						map->setTile(x + xStart, slope.at(x) - 1, Tiles::Terrain::PLATFORM);
				}
				else {
					map->setTile(x + xStart, slope.at(x) - 1, Tiles::Terrain::PLATFORM);
				}
			}
		}
	}
}

void Overworld::randomizeEnemies() {
	// for all map length
	for (int x = SIGN_POS + 2; x < map->getWidth() - FLAG_POS - 2; x++) {
		// if random noise at this point is greater than 90% other points
		if (p->noise(x, Random::uniform(0, (int)MAX_Y), MAX_Z) > p->getStatsAt(95)) {
			int y = map->getGroundHeight(x);
			if (y > 0)
				map->setTile(x, y, Random::uniform(Tiles::Enemies::FIRST_ENEMY, Tiles::Enemies::INVALID_ENEMY - 1));
			else
				map->setTile(x, abs(Random::gaussian(3, 1)), Random::uniform(Tiles::Enemies::FLYING_ENEMIES, Tiles::Enemies::INVALID_ENEMY - 1));
			x += Random::gaussian(2, 0.7);
		}
	}
}



