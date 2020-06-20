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

using namespace std;

Gra::Gra(Trasa t)
{
	trasa_gra = t;
}

Trasa::Trasa()
{
	for (int i = 0;i < 40;i++)
	{
		Pole nowe_pole(i,-1, 7, false, 0, 0);
		trasa[i] = nowe_pole;
		trasa[i].set_kolor_tla(podstawowy_kolor);
	}
	for (int i = 0;i < 4;i++)
	{
		for (int j = 0;j < 4;j++)
		{
			Pole nowe_pole(j,-1, -1, false, 0, 0);
			baza[i][j] = nowe_pole;
		}
	}
	for (int i = 0;i < 4;i++)
	{
		for (int j = 0;j < 6;j++)
		{
			Pole nowe_pole(j,-1, -1, false, 0, 0);
			meta[i][j] = nowe_pole;
		}
	}

	wpisz_wspolrzedne();
	wpisz_wspolrzedne_baza();
	wpisz_wspolrzedne_meta();
	wpisz_kolory();
}

void Trasa::wpisz_wspolrzedne_meta()
{
	for (int i = 0;i < 4;i++)
	{
		switch (i)
		{
		case 0:
			for (int j = 0;j < 5;j++)
			{
				meta[i][j].set_x(poczatek_x + 6 + j * 4);
				meta[i][j].set_y(poczatek_y + 11);
			}
			break;
		case 1:
			for (int j = 0;j < 5;j++)
			{
				meta[i][j].set_x(poczatek_x + 22);
				meta[i][j].set_y(poczatek_y + 3 + j * 2);
			}
			break;
		case 2:
			for (int j = 0;j < 5;j++)
			{
				meta[i][j].set_x(poczatek_x + 38 - j * 4);
				meta[i][j].set_y(poczatek_y + 11);
			}
			break;
		case 3:
			for (int j = 0;j < 5;j++)
			{
				meta[i][j].set_x(poczatek_x + 22);
				meta[i][j].set_y(poczatek_y + 19 - j * 2);
			}
			break;
		}
	}

}

void Trasa::wpisz_wspolrzedne_baza()
{
	for (int i = 0;i < 4;i++)
	{
		for (int j = 0;j < 4;j += 2)
		{
			if (i < 2)
			{
				baza[i][j].set_x(poczatek_x + 2 + 37 * (i % 2));
				baza[i][j].set_y(poczatek_y + 1 + j);
				baza[i][j + 1].set_x(poczatek_x + 6 + 37 * (i % 2));
				baza[i][j + 1].set_y(poczatek_y + 1 + j);
			}
			else
			{
				baza[i][j].set_x(poczatek_x + 2 + 37 * ((i + 1) % 2));
				baza[i][j].set_y(poczatek_y + j + 19);
				baza[i][j + 1].set_x(poczatek_x + 6 + 37 * ((i + 1) % 2));
				baza[i][j + 1].set_y(poczatek_y + j + 19);
			}

		}
	}
}

void Trasa::wpisz_wspolrzedne()
{
	for (int i = 0;i < 5;i++) //prawo
	{
		trasa[i].set_x(poczatek_x + 2 + i * 4);
		trasa[i].set_y(poczatek_y + 9);
	}
	for (int i = 5;i < 9;i++) //gora
	{
		trasa[i].set_x(poczatek_x + 18);
		trasa[i].set_y(poczatek_y + 7 - 2 * (i - 5));
	}
	trasa[9].set_x(poczatek_x + 22);
	trasa[9].set_y(poczatek_y + 1);

	for (int i = 10;i < 15;i++) //dol
	{
		trasa[i].set_x(poczatek_x + 26);
		trasa[i].set_y(poczatek_y + 1 + 2 * (i - 10));
	}
	for (int i = 15;i < 19;i++) //prawo
	{
		trasa[i].set_x(poczatek_x + 30 + ((i - 15) * 4));
		trasa[i].set_y(poczatek_y + 9);
	}
	trasa[19].set_x(poczatek_x + 42);
	trasa[19].set_y(poczatek_y + 11);

	for (int i = 20;i < 25;i++) //lewo
	{
		trasa[i].set_x(poczatek_x + 42 - ((i - 20) * 4));
		trasa[i].set_y(poczatek_y + 13);
	}
	for (int i = 25;i < 29;i++) //dol
	{
		trasa[i].set_x(poczatek_x + 26);
		trasa[i].set_y(poczatek_y + 15 + 2 * (i - 25));
	}
	trasa[29].set_x(poczatek_x + 22);
	trasa[29].set_y(poczatek_y + 21);

	for (int i = 30;i < 35;i++) //gora
	{
		trasa[i].set_x(poczatek_x + 18);
		trasa[i].set_y(poczatek_y + 21 - 2 * (i - 30));
	}
	for (int i = 35;i < 39;i++) //lewo
	{
		trasa[i].set_x(poczatek_x + 14 - ((i - 35) * 4));
		trasa[i].set_y(poczatek_y + 13);
	}
	trasa[39].set_x(poczatek_x + 2);
	trasa[39].set_y(poczatek_y + 11);

}

void Trasa::wpisz_kolory()
{
	trasa[0].set_kolor_tla(kolor1_tlo);
	trasa[10].set_kolor_tla(kolor2_tlo);
	trasa[20].set_kolor_tla(kolor3_tlo);
	trasa[30].set_kolor_tla(kolor4_tlo);

	int kolor;
	for (int i = 0;i < 4;i++)
	{
		switch (i)
		{
		case 0:
			kolor = kolor1_tlo;
			break;
		case 1:
			kolor = kolor2_tlo;
			break;
		case 2:
			kolor = kolor3_tlo;
			break;
		case 3:
			kolor = kolor4_tlo;
			break;
		}
		for (int j = 0;j < 4;j++)
		{
			baza[i][j].set_kolor_tla(kolor);
		}
		for (int j = 0;j < 6;j++)
		{
			meta[i][j].set_kolor_tla(kolor);
		}
	}
}

void Gra::wypisz_bazy()
{
	for (int i = 0;i < 4;i++) //dla kazdego gracz
	{
		for (int j = 0;j < 4;j++) //dla kazdego pionka
		{
			cout << trasa_gra.get_baza(i, j);
		}
	}
}

void Gra::wpisz_graczy()
{
	int kolor;
	for (int i = 0;i < 4;i++)
	{
		Gracz tmp(i, 4, 0,0);
		gracz[i] = tmp;
		for (int j = 0;j < 4;j++)
		{
			switch (i) {
			case 0: 
				kolor = kolor1_cz; break;
			case 1: 
				kolor = kolor2_cz; break;
			case 2: 
				kolor = kolor3_cz; break;
			case 3: 
				kolor = kolor4_cz; break;
			}
			Pionek tmpp(j, kolor, true, false, false, j);
			gracz[i].set_p(j, tmpp);
			(trasa_gra.get_baza(i, j)).set_zajete(true);
			//trasa_gra.get_baza(i,j).				//ustawnienie gracza, ktorego pionek stoi na polu
			trasa_gra.get_baza(i, j).set_Pionek(&(gracz[i].get_p(j))); //umieszczenie pionka w bazie
		}
	}
	gracz[0].set_kolor(kolor1_cz, kolor1_tlo);
	gracz[0].set_wejscie_meta(39);
	gracz[1].set_kolor(kolor2_cz, kolor2_tlo);
	gracz[1].set_wejscie_meta(9);
	gracz[2].set_kolor(kolor3_cz, kolor3_tlo);
	gracz[2].set_wejscie_meta(19);
	gracz[3].set_kolor(kolor4_cz, kolor4_tlo);
	gracz[3].set_wejscie_meta(29);
}

Pionek::Pionek(int i, int k, bool b, bool m, bool kon, int p/*,blad* bl*/)
{
	id = i;
	kolor_cz = k;
	baza = b;
	meta = m;
	koniec = kon;
	polozenie = p;
	//blad1 = bl;
}

Gracz::Gracz(int n, int b, int m, int k)
{
	nr = n;
	pionki_baza = b;
	pionki_meta = m;
	pionki_koniec = k;
}

Pole::Pole(int i, int g, int k, bool z, int x_n, int y_n)
{
	id_pola = i;
	gracz = g;
	kolor_tla = k;
	zajete = z;
	x = x_n;
	y = y_n;
}

void Stan::wypisz_plansze()
{
	//gorne ramie

	ustaw_kursor(16 + poczatek_x, poczatek_y);
	cout << "ษอออหอออหอออป";
	for (int i = 0;i < 8;i += 2)
	{
		ustaw_kursor(16 + poczatek_x, poczatek_y + 1 + i);
		cout << "บ   บ   บ   บ";
		ustaw_kursor(16 + poczatek_x, poczatek_y + 2 + i);
		cout << "ฬอออฮอออฮอออน";
	}
	cout << endl;

	//lewe ramie

	ustaw_kursor(poczatek_x, poczatek_y + 8);
	cout << "ษอออหอออหอออหอออฮอออฮอออฮอออฮอออหอออหอออหอออป";
	ustaw_kursor(poczatek_x, poczatek_y + 9);
	for (int i = 4;i < 7;i++)
	{
		cout << "บ ";
		for (int j = 0;j < 11;j++)
		{
			cout << "  บ ";
		}
		ustaw_kursor(poczatek_x, poczatek_y + 2 * i + 2);
		cout << "ฬอออฮอออฮอออฮอออฮอออฮอออฮอออฮอออฮอออฮอออฮอออน";
		ustaw_kursor(poczatek_x, poczatek_y + 2 * i + 3);
	}


	ustaw_kursor(poczatek_x, 14 + poczatek_y);
	cout << "ศอออสอออสอออสอออฮอออฮอออฮอออฮอออสอออสอออสอออผ";
	ustaw_kursor(poczatek_x, 15 + poczatek_y);
	//dolne ramie

	for (int i = 0;i < 8;i += 2)
	{
		ustaw_kursor(16 + poczatek_x, 15 + poczatek_y + i);
		cout << "บ   บ   บ   บ";
		ustaw_kursor(16 + poczatek_x, 16 + poczatek_y + i);
		cout << "ฬอออฮอออฮอออน";
	}
	ustaw_kursor(16 + poczatek_x, 22 + poczatek_y);
	cout << "ศอออสอออสอออผ";

	//wypisanie baz graczy

	ustaw_kursor(poczatek_x, poczatek_y);
	daj_kolor(7, kolor1_tlo);
	cout << "ษอออหอออป";
	ustaw_kursor(poczatek_x, poczatek_y + 1);
	cout << "บ   บ   บ";
	ustaw_kursor(poczatek_x, poczatek_y + 2);
	cout << "ฬอออฮอออน";
	ustaw_kursor(poczatek_x, poczatek_y + 3);
	cout << "บ   บ   บ";
	ustaw_kursor(poczatek_x, poczatek_y + 4);
	cout << "ศอออสอออผ";
	domyslny_kolor();

	ustaw_kursor(37 + poczatek_x, poczatek_y);
	daj_kolor(7, kolor2_tlo);
	cout << "ษอออหอออป";
	ustaw_kursor(37 + poczatek_x, 1 + poczatek_y);
	cout << "บ   บ   บ";
	ustaw_kursor(37 + poczatek_x, 2 + poczatek_y);
	cout << "ฬอออฮอออน";
	ustaw_kursor(37 + poczatek_x, 3 + poczatek_y);
	cout << "บ   บ   บ";
	ustaw_kursor(37 + poczatek_x, 4 + poczatek_y);
	cout << "ศอออสอออผ";
	domyslny_kolor();

	ustaw_kursor(poczatek_x, 18 + poczatek_y);
	daj_kolor(7, kolor4_tlo);
	cout << "ษอออหอออป";
	ustaw_kursor(poczatek_x, 19 + poczatek_y);
	cout << "บ   บ   บ";
	ustaw_kursor(poczatek_x, 20 + poczatek_y);
	cout << "ฬอออฮอออน";
	ustaw_kursor(poczatek_x, 21 + poczatek_y);
	cout << "บ   บ   บ";
	ustaw_kursor(poczatek_x, 22 + poczatek_y);
	cout << "ศอออสอออผ";
	domyslny_kolor();

	ustaw_kursor(37 + poczatek_x, 18 + poczatek_y);
	daj_kolor(7, kolor3_tlo);
	cout << "ษอออหอออป";
	ustaw_kursor(37 + poczatek_x, 19 + poczatek_y);
	cout << "บ   บ   บ";
	ustaw_kursor(37 + poczatek_x, 20 + poczatek_y);
	cout << "ฬอออฮอออน";
	ustaw_kursor(37 + poczatek_x, 21 + poczatek_y);
	cout << "บ   บ   บ";
	ustaw_kursor(37 + poczatek_x, 22 + poczatek_y);
	cout << "ศอออสอออผ";
	domyslny_kolor();

	//kolorowanie planszy

	daj_kolor(7, kolor1_tlo);
	ustaw_kursor(poczatek_x, 8 + poczatek_y);
	cout << "ษอออห";
	ustaw_kursor(poczatek_x, 9 + poczatek_y);
	cout << "บ   บ";
	ustaw_kursor(poczatek_x, 10 + poczatek_y);
	cout << "ฬอออฮ";
	ustaw_kursor(4 + poczatek_x, 10 + poczatek_y);
	cout << "ฮอออฮอออฮอออฮอออฮ";
	for (int i = 0;i < 4;i++)
	{
		ustaw_kursor(poczatek_x + 4 + i * 4, 11 + poczatek_y);
		cout << "บ   ";
	}
	cout << "บ";
	ustaw_kursor(4 + poczatek_x, 12 + poczatek_y);
	cout << "ฮอออฮอออฮอออฮอออฮ";


	daj_kolor(7, kolor2_tlo);
	ustaw_kursor(24 + poczatek_x, poczatek_y);
	cout << "หอออป";

	ustaw_kursor(24 + poczatek_x, 1 + poczatek_y);
	cout << "บ   บ";

	ustaw_kursor(24 + poczatek_x, 2 + poczatek_y);
	cout << "ฮอออน";

	ustaw_kursor(20 + poczatek_x, 2 + poczatek_y);
	cout << "ฮอออฮ";
	domyslny_kolor();
	for (int i = 0;i < 8;i += 2)
	{
		ustaw_kursor(20 + poczatek_x, poczatek_y + 3 + i);
		daj_kolor(7, kolor2_tlo);
		cout << "บ   บ";
		ustaw_kursor(20 + poczatek_x, poczatek_y + 4 + i);
		cout << "ฮอออฮ";
		domyslny_kolor();
	}
	domyslny_kolor();


	daj_kolor(7, kolor3_tlo);
	ustaw_kursor(40 + poczatek_x, 12 + poczatek_y);
	cout << "ฬอออน";
	ustaw_kursor(40 + poczatek_x, 13 + poczatek_y);
	cout << "บ   บ";
	ustaw_kursor(40 + poczatek_x, 14 + poczatek_y);
	cout << "สอออผ";
	ustaw_kursor(24 + poczatek_x, 10 + poczatek_y);
	cout << "ฮอออฮอออฮอออฮอออฮ";
	for (int i = 0;i < 4;i++)
	{
		ustaw_kursor(poczatek_x + 24 + i * 4, 11 + poczatek_y);
		cout << "บ   ";
	}
	cout << "บ";
	ustaw_kursor(24 + poczatek_x, 12 + poczatek_y);
	cout << "ฮอออฮอออฮอออฮอออฮ";


	daj_kolor(7, kolor4_tlo);
	ustaw_kursor(16 + poczatek_x, 20 + poczatek_y);
	cout << "ฬอออฮ";
	ustaw_kursor(16 + poczatek_x, 21 + poczatek_y);
	cout << "บ   บ";
	ustaw_kursor(16 + poczatek_x, 22 + poczatek_y);
	cout << "ศอออส";
	ustaw_kursor(20 + poczatek_x, 12 + poczatek_y);
	cout << "ฮอออฮ";
	for (int i = 0;i < 8;i += 2)
	{
		ustaw_kursor(20 + poczatek_x, poczatek_y + 13 + i);
		cout << "บ   บ";
		ustaw_kursor(20 + poczatek_x, poczatek_y + 14 + i);
		cout << "ฮอออฮ";
	}
	domyslny_kolor();

	//srodek

	daj_kolor(czerwony, tlo_bialy);
	ustaw_kursor(20 + poczatek_x, 10 + poczatek_y);
	cout << "ฮอออฮ";
	ustaw_kursor(20 + poczatek_x, 11 + poczatek_y);
	cout << "บ   บ";
	ustaw_kursor(20 + poczatek_x, 12 + poczatek_y);
	cout << "ฮอออฮ";
	ustaw_kursor(poczatek_x, 23 + poczatek_y);

	domyslny_kolor();
}
