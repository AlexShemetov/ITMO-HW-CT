#include "PNM.h"
#include <iostream>
#include <ctime>
#include <string>

int main(int argc, char** argv) {
	if (argc < 4) {
		std::cerr << "no arguments!";
		return -1;
	}

	int threads = std::atoi(argv[1]);
	std::string fileInput = argv[2];
	std::string fileOutput = argv[3];

	double kf = 0;
	if (argc >= 5) {
		kf = std::stod(argv[4]);
	}

	try {
		if (kf >= 0.5) {
			throw "too high value of kf";
		}
		if (kf < 0) {
			throw "kf cannot be negative";
		}
	}
	catch (char* ch) {
		std::cerr << ch;
		return -1;
	}

	std::ifstream fileIn(fileInput);
	if (!fileIn.is_open()) {
		std::cerr << "file not found";
		return -1;
	}
	std::string version;
	fileIn >> version;
	fileIn.close();
	if (version != "P5" && version != "P6") {
		std::cerr << "unsoported version";
		return -1;
	}

	unsigned int start = clock();
	PNM pnm(fileInput, kf, threads);
	unsigned int startWork = clock();
	pnm.increaseContrast();
	unsigned int endWork = clock();
	pnm.write(fileOutput);
	unsigned int end = clock();
	std::cout << "Reading: " << startWork - start << "ms\n";
	std::cout << "Time work: " << endWork - startWork << "ms\n";
	std::cout << "Writing: " << end - endWork << "ms\n";
	std::cout << "Total: " << end - start << "ms";

	return 0;
}