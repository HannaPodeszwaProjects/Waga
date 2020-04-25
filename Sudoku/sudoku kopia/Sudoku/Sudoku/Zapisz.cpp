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

state Stan::do_zapisz()
{
	
	ofstream plik("zapisane.txt");
	
	if (plik)
	{

		for (int i = 0;i < 9;i++)
		{
			for (int j = 0;j < 9;j++)
			{
				if (plansza_stan.get_tab(i, j).get_wartosc() == -1)
					plik << '-';
				else
					if (plansza_stan.get_tab(i, j).get_z_pliku() == false)
						plik << "g";
						plik << plansza_stan.get_tab(i, j).get_wartosc();
			}
			plik << endl;
		}
		plik.close();
		system("cls");
		cout <<"Zapisano plansze" << endl;
		system("pause");
	}
	else  cout << "Blad otwarcia pliku." << endl;
	
	return MENU;
}
