#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <stdlib.h> 
#include <windows.h>
#include <cstdio>
#include <conio.h>
#include <stdio.h>
#include "Stan.h"
#include "Pole.h"
#include "Gracz.h"

//#include <vld.h>

using namespace std;

void ustaw_kursor(int x, int y)
{
	COORD c;
	c.X = x;
	c.Y = y;
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), c);
}

void daj_kolor(int kolor, int tlo) //funkcja zmienia kolor czcionki
{
	HANDLE hOut;
	hOut = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hOut, kolor | tlo << 4);
}


void domyslny_kolor()
{
	daj_kolor(7, tlo_czarny);
}


state Stan::get_aktualny_stan() { return aktualny_stan; }
void Stan::set_aktualny_stan(state stan) { aktualny_stan = stan; }


void Stan::wypisz_menu()
{
	system("cls"); //czyszczenie konsoli
	ustaw_kursor(0, 0);
	daj_kolor(15, tlo_czarny);
	cout << endl << endl;
	cout << "*********************" << endl;
	cout << "*     CHINCZYK      *" << endl;
	cout << "*********************" << endl << endl;
}

state Stan::do_menu()
{
	system("cls");
	daj_kolor(15, tlo_czarny);

	wypisz_menu();

	cout << "1.GRAJ" << endl;
	cout << "2.WYNIKI" << endl;
	cout << "0.WYJSCIE" << endl;
	char wybor = '-1';
	fseek(stdin, 0, SEEK_END); //usuniecie dodatkowych znakow z bufora
	cin >> wybor;
	switch (wybor)
	{
	case '0':
		return STOP;
	case '1':
	{
		return GRA;
	}
	case '2':
		return WYNIK;
	case'h':
	{
		return HELP;
	}

	default:
	{
		cout << "Niepoprawna wartosc." << endl;
		system("pause");
		return MENU;
	}
	}
}


state Stan::do_help()
{
	system("cls");
	cout<<endl << "Gracze kolejno rzucaja kostka, naciskajac dowolny klawisz." << endl;
	cout << "Jesli maja mozliwosc ruchu, wybieraja pionek, ktory ma sie ruszyc, wybierajac cyfre od 1 do 4." << endl;
	cout << "Gra konczy sie, gdy ktorys z graczy umiesci 4 swoje pionki na srodku planszy." << endl<<endl;
	system("pause");
	return MENU;
}

Gracz& Gracz::operator= (Gracz& nowy)
{
	nr = nowy.nr;
	kolor_t = nowy.kolor_t;
	kolor_cz = nowy.kolor_cz;
	pionki_baza = nowy.pionki_baza;
	pionki_meta = nowy.pionki_meta;
	pionki_koniec = nowy.pionki_koniec;
	wejscie_meta = nowy.wejscie_meta;

	for (int i = 0;i < 4;i++)
	{

		p[i] = nowy.p[i];
	}

	return *this;
}

Pionek& Pionek::operator=(Pionek& nowy)
{
	id = nowy.id;
	kolor_cz = nowy.kolor_cz;
	baza = nowy.baza;
	meta = nowy.meta;
	koniec = nowy.koniec;
	polozenie = nowy.polozenie;
	blad1 = nowy.blad1;

	return *this;
}
Pole& Pole::operator=(Pole& nowy)
{
	id_pola = nowy.id_pola;
	gracz = nowy.gracz;
	pionek = nowy.pionek;
	kolor_tla = nowy.kolor_tla;
	zajete = nowy.zajete;
	x = nowy.x;
	y = nowy.y;

	return *this;
}

ostream& operator<<(ostream& out, Pole p)
{
	ustaw_kursor(p.get_x(), p.get_y());
	if (p.get_zajete())
	{
		daj_kolor(7, p.get_Pionek()->get_kolor_cz());
		out << p.get_pionek();
	}
	else
	{
		daj_kolor(7,p.kolor_tla);
		out << " ";
	}
	domyslny_kolor();
	ustaw_kursor(koniec_x, koniec_y);
	return out;
}