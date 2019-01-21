#ifndef RANDOM_H
#define RANDOM_H

#include <random>
#include <time.h>

class Random {
public:
	static int dice(double prob);
	static double percent();
	static int uniform(int min, int max);
	static double uniform(double min, double max);
	static int binomial(int n, double p);
	static double gaussian(double mean, double variance);
private:
	Random() {};
	static std::default_random_engine generator;
};

#endif
