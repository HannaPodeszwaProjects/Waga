#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <stdlib.h> 
#include <windows.h>
#include <cstdio>
#include <conio.h>
#include <stdio.h>
#include <time.h>
#include "Stan.h"
#include "Ogolne.h"
#include "Pole.h"
#include "Plansza.h"
#include "Rozwiaz.h"
#include "Wynik.h"
#include "Wygrana.h"

using namespace std;

state Stan::do_wygrana()
{
	Wygrana wygrana1;
	system("cls");
	wypisz_menu();
	plansza_stan.wypisz_plansze();
	wygrana1.kaczki();
	wygrana1.wpisz_nick();
	wygrana1.set_czas(czas);
	wygrana1.zapisz();
	return MENU;
	
}
void Wygrana::wpisz_nick()
{
	system("cls");
	fseek(stdin, 0, SEEK_END); //usuniecie dodatkowych znakow z bufora
	ustaw_kursor(0, 0);
	cout << "Podaj nick: (max 20 znakow)" << endl;
	cin >> nick;
	nick = nick.substr(0, 20);
}

void Wygrana::kaczki()
{
	char spacja = ' ';
	
	for (int i = 0;i < 50;i++)
	{
		ustaw_kursor(40, 14);
		daj_kolor(14);
		wypisz_spacje(i);
		cout << "__(.)< __(.)> __(.)>" << endl;
		ustaw_kursor(40, 15);
		wypisz_spacje(i);
		cout << "\\\___)  \\\___)  \\\___)" << endl;
		ustaw_kursor(43, 16);
		wypisz_spacje(i);
		cout << "GRATULACJE" << endl;
		daj_kolor(15);
		Sleep(200);
	}
	ustaw_kursor(43, 20);
	system("pause");
	
}

bool Wygrana::zapisz()
{
	ofstream plik("wyniki.txt", ios::app);

	if (plik)
	{
		plik << *this;
	}
	else  cout << "Blad otwarcia pliku." << endl;

	return MENU;
}

ostream& operator<<(ostream& out,Wygrana &w)
{
	out << w.nick << endl << w.czas << endl;
	return out;
}