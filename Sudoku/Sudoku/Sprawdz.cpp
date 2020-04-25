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

state Stan::do_sprawdz()
{
	for (int i = 0;i < 9;i++)
	{
		for (int j = 0;j < 9;j++)
		{
			if (plansza_stan.get_tab(i, j).get_wartosc() == -1)
			{
				ustaw_kursor(40, 14);
				cout << "Plansza nie jest wypelniona." << endl;
				ustaw_kursor(43, 15);
				system("pause");
				poprzedni_stan = SPRAWDZ;
				return GRA;
			}
		}
	}
	
	if (plansza_stan.sprawdz(plansza_stan))
	{
		return WYGRANA;
	}
	else
	{
		ustaw_kursor(43, 19);
		cout << "Plansza nie jest wypelnona poprawnie." << endl;
		ustaw_kursor(43, 20);
		system("pause");
		poprzedni_stan = SPRAWDZ;
		return GRA;
	}
}
void wypisz_spacje(int s)
{
	for (int i = 0;i < s;i++)
		cout << " ";
}

bool Plansza::sprawdz(Plansza p)
{
	//wiersze
	for (int a = 0; a < 9; a++)
	{
		for (int b = 0; b < 9; b++)
		{
			for (int j = b + 1; j < 9; j++)
			{
				if (p.get_tab(a, b).get_wartosc() == p.get_tab(a, j).get_wartosc())
				{
					return false;
				}
			}
		}
	}
	//kolumny
	for (int b = 0; b < 9; b++)
	{
		for (int a = 0; a < 9; a++)
		{
			for (int i = a + 1; i < 9; i++)
			{
				if (p.get_tab(a, b).get_wartosc() == p.get_tab(i, b).get_wartosc())
				{
					return false;
				}
			}
		}
	}
	//kwadrat
	for (int i = 0; i < 9; i += 3)
	{

		for (int j = 0; j < 9; j += 3) //wieksze kwadraty
		{
			for (int c = i; c < (i + 3); c++)
			{
				for (int d = j; d < (j + 3); d++) //przesuwanie sie po elemenatch mniejszego kwadratu
				{
					for (int a = i; a < (i + 3); a++)
					{
						for (int b = j; b < (j + 3); b++)
						{
							if (c == a && d == b)
								break;
							if (p.get_tab(c, d).get_wartosc() == p.get_tab(a, b).get_wartosc())
							{
								std::cout << c << " " << d << "kwadrat" << std::endl;
								return false;
							}
						}
					}
				}
			}
		}

	}

	return true;
}
