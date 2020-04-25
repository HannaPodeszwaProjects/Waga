#pragma once
#ifndef Rozwiaz_H
#define Rozwiaz_H

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
#include "Plansza.h"
using namespace std;

/**Klasa Rozwiaz - klasa obslugujaca rozwiazywanie planszy
@param plansza plansza
@see state rozwiaz_gracz(Plansza&)
@see Rozwiaz(Plansza nowa)
@see bool rozwiaz(Plansza&)
@see bool czy_moze(Plansza, int, int, int)
@see bool znajdz_puste(Plansza, int&, int&)
@see bool uzyj_wiersz(Plansza, int, int)
@see bool uzyj_kolumna(Plansza, int, int)
@see bool uzyj_kwadrat(Plansza, int, int, int)
*/
class Rozwiaz : public Plansza
{
private:
	Plansza plansza;
public:
	/**Funkcja umozliwiajaca rozwiazanie sudoku przez gracza
	@param Plansza plansza
	@return Funkcja zwraca kolejny stan
	*/
	state rozwiaz_gracz(Plansza&);

	/**Konstruktor obiektu klasy Rozwiaz
	@param nowa plansza
	*/
	Rozwiaz(Plansza nowa) { plansza = nowa; }

	/**Funkcja umozliwiajaca automatyczne rozwiazywanie sudoku
	@param Plansza plansza
	@return Funkcja zwraca czy plansza zostala rozwiazana
	*/
	bool rozwiaz(Plansza&);
	/**Funkcja sprawdza czy mozna umiescic cyfre
	@param Plansza plansza
	@param int wiersz
	@param int kolumna
	@param int cyfra
	@return Funkcja zwraca czy mozna umiescic cyfre
	*/
	bool czy_moze(Plansza, int, int, int);
	/**Funkcja znajduje puste pole
	@param Plansza plansza
	@param int wiersz
	@param int kolumna
	@return Funkcja zwraca czy pole jest puste
	*/
	bool znajdz_puste(Plansza, int&, int&);
	/**Funkcja sprawdza czy mozna umiescic cyfre w wierszu
	@param Plansza plansza
	@param int wiersz
	@param int cyfra
	@return Funkcja zwraca czy mozna umiescic cyfre w wierszu
	*/
	bool uzyj_wiersz(Plansza, int, int);
	/**Funkcja sprawdza czy mozna umiescic cyfre w kolumnie
	@param Plansza plansza
	@param int kolumna
	@param int cyfra
	@return Funkcja zwraca czy mozna umiescic cyfre w kolumnie
	*/
	bool uzyj_kolumna(Plansza, int, int);
	/**Funkcja sprawdza czy mozna umiescic cyfre w kwadracie
	@param Plansza plansza
	@param int wiersz
	@param int kolumna
	@param int cyfra
	@return Funkcja zwraca czy mozna umiescic cyfre w kwadracie
	*/
	bool uzyj_kwadrat(Plansza, int, int, int);
};


#endif
