#include <iostream>
#include <stdio.h>
#include <fstream>
#include <string>
#include <sstream>

int setUUID() {
	int codeError = 0;

	try {
		system("wmic csproduct get uuid | more +1 > lic.key");
		system("reg add HKEY_CURRENT_USER\\Software\\Key /f");

		std::ifstream file("lic.key");
		std::string line;
		std::getline(file, line);
		file.close();

		//std::cout << line << '\n';

		std::string magic = "reg add HKEY_CURRENT_USER\\Software\\Key /f /v key /t REG_SZ /d ";
		magic = magic + line;
		system(magic.c_str());

		system("erase lic.key");

	}
	catch (...) {
		codeError = 1;
		std::cout << "Some error" << '\n';
	}

	return codeError;
}

int main()
{
	if (!setUUID()) {
		printf("Activation is succeed\n");
	}
	else {
		printf("Activation is failed\n");
	}

	system("PAUSE");

	return 0;
}
