#include "Random.h"

std::default_random_engine Random::generator;

/*
return true 1 time out of prob*100 with 0<=prob<=1.
Example : prob = 1/2 will return true 1 time out of 2
*/
int Random::dice(double prob) {
	std::uniform_int_distribution<int> distribution(0, 100);
	return prob * 100 < distribution(generator);
}

/*
return a number between 0. and 1.
*/
double Random::percent() {
	std::uniform_int_distribution<int> distribution(0, 100);
	return (double)distribution(generator) / 100;
}


/*
uniform_int_distribution (0,9):
0: *********
1: *********
2: *********
3: *********
4: *********
5: *********
6: *********
7: *********
8: *********
9: *********
*/
int Random::uniform(int min, int max) {
	std::uniform_int_distribution<int> distribution(min, max);
	return distribution(generator);
}

/*
uniform_real_distribution (0.0,1.0):
0.0-0.1: *********
0.1-0.2: *********
0.2-0.3: *********
0.3-0.4: *********
0.4-0.5: *********
0.5-0.6: *********
0.6-0.7: *********
0.7-0.8: *********
0.8-0.9: *********
0.9-1.0: *********
*/
double Random::uniform(double min, double max) {
	std::uniform_real_distribution<double> distribution(min, max);
	return distribution(generator);
}

/*
binomial_distribution (9,0.5):
0:
1: *
2: ******
3: ****************
4: *************************
5: ************************
6: *****************
7: *****
8: *
9:
*/
int Random::binomial(int n, double p) {
	std::binomial_distribution<int> distribution(n, p);
	return distribution(generator);
}

/*
normal_distribution (5.0,2.0):
0-1: *
1-2: ****
2-3: *********
3-4: ***************
4-5: ******************
5-6: *******************
6-7: ***************
7-8: ********
8-9: ****
9-10:*
*/
double Random::gaussian(double mean, double variance) {
	std::normal_distribution<double> distribution(mean, variance);
	return distribution(generator);
}
