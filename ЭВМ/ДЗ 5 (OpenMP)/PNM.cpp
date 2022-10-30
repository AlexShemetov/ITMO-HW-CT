#include "PNM.h"

PNM::PNM(std::string file, double kf, int threads) : kf(kf), threads(threads) {
	std::ifstream img(file, std::ios::binary);

	img >> version;
	if (version == "P5") {
		Ncolor = 1;
	}
	else {
		Ncolor = 3;
	}

	img >> width >> height >> colorRange;
	char* buf = new char[width * Ncolor];
	img.read(buf, 1);

	data = new unsigned char** [height];
	for (int row = 0; row < height; row++) {
		data[row] = new unsigned char* [width];
		for (int col = 0; col < width; col++) {
			data[row][col] = new unsigned char[Ncolor];
		}
	}

	for (int row = 0; row < height; row++) {
		img.read(buf, width * Ncolor);
		for (int col = 0; col < width; col++) {
			for (int colors = 0; colors < Ncolor; colors++) {
				data[row][col][colors] = buf[col * Ncolor + colors];
			}
		}
	}


	img.close();
}

void PNM::increaseContrast() {
	omp_set_num_threads(threads);
	unsigned char minColor = 255, maxColor = 0;

	int* arr = new int[colorRange + 1];
	for (int i = 0; i < colorRange + 1; i++) {
		arr[i] = 0;
	}

#pragma omp parallel for schedule(static)
	for (int row = 0; row < height; row++) {
		for (int col = 0; col < width; col++) {
			for (int colors = 0; colors < Ncolor; colors++) {
				arr[int(data[row][col][colors])]++;
			}
		}
	}

	if (kf - 1e-6 > 0) {
		int pixels = width * height * 3;
		int count = 0, i;

		for (i = 0; count < pixels * kf; i++) {
			count += arr[i];
		}
		minColor = i;

		count = 0;
		for (i = colorRange; count < pixels * kf; i--) {
			count += arr[i];
		}
		maxColor = i;
	}
	else {
		int i;
		for (i = 0; arr[i] == 0; i++) {}
		minColor = i;

		for (i = colorRange; arr[i] == 0; i--) {}
		maxColor = i;
	}

	if (maxColor == minColor) {
		return;
	}

#pragma omp parallel for schedule(static)
	for (int row = 0; row < height; row++) {
		for (int col = 0; col < width; col++) {
			for (int colors = 0; colors < Ncolor; colors++) {
				int newPixel = colorRange * (data[row][col][colors] - minColor) / (maxColor - minColor);
				if (newPixel > 0 && newPixel < colorRange + 1) {
					data[row][col][colors] = newPixel;
				}
				else if (newPixel <= 0) {
					data[row][col][colors] = 0;
				}
				else {
					data[row][col][colors] = 255;
				}
			}
		}
	}
}

void PNM::write(std::string file) {
	std::ofstream img(file, std::ios::binary);

	img << version + "\n" << width << " " << height << "\n" << colorRange << "\n";

	char* buf = new char[width * Ncolor];
	for (int row = 0; row < height; row++) {
		for (int col = 0; col < width; col++) {
			for (int colors = 0; colors < Ncolor; colors++) {
				buf[col * Ncolor + colors] = data[row][col][colors];
			}
		}
		img.write(buf, width * Ncolor);
	}

	delete data;
	img.close();
}