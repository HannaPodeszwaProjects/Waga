#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <stdlib.h> 
#include <windows.h>
#include <cstdio>
#include <conio.h>
#include <stdio.h>
#include <random>
#include "Stan.h"
#include "Pole.h"
#include "Gracz.h"
#include "Trasa.h"

void Gra::wygrana(int nr_gracza)
{
	Wygrana wygrana1;

	wygrana1.kaczki();
	wygrana1.wpisz_nick();
	wygrana1.zapisz();
}

void Wygrana::wpisz_nick()
{
	wypisz_komunikat("Podaj nick: (max 20 znakow)", false);
	fseek(stdin, 0, SEEK_END); //usuniecie dodatkowych znakow z bufora
	cin >> nick;
	wypisz_komunikat("                               ", false);
	nick = nick.substr(0, 20);
}



void Wygrana::kaczki()
{
	char spacja = ' ';

	for (int i = 0;i < 40;i++)
	{
		ustaw_kursor(50, 12);
		daj_kolor(zloty,tlo_czarny);
		wypisz_spacje(i);
		cout << "__(.)< __(.)> __(.)>" << endl;
		ustaw_kursor(50, 13);
		wypisz_spacje(i);
		cout << "\\\___)  \\\___)  \\\___)" << endl;
		ustaw_kursor(53, 14);
		wypisz_spacje(i);
		cout << "GRATULACJE";
		domyslny_kolor();
		Sleep(200);
	}
	ustaw_kursor(53, 20);

}

void wypisz_spacje(int s)
{
	for (int i = 0;i < s;i++)
		cout << " ";
} 

bool Wygrana::zapisz()
{
	ofstream plik("wyniki.txt", ios::app);

	if (plik)
	{
		plik << *this;

		plik.close();
		return true;
	}
	else  cout << "Blad otwarcia pliku." << endl;
	return false;

}

ostream& operator<<(ostream& out, Wygrana& w)
{
	out << w.nick << endl;
	return out;
}