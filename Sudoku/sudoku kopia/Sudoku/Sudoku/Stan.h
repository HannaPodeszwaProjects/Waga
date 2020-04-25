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
#include <time.h>
#include "Ogolne.h"
#include "Plansza.h"
using namespace std;

/**Klasa Stan - obsluguje zmiany stanow
@param aktualny_stan aktualny stan
@param plansza_stan plansza
@param poprzedni_stan poprzedni stan
@param czas czas rozgrywki
@param tab[NUMBER] tablica wskaznikow na funkcje obslugujace stany
@see state get_aktualny_stan()
@see void set_aktualny_stan(state stan)
@see void set_poprzedni_stan(state stan) 
@see Stan(state stan) 

@see friend void wypisz_menu()
@see state do_menu(void)
@see state do_gra()
@see state do_wynik()
@see state do_sprawdz()
@see state do_zapisz()
@see state do_wyjdz()
@see state do_rozwiaz()
@see state do_wygrana()
@see state do_help()

@see void kolejny_stan()
*/
class Stan
{
private:
	state aktualny_stan;
	Plansza plansza_stan;
	state poprzedni_stan;
	time_t czas;
public:
	/**Funkcja wypisujaca aktualny stan
	@retrun Funkcja zwraca aktualny stan
	*/
	state get_aktualny_stan();
	/**Funkcja wpisujaca aktualny stan
	@param stan aktualny stan
	*/
	void set_aktualny_stan(state stan);
	/**Funkcja wpisujaca poprzedni stan
	@param stan poprzedni stan
	*/
	void set_poprzedni_stan(state stan) { poprzedni_stan = stan; }
	/**Konstruktor obiektu Stan
	@param stan aktualny stan
	*/
	Stan(state stan) { aktualny_stan = stan; }


	friend void wypisz_menu();
	/**Funkcja obslugujaca stan MENU
	@return Funkcja zwraca kolejny stan
	*/
	state do_menu(void);
	/**Funkcja obslugujaca stan GRA
	@return Funkcja zwraca kolejny stan
	*/
	state do_gra();
	/**Funkcja obslugujaca stan WYNIK
	@return Funkcja zwraca kolejny stan
	*/
	state do_wynik(); 
	/**Funkcja obslugujaca stan SPRAWDZ
	@return Funkcja zwraca kolejny stan
	*/
	state do_sprawdz();
	/**Funkcja obslugujaca stan ZAPISZ
	@return Funkcja zwraca kolejny stan
	*/
	state do_zapisz();
	/**Funkcja obslugujaca stan WYJDZ
	@return Funkcja zwraca kolejny stan
	*/
	state do_wyjdz();
	/**Funkcja obslugujaca stan ROZWIAZ
	@return Funkcja zwraca kolejny stan
	*/
	state do_rozwiaz();
	/**Funkcja obslugujaca stan WYGRANA
	@return Funkcja zwraca kolejny stan
	*/
	state do_wygrana();
	/**Funkcja obslugujaca stan HELP
	@return Funkcja zwraca kolejny stan
	*/
	state do_help();
	/**Funkcja ustawia kolejny stan
	*/
	void kolejny_stan();
private:
	state(Stan::* (tab[NUMBER]))(void) =
	{ NULL,&Stan::do_menu,&Stan::do_gra, &Stan::do_wynik,&Stan::do_sprawdz,
	&Stan::do_zapisz,&Stan::do_wyjdz,&Stan::do_rozwiaz,
		&Stan::do_wygrana,&Stan::do_help };
};


#endif