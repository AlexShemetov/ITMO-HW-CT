#include <omp.h>
#include <fstream>

class PNM
{
public:
	PNM(std::string file, double kf, int threads);
	void write(std::string file);
	void increaseContrast();
private:
	std::string version;
	int width, height;
	int colorRange;
	double kf;
	int threads;
	int Ncolor;
	unsigned char*** data;
};