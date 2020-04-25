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

/**Klasa Solver - klasa bazowa
@see state rozwiaz_gracz(Plansza&)
*/
class Solver
{
public:
	/**Funkcja umozliwiajaca rozwiazanie sudoku przez gracza
	@param Plansza plansza
	@return Funkcja zwraca kolejny stan
	*/
	state rozwiaz_gracz(Plansza&);
};

/**Klasa Rozwiaz2 - klasa obslugujaca inna metode rozwiazywania sudoku
@see bool rozwiaz(Plansza&) 
*/
class Rozwiaz2 : public Solver
{
public:
	/**Funkcja umozliwiajaca automatyczne rozwiazywanie sudoku, ktora kiedys moze zostanie dodana
	@param Plansza plansza
	@return Funkcja zwraca czy plansza zostala rozwiazana
	*/
	bool rozwiaz(Plansza&) { return true; }
};


/**Klasa Rozwiaz - klasa obslugujaca rozwiazywanie planszy
@see bool rozwiaz(Plansza&)
@see bool czy_moze(Plansza, int, int, int)
@see bool znajdz_puste(Plansza, int&, int&)
@see bool uzyj_wiersz(Plansza, int, int)
@see bool uzyj_kolumna(Plansza, int, int)
@see bool uzyj_kwadrat(Plansza, int, int, int)
*/
class Rozwiaz1: public Solver
{
public:
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
