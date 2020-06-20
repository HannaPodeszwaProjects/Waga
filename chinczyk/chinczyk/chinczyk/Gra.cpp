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
#include "Blad.h"

using namespace std;

state Stan::do_gra()
{
	Trasa trasa1;
	Gra gra(trasa1);
	gra.wpisz_graczy();
	wypisz_menu();
	wypisz_plansze();
	gra.wypisz_bazy();


	int wynik =gra.graj();
	if (!wynik)
	{
		gra.wygrana(gra.get_nr_aktualnego_gracza());
	}
	return MENU;
}


int Gra::graj()
{
	srand(time(NULL));
	set_nr_aktualnego_gracza(rand() % 4);
	set_kostka(0);
	int zwracana;

	while (1)
	{
		set_nr_aktualnego_gracza((get_nr_aktualnego_gracza() + 1) % 4);
		ustaw_kursor(60, 4);
		cout << "Kolej gracza: " << nr_aktualnego_gracza + 1;
		wypisz_komunikat("Rzuc kostka.", true);
		rzuc_kostka();


		if (!sprawdz()) //jesli zaden pionek nie moze sie ruszyc
		{
			wypisz_komunikat("Brak mozliwosci ruchu.", true);
		}
		else
		{
			przesun_pionek(nr_aktualnego_gracza, kostka);
		}
		if (gracz[nr_aktualnego_gracza].get_pionki_koniec() == 4)
		{
			wypisz_komunikat("KTOS TU CHYBA WYGRAL!!!!!!!!!!", true);
			zwracana = 0;
			break;
		}
	}

	return zwracana;
}

void Gra::przesun_pionek(int nr_aktualnego_gracza, int kostka)
{
	int znak = -1;
	int ile_pionkow; //ile pionkow jest w bazie/ na mecie
	int polozenie_pionka;
	int gdzie_jest=-1; //informacja gdzie zostanie przeniesiony pionek //0 koniec // 1 meta

	while (znak == -1)
	{
	znak = pobierz_znak();
	}
	
	this->set_wybrany_znak(znak);
	Pole* pole_przed=nullptr;
	if (gracz[nr_aktualnego_gracza].get_p(znak).get_baza() == true) //jesli wybrany pionek jest w bazie
	{
		pole_przed = &(trasa_gra.znajdz_baza(nr_aktualnego_gracza, znak)); //pole na ktorym stoi wybrany pionek
		polozenie_pionka = nr_aktualnego_gracza * 10;
		if (trasa_gra.get_pole(polozenie_pionka).get_zajete()) //kolizja
		{
			kolizja(polozenie_pionka);
		}
		zajmij_pole(trasa_gra.get_pole(polozenie_pionka), gracz[nr_aktualnego_gracza].get_p(znak),polozenie_pionka);
		
		gracz[nr_aktualnego_gracza].get_p(znak).set_baza(false); //pionek juz nie jest w bazie

		ile_pionkow = gracz[nr_aktualnego_gracza].get_pionki_baza();
		gracz[nr_aktualnego_gracza].set_pionki_baza(ile_pionkow - 1);
	}
	else if (gracz[nr_aktualnego_gracza].get_p(znak).get_meta() == true) //jesli wybrany pionek jest na mecie
	{
		pole_przed = &(trasa_gra.znajdz_meta(nr_aktualnego_gracza, znak)); //pole na ktorym stoi wybrany pionek
		polozenie_pionka = gracz[nr_aktualnego_gracza].get_p(znak).get_polozenie();
		nastepne_pole(polozenie_pionka, kostka, gdzie_jest);
		if (gdzie_jest != 0) //jesli jest jeszcze na mecie
		{
			zajmij_pole(trasa_gra.get_meta(nr_aktualnego_gracza, polozenie_pionka), gracz[nr_aktualnego_gracza].get_p(znak),polozenie_pionka);
		}
	}
	else  //jesli wybrany pionek jest na trasie
	{
		pole_przed = &(trasa_gra.znajdz_pole(nr_aktualnego_gracza, znak)); //pole na ktorym stoi wybrany pionek
		polozenie_pionka = gracz[nr_aktualnego_gracza].get_p(znak).get_polozenie(); //aktualne polozenie
		nastepne_pole(polozenie_pionka, kostka, gdzie_jest);

		if (gdzie_jest == 1) //jesli pionek wszedl na mete
		{
			zajmij_pole(trasa_gra.get_meta(nr_aktualnego_gracza, polozenie_pionka), gracz[nr_aktualnego_gracza].get_p(znak), polozenie_pionka);
			
			gracz[nr_aktualnego_gracza].get_p(znak).set_meta(true); //pionek jest na mecie
			ile_pionkow = gracz[nr_aktualnego_gracza].get_pionki_meta();
			gracz[nr_aktualnego_gracza].set_pionki_meta(ile_pionkow + 1);
		}
		else if (gdzie_jest !=0) //jesli dalej jest na trasie
		{
			zajmij_pole(trasa_gra.get_pole( polozenie_pionka), gracz[nr_aktualnego_gracza].get_p(znak), polozenie_pionka);
		}
	}
	if (gdzie_jest == 0) //pionek przeszedl na koniec
	{
		int ilosc_pionkow = gracz[nr_aktualnego_gracza].get_pionki_koniec();
		gracz[nr_aktualnego_gracza].set_pionki_koniec(ilosc_pionkow + 1);
		ilosc_pionkow = gracz[nr_aktualnego_gracza].get_pionki_meta();
		gracz[nr_aktualnego_gracza].set_pionki_meta(ilosc_pionkow - 1);
		gracz[nr_aktualnego_gracza].get_p(znak).set_koniec(true);
	}
	pole_przed->set_gracz(-1);
	pole_przed->set_zajete(false);						 //pole nie jest teraz zajete
	cout << *pole_przed; //usuniecie pionka z poprzedniego pola
}

void Gra::zajmij_pole(Pole& pole, Pionek &pionek, int polozenie_pionka)
{
	pole.set_Pionek(&pionek); //ustawienie pionka na polu
	pole.set_zajete(true); //pole jest teraz zajete
	pole.set_gracz(nr_aktualnego_gracza); //zajete przez tego gracza  
	gracz[nr_aktualnego_gracza].get_p(wybrany_znak).set_polozenie(polozenie_pionka); //nowe polozenie
	cout << pole;
}

void Gra::nastepne_pole(int& polozenie, int kostka, int& gdzie)
{
	while (kostka)
	{
		//jesli zgadza sie gracz i nr pionka a polozenie na mecie+kostka==5 to pionek jest w domu
		if (polozenie<4 &&trasa_gra.get_meta(nr_aktualnego_gracza, polozenie).get_zajete() &&trasa_gra.get_meta(nr_aktualnego_gracza, polozenie).get_gracz() == nr_aktualnego_gracza &&
			trasa_gra.get_meta(nr_aktualnego_gracza, polozenie).get_Pionek()->get_id() == get_wybrany_znak() && (polozenie + kostka) == 4)
		{
			gdzie = 0;
			kostka = 0;
		}
		else
		{
			if (trasa_gra.get_pole(polozenie).get_id_pola() == gracz[nr_aktualnego_gracza].get_wejscie_meta()) //przejscie na mete
			{
				if (kostka == 5) //pionek idzie do domu
				{
					gdzie = 0;
					kostka = 0;
				}
				else //pionek jest na mecie
				{
					polozenie = kostka - 1;
					kostka = 0;
					gdzie = 1;
				}
			}
			else
			{
				polozenie++;		//przesuniecie pionka
				polozenie = polozenie % 40;
				if (kostka == 1 && trasa_gra.get_pole(polozenie).get_zajete()) //kolizja
				{
					kolizja(polozenie);
				}
				kostka--;
			}
		}
	}
	polozenie = polozenie % 40; //bo trasa ma 40 pol
}

void Gra::kolizja(int polozenie)
{
	int nr; //nr przenoszonego pionka
	int nr_gracza;
	Pionek* p;
	int ilosc_pionkow;
	nr = trasa_gra.get_pole(polozenie).get_Pionek()->get_id(); //nr pionka
	nr_gracza = trasa_gra.get_pole(polozenie).get_gracz(); //nr gracza
	p = trasa_gra.get_pole(polozenie).get_Pionek();
	trasa_gra.get_baza(nr_gracza, nr).set_Pionek(p); //pionek wraca do bazy
	trasa_gra.get_baza(nr_gracza, nr).set_zajete(true);
	gracz[nr_gracza].get_p(nr).set_baza(true);
	trasa_gra.get_pole(polozenie).set_zajete(false);

	ilosc_pionkow = gracz[nr_gracza].get_pionki_baza();
	gracz[nr_gracza].set_pionki_baza(ilosc_pionkow + 1);
	cout << trasa_gra.get_baza(nr_gracza, nr);

}

int Gra::pobierz_znak()
{
	while (_kbhit())
		_getch();
	wypisz_komunikat("Wybierz pionek:", false);


	int znak = _getch();
	wypisz_komunikat("                     ", false);
	znak -= 48; //z ascii
	znak--; //bo tablica od 0 do 3
	Pionek* p = &(gracz[nr_aktualnego_gracza].get_p(znak)); //wybrany pionek
	
	try
	{
		znak = sprawdz_zakres(znak);
		if (p->get_blad()) //jesli ten pionek zwracal blad
		{
			p->wyswietl_blad(*((gracz[nr_aktualnego_gracza].get_p(znak)).get_blad())); 
			return -1;
		}
		return znak;
	}
	catch (zly_znak type)
	{
		zly_znak x;
		x.wypisz_blad();
	}
	return -1;

}

Pole& Trasa::znajdz_pole(int gracz, int pionek)
{
	for (int i = 0; i < 40;i++)
	{
		if (get_pole(i).get_zajete())
			if (get_pole(i).get_gracz() == gracz && (get_pole(i).get_Pionek()->get_id()) == pionek)
				return get_pole(i);
	}
}
Pole& Trasa::znajdz_meta(int gracz, int pionek)
{
	for (int i = 0;i < 4;i++)
	{
		if (get_meta(gracz, i).get_zajete())
			if ((get_meta(gracz, i).get_Pionek()->get_id()) == pionek)
				return get_meta(gracz, i);
	}
}

Pole& Trasa::znajdz_baza(int gracz, int pionek)
{
	for (int i = 0;i < 4;i++)
	{
		if (get_baza(gracz, i).get_zajete())
			if ((get_baza(gracz, i).get_Pionek()->get_id()) == pionek)
				return get_baza(gracz, i);

	}
}


void wypisz_komunikat(string s, bool czekaj)
{
	ustaw_kursor(komunikat_x, komunikat_y);
	domyslny_kolor();
	cout << s;
	if (czekaj)
	{
		ustaw_kursor(komunikat_x, komunikat_y + 2);
		system("pause");
		ustaw_kursor(komunikat_x, komunikat_y + 2);
		cout << "                                        ";
		ustaw_kursor(komunikat_x, komunikat_y);
		cout << "                                                ";
	}


}

void Gra::rzuc_kostka()
{
	set_kostka(rand() % 6 + 1);
	ustaw_kursor(komunikat_x, komunikat_y - 9);
	cout << " KOSTKA ";
	ustaw_kursor(komunikat_x, komunikat_y - 8);
	cout << "*********";
	ustaw_kursor(komunikat_x, komunikat_y - 7);
	cout << "*   " << kostka << "   *";
	ustaw_kursor(komunikat_x, komunikat_y - 6);
	cout << "*********";
}
