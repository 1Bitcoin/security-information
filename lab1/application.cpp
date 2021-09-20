#include <iostream>
#include <stdio.h>
#include <fstream>
#include <string>
#include <sstream>

#include <algorithm> 
#include <functional> 
#include <cctype>
#include <locale>
#include <windows.h>

// trim from start
static inline std::string &ltrim(std::string &s) {
	s.erase(s.begin(), std::find_if(s.begin(), s.end(),
		std::not1(std::ptr_fun<int, int>(std::isspace))));
	return s;
}

// trim from end
static inline std::string &rtrim(std::string &s) {
	s.erase(std::find_if(s.rbegin(), s.rend(),
		std::not1(std::ptr_fun<int, int>(std::isspace))).base(), s.end());
	return s;
}

// trim from both ends
static inline std::string &trim(std::string &s) {
	return ltrim(rtrim(s));
}

int checkLicense() {
	int codeError = 0;

	try {
		HKEY hk;

		long result = RegOpenKeyEx(HKEY_CURRENT_USER, TEXT("SOFTWARE\\Key"), 0, KEY_READ, &hk);

		if (result == ERROR_SUCCESS) {
			system("wmic csproduct get uuid | more +1 > tmp.key");
			system("reg query HKEY_CURRENT_USER\\Software\\Key /v key | more +2 > tmp.key 3> nul");

			std::ifstream file("tmp.key");
			std::string registeredUUID;
			std::getline(file, registeredUUID);
			file.close();

			char buffer[37];
			std::size_t length = registeredUUID.copy(buffer, 36, 21);
			buffer[36] = 0;
			std::string uuid = buffer;
			//std::cout << uuid << '!' << '\n';

			file = std::ifstream("tmp.key");
			std::string currentUUID;
			system("wmic csproduct get uuid | more +1 > tmp.key");
			std::getline(file, currentUUID);
			file.close();

			trim(currentUUID);
			//std::cout << currentUUID << '!' << '\n';

			//std::cout << currentUUID.length() << '\n';
			//std::cout << uuid.length() << '\n';


			if (uuid != currentUUID) {
				codeError = 1;
			}

			system("erase tmp.key");
		}
		else {
			codeError = 1;
		}
	}
	catch (...) {
		codeError = 1;
		std::cout << "Some error" << '\n';
	}

	return codeError;
}

int main()
{
	if (!checkLicense()) {
		std::cout << "Hello, User!\n";
	}
	else {
		std::cout << "Buy license, please!\n";
	}

	system("PAUSE");

	return 0;
}


