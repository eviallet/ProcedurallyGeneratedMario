#ifndef PERLIN_H
#define PERLIN_H

#include <math.h>
#include <fstream>
#include <iostream>
#include <vector>
#include <random>
#include <algorithm>
#include <numeric>
#include "random.h"

constexpr double MAX_X = 100.0;
constexpr double MAX_Y = 100.0;
constexpr double MAX_Z = 1.0;

// Perlin noise implementation from
// https://github.com/sol-prog/Perlin_Noise

class Perlin {
public:
	Perlin(double xMax, double yMax, double zMax);
	double noise(double x, double y, double z);
	void randomize();
	// Stats
	double getAverage();
	double getMedian();
	double getThirdQuartile();
	double getLastPercent();
	double getStatsAt(int percentage);
	// File
	void noiseToFile();
private:
	void init();
	double fade(double t);
	double lerp(double t, double a, double b);
	double grad(int hash, double x, double y, double z);
	// Stats
	double computeAverage();
	void computeStats();

private:
	std::vector<int> _p;
	std::vector<int> _p_0;
	std::vector<double> _stats;
	double _average = -1;
	double _median = -1;
	double _thirdQuartile = -1;
	double _lastPercent = -1;
	double _xMax, _yMax, _zMax;
};

#endif // PERLIN_H