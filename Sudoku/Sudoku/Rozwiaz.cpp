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

state Solver::rozwiaz_gracz(Plansza& p)
{
	COORD c;
	c.X = 2;
	c.Y = 7;

	while (1)
	{
		int cyfra = poruszanie_strzalkami(&c);
		Pole pole = znajdz_pole(c, p);

		switch (cyfra)
		{
		case w:
			return WYJDZ;
		case z:
			return ZAPISZ;
		case s:
			return SPRAWDZ;
		case r:
			return ROZWIAZ;
		}

		if (pole.get_z_pliku() == false)
		{
			if (cyfra == spacja)
			{
				cout << " ";
				pole.set_wartosc(-1);
			}
			else
			{
				cout << cyfra;
				pole.set_wartosc(cyfra);
			}
			p.set_tab((c.Y - 7) / 2, (c.X - 2) / 4, pole);
		}
	}
}

state Stan::do_rozwiaz()
{
	Rozwiaz1 rozwiaz2;
	Pole *tmp;
	for (int i = 0;i < 9;i++)
		for (int j = 0;j < 9;j++)
		{
			tmp = &plansza_stan.get_tab(i, j);
			if ((tmp->get_z_pliku()) != true)
			{
				Pole nowe_pole(-1, false, (2 + 4 * i), (7 + j * 2)); //wyczyszczenie wartosci wpisanych przez gracza
				plansza_stan.set_tab(i, j, nowe_pole);
			}
		}
	bool gotowe = rozwiaz2.rozwiaz(plansza_stan);
	if ( gotowe== true)
	{
		plansza_stan.wypisz_plansze();
		ustaw_kursor(43, 15);
		system("pause");
		return MENU;
	}
	else
	{
		ustaw_kursor(43, 15);
		cout << "Brak rozwiazania." << endl;
		ustaw_kursor(43, 16);
		system("pause");
			return MENU;
	}
}

bool Rozwiaz1::rozwiaz(Plansza &plansza_stan)
{
	int wiersz, kolumna;

	if (!znajdz_puste(plansza_stan,wiersz,kolumna)) //szuka wolnego pola
		return true;

	for (int i = 1;i <= 9;i++)
	{
		if (czy_moze(plansza_stan,wiersz, kolumna, i)) //sprawdza, czy mozna umiescic dana wartosc
		{
			Pole nowe_pole(i,false,wiersz,kolumna);
			plansza_stan.set_tab(wiersz, kolumna, nowe_pole);

			if (rozwiaz(plansza_stan))
				return true;
			nowe_pole.set_wartosc(-1);
			plansza_stan.set_tab(wiersz, kolumna, nowe_pole);
		}
	}
	return false;
}

bool Rozwiaz1::czy_moze(Plansza plansza_stan,int wiersz, int kolumna, int i)
{
	
	return !uzyj_kolumna( plansza_stan, kolumna, i) && !uzyj_wiersz(plansza_stan, wiersz, i)
		&& !uzyj_kwadrat(plansza_stan, wiersz - wiersz % 3, kolumna - kolumna % 3, i)
		&& plansza_stan.get_tab(wiersz, kolumna).get_wartosc()== -1;
}

bool Rozwiaz1::znajdz_puste(Plansza plansza_stan,int &wiersz, int &kolumna)
{
	for (wiersz = 0;wiersz < 9;wiersz++)
	{
		for (kolumna = 0; kolumna < 9; kolumna++)
		{
			if (plansza_stan.get_tab(wiersz, kolumna).get_wartosc() == -1)
				return true;
		}
	}
	return false;
}

bool Rozwiaz1::uzyj_wiersz(Plansza plansza_stan,int wiersz, int nr)
{
	for (int i = 0;i < 9;i++)
	{
		if (plansza_stan.get_tab(wiersz, i).get_wartosc() == nr)
			return true;
	}
	return false;
}

bool Rozwiaz1::uzyj_kolumna(Plansza plansza_stan, int kolumna, int nr)
{
	for (int i = 0;i < 9;i++)
	{
		if (plansza_stan.get_tab(i, kolumna).get_wartosc() == nr)
			return true;
	}
	return false;
}

bool Rozwiaz1::uzyj_kwadrat(Plansza plansza_stan, int wiersz, int kolumna, int nr)
{
	for (int i = 0;i < 3;i++)
		for (int j = 0;j < 3;j++)
		{
			if (plansza_stan.get_tab(i + wiersz, j + kolumna).get_wartosc() == nr)
				return true;
		}
				return false;
}

