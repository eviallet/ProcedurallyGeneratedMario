#ifndef UNDERGROUND_H
#define UNDERGROUND_H

#include "Generator.h"
#include "map.h"

constexpr int NB_TUNNELS = 75;
constexpr int MAX_LENGTH = 9;


class Underground : public Generator {
public:
	Underground(Map* map);
	void generate();
};

#endif // UNDERGROUND_H