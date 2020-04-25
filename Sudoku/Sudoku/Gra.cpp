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
#include "Pole.h"
#include "Plansza.h"
#include "Ogolne.h"
#include "Rozwiaz.h"

using namespace std;

state Stan::do_gra()
{
	time_t czas_poczatek, czas_koniec;

	system("cls");
	wypisz_menu();
	Plansza plansza1;
	Rozwiaz1 rozwiaz;
	if (poprzedni_stan != SPRAWDZ)
	{
		char wybor;
		fseek(stdin, 0, SEEK_END); //usuniecie dodatkowych znakow z bufora
		cout << "Jaka plansze wyswietlic?" << endl;
		cout << "1.Z poprzedniej gry." << endl << "2.Z pliku." << endl << "3.Stworz nowa." << endl;
		cin >> wybor;
		switch (wybor)
		{
		case '1':
		{
			plansza1.set_tab(0, 0, plansza1.uzupelnij_z_pliku("zapisane.txt")); //przepisanie planszy z pliku
			break;
		}
		case '2':
		{
			plansza1.set_tab(0, 0, plansza1.uzupelnij_z_pliku("tabele.txt")); //przepisanie planszy z pliku
			break;
		}
		case '3':
		{
			Pole p;
			p.set_wartosc(-10);
			plansza1.set_tab(0, 0, p);
			while (plansza1.get_tab(0, 0).get_wartosc() == -10)
			{
				plansza1.set_tab(0, 0, plansza1.stworz_nowa(plansza1)); //stworzenie nowej planszy
			}
			break;
		}
		case '9':
		{
			plansza1.set_tab(0, 0, plansza1.uzupelnij_z_pliku("poprawne.txt")); //plik z poprawnie wypelniona plansza
			break;
		}
		default:
		{
			cout << "Nieprawidlowa wartosc." << endl;
			system("pause");
			return GRA;
		}
		}
	}
	else
	{
		plansza1 = plansza_stan;
	}
	if (plansza1.get_tab(0, 0).get_wartosc() == -100) //gdy plansza nie wczyta sie z pliku
	{
		return MENU;
	}
	poprzedni_stan = GRA;

	//wypisanie planszy
	plansza1.wypisz_plansze();

	cout << "s.SPRAWDZ" << endl << "z.ZAPISZ" << endl << "w.WYJDZ BEZ ZAPISYWANIA" << endl << "r.ROZWIAZ" << endl
		<<"By usunac blednie wpisana wartosc wcisnij spacje."<<endl;
	time(&czas_poczatek);
	state kolejny_stan = rozwiaz.rozwiaz_gracz(plansza1);
	plansza_stan = plansza1;
	time(&czas_koniec);
	czas += (czas_koniec - czas_poczatek); //obliczanie czasu rozgrywki

	return kolejny_stan;
}

Pole Plansza::uzupelnij_z_pliku(string nazwa_pliku)
{
	ifstream plik(nazwa_pliku);

	int n_wartosc;
	if (plik)
	{

		for (int i = 0;i < 9;i++)
		{
			for (int j = 0;j < 9;j++)
			{
				n_wartosc = plik.get();
				while (n_wartosc == 10) //koniec pliku
				{
					n_wartosc = plik.get();
				}
				if (n_wartosc == 45) //znak -
				{
					n_wartosc = -1;
					tab[i][j].set_z_pliku(false);
				}
				else if (n_wartosc == g) //znacznik, ze wartosc byla wpisana przez gracza
				{
					n_wartosc = plik.get();
					n_wartosc -= 48;
					tab[i][j].set_z_pliku(false);
				}
				else
				{
					n_wartosc -= 48; //zamiana z ascii
					tab[i][j].set_z_pliku(true);
				}
				Pole nowa(n_wartosc, tab[i][j].get_z_pliku(), 2 + j * 4, 7 + i * 2); //wpisanie nowej wartosci
				tab[i][j] = nowa;
			}
		}
		plik.close();
	}
	else
	{
		cout << "Blad otwarcia pliku." << endl;
		system("pause");
		tab[0][0].set_wartosc(-100); //znacznik ze nastapil blad
	}
	return tab[0][0];
}

Pole Plansza::stworz_nowa(Plansza& plansza2)
{
	srand(time(0));
	Rozwiaz1 rozwiaz2;
	for (int i = 0;i < 9;i++)
		for (int j = 0;j < 9;j++)
		{
			Pole p(-1, false, (2 + 4 * j), (7 + i * 2)); //wypelnienien planszy pustymi wartosciami
			plansza2.set_tab(i, j, p);
		}

	for (int i = 1;i < 10;i++)
	{
		int x = (rand() % 9);
		int y = (rand() % 9);
		Pole p(i, true, (2 + 4 * x), (7 + y * 2)); //ustawienie 9 poczatkowych wartosci
		plansza2.set_tab(x, y, p);
	}
	bool gotowe = rozwiaz2.rozwiaz(plansza2); //wypelnienie calej planszy
	if (gotowe == true)
	{
		usun_losowe(plansza2); //usuwanie wartosci
		return plansza2.get_tab(0, 0);
	}
	else
	{
		Pole tmp;
		tmp.set_wartosc(-10); //oznaczenie ze plansza nie zostala utworzona
		plansza2.set_tab(0, 0, tmp);
		cout << "Brak rozwiazania" << endl;
		return tmp;
	}
}
void Plansza::usun_losowe(Plansza& plansza2)
{

	for (int i = 0;i < 9;i++)
		for (int j = 0;j < 9;j++)
		{
			Pole p(plansza2.get_tab(i, j).get_wartosc(), true, (2 + 4 * j), (7 + i * 2)); //ustawienie wszytskich wartosci na z pliku
			plansza2.set_tab(i, j, p);
		}

	int i = 0;
	int losowe = (rand() % 4) + 48;
	while (i < losowe)
	{
		int x = (rand() % 9);
		int y = (rand() % 9);
		Pole p(-1, false, (2 + 4 * y), (7 + x * 2));
		if (plansza2.get_tab(x, y).get_wartosc() != -1) //usuniecie i ustawienie na nie z pliku
		{
			i++;
			plansza2.set_tab(x, y, p);
		}
	}
}
