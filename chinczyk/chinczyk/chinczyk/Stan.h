#pragma once
#ifndef Stan_H
#define Stan_H

#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <stdlib.h> 
#include <windows.h>
#include <cstdio>
#include <conio.h>
#include <stdio.h>
#include <iomanip>
#include "Pole.h"
#include "Gracz.h"
#include "Trasa.h"
#include "Gra.h"

enum  tlo
{
	tlo_czarny, tlo_niebieski, tlo_zielony, tlo_niebieski2, tlo_czerwony,
	tlo_fioletowy, tlo_pomaranczowy, tlo_szary, tlo_szary2, tlo_niebieski3,
	tlo_zielony2, tlo_niebieski4, tlo_rozowy, tlo_fioletowy2, tlo_zolty, tlo_bialy
};

enum czcionka
{
	czarny, niebieski1, zielony, turkus, czerwony, rozowy, zielony2, szary,
	szary2, niebieski2, zielony3, niebieski3, czerwony2, fioletowy2, zloty, bialy,
};

typedef enum
{
	poczatek_x = 3, poczatek_y = 6, koniec_x=poczatek_x, koniec_y=(poczatek_y+23), komunikat_x=poczatek_x+60, komunikat_y = 11+poczatek_y
}plansza;

typedef enum
{
	STOP, MENU, GRA,WYNIK, HELP, NUMBER
}state;

typedef enum
{
	zero =48
}stale;

typedef enum
{
	kolor1_tlo=tlo_czerwony,kolor2_tlo=tlo_niebieski,kolor3_tlo=fioletowy2,kolor4_tlo=tlo_zielony,
	kolor1_cz=tlo_czerwony, kolor2_cz=tlo_niebieski, kolor3_cz= fioletowy2, kolor4_cz=tlo_zielony,
	podstawowy_kolor=tlo_czarny
}kolor;


void ustaw_kursor(int,int); //ustawia kursor w wybranym miejscu
void domyslny_kolor();
void daj_kolor(int kolor, int tlo);
void wypisz_komunikat(string s, bool czekaj);


class Stan
{
private:
	state aktualny_stan;
	state poprzedni_stan;
public:
	Stan(state stan) { aktualny_stan = stan; }

	//funkcje obslugujace poszczegolne stany
	state do_menu(void);
	state do_gra(void);
	state do_wynik(void);
	state do_help(void);

	state get_aktualny_stan();
	void set_aktualny_stan(state stan);

	void wypisz_menu(); 
	void wypisz_plansze();

	/**Funkcja ustawia kolejny stan
	*/
	void kolejny_stan();
private:
	state(Stan::* (tab[NUMBER]))(void) =
	{ NULL,&Stan::do_menu,&Stan::do_gra,&Stan::do_wynik,&Stan::do_help };
};




#endif