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
#include "vld.h"
#include "Stan.h"
#include "Ogolne.h"
#include "Pole.h"
#include "Plansza.h"
#include "Rozwiaz.h"
#include "Wynik.h"
#include "Wygrana.h"



using namespace std;

state Stan::do_help()
{
	system("cls");
	ustaw_kursor(0, 0);
	cout<< "Rozwiazanie sudoku polega na wypelnieniu planszy 9 x 9 w taki sposob,"<<endl<<
		"aby w kazdym wierszu, w kazdej kolumnie i w kazdym z dziewieciu"<<endl<<
		"pogrubionych kwadratow 3 x 3 znalazlo sie po jednej cyfrze od 1 do 9."<<endl<<endl;
	cout << "Po planszy poruszaj sie przy pomocy strzalek." << endl<<endl;
	system("pause");
	return MENU;
}

state Stan::do_wyjdz()
{
	return MENU;
}
void daj_kolor(int kolor)
{
	HANDLE hOut;
	hOut = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hOut, kolor);
}
void ustaw_kursor(int x, int y)
{
	COORD c;
	c.X = x;
	c.Y = y;
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), c);
}



Plansza &Plansza::operator=(Plansza &p)
{
	for(int i = 0;i < 9;i++)
	{
		for (int j = 0;j < 9;j++)
		{
			set_tab(i,j, p.get_tab(i, j));
		}
	}
	return *this;
}

int poruszanie_strzalkami(COORD *c)
{
	int Klawisz;
	
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), (*c));
	while (1)
	{
		int x = _getch();
		if (x == 224)
		{
			Klawisz = _getch();

			switch (Klawisz)
			{
			case 72:
				//gora
				if ((*c).Y > 7)
				{
					(*c).Y = (*c).Y - 2;
					SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), *c);
				}
				break;

			case 80:
				//dol
				if ((*c).Y < 23)
				{
					(*c).Y = (*c).Y + 2;
					SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), *c);
				}
				break;
			case 75:
				//lewo
				if ((*c).X > 2)
				{
					(*c).X = (*c).X - 4;
					SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), *c);
				}
				break;
			case 77:
				//prawo
				if ((*c).X < 34)
				{
					(*c).X = (*c).X + 4;
					SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), *c);
				}
				break;
			default:
				cout << "Bledny znak." << endl;
				break;
			}
		}
		else
		{
			if (x > zero && x < 58)
				return x - zero;
			else if (x == w || x == s || x == z|| x==r||x==spacja)
				return x;				
		}
	}
}

Pole Plansza::get_tab(int x, int y)
{
	return tab[x][y];
}
Pole Plansza::set_tab(int x, int y, Pole cos)
{
	tab[x][y] = cos;
	return tab[x][y];
}


Pole znajdz_pole(COORD c, Plansza plansza)
{
	for(int i=0;i<9;i++)
		for (int j = 0;j < 9;j++)
		{
			if (plansza.get_tab(i,j).get_x() == c.X && plansza.get_tab(i, j).get_y() == c.Y)
				return plansza.get_tab(i, j);
		}
}


Pole::Pole(int w, bool z_p, int X, int Y)
{
	wartosc = w;
	z_pliku = z_p;
	x = X;
	y = Y;
}

state Stan::get_aktualny_stan() { return aktualny_stan; }
void Stan::set_aktualny_stan(state stan) { aktualny_stan = stan; }


Pole& Pole::operator=(Pole& nowa)
{
	wartosc = nowa.wartosc;
	z_pliku = nowa.z_pliku;
	x = nowa.x;
	y = nowa.y;
	return *this;
}


bool Pole::operator==(Pole p)
{
	if (x == p.x && y == p.y)
		return true;
	return false;
}

