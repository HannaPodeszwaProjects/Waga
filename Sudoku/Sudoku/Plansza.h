#pragma once
#ifndef Plansza_H
#define Plansza_H

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
using namespace std;

/**Klasa Plansza opisuje plansze
@param tab[9][9] tablica obiektow Pole
@see uzupelnij_z_pliku(string)
@see Plansza(Pole Tab) 
@see Plansza() 
@see wypisz_plansze()
@see Pole get_tab(int x, int y)
@see Pole set_tab(int x, int y, Pole cos)
@see bool sprawdz(Plansza)
@see Plansza& operator=(Plansza&)
@see Pole stworz_nowa(Plansza&)
@see void usun_losowe(Plansza&)
*/
class Plansza
{
private:
	Pole tab[9][9];
public:
	Pole uzupelnij_z_pliku(string);
	/**Konstruktor obiektu Pole
	@param Tab tablica obiketow Pole
	*/
	Plansza(Pole Tab) { tab[0][0] = Tab; }

	/**Konstruktor obiektu Pole
	*/
	Plansza() {};

	/**Funkcja wypisujaca plansze
	*/
	void wypisz_plansze();

	/**Funkcja wypisujaca element tablicy
	@param x wspolrzedne elementu tablicy
	@param y wspolrzedne elementu tablicy
	@return Funkcja zwraca element Planszy
	*/
	Pole get_tab(int x, int y);

	/**Funkcja wpisuje element tablicy
	@param x wspolrzedne elementu tablicy
	@param y wspolrzedne elementu tablicy
	@param cos obiekt Pole do wpisania
	@return Funkcja zwraca obiekt Pole
	*/
	Pole set_tab(int x, int y, Pole cos);

	/**Funkcja sprawdza poprawnosc wypelnienia planszy
	@param Plansza
	@return Funkcja zwraca czy plansza jest poprawnie wypelniona
	*/
	bool sprawdz(Plansza);
	
	/**Operator przypisania
	@param Plansza nowa plansza
	@return Operator zwraca plansze
	*/
	Plansza& operator=(Plansza&);
	
	/**Funkcja tworzy nowa plansze
	@param Plansza plansza do wypelnienia
	@return Funkcja zwraca poczatek nowej planszy
	*/
	Pole stworz_nowa(Plansza&);

	/**Funkcja usuwa losowe wartosci z planszy
	@param Plansza plansza z ktorej usuwamy wartosci
	*/
	void usun_losowe(Plansza&);

};

#endif