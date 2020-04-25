#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <iomanip>
#include <limits>

#include<limits.h>
#include "string.h"
#include "pch.h"
#include "struktury.h"
#include "funkcje.h"  

#define debug(x) std::cerr << "(" << __LINE__ << ") " << #x << " == " << (x) << std::endl;

int main(int ile, char ** params)
{	
	std::string wejscie = "", wyjscie = "", start = "";
    bool params_ok = sprawdz_argumenty (ile, params, wejscie, wyjscie, start);
	if (params_ok)
	{
		//wypisanie danych z pliku
		miasto * pGlowa = nullptr;
		wczytajzPliku(wejscie, pGlowa);

		//algorytm Dijkstry
		bool brak = Dijkstra(start, pGlowa);
		if(brak==false)
			std::cout << "Brak miasta startowego" << std::endl;
		wypisz_wynik(pGlowa, wyjscie); // wypisanie wyniku
		usun(pGlowa); 
	}
	else
	{
		help(ile, params);
		std::cout << std::endl;
		std::cout << "Bledne argumenty" << std::endl;
	}
    return 0;
}

