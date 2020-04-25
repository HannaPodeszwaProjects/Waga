#pragma once
#ifndef Wygrana_H
#define Wygrana_H

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
using namespace std;

/** Klasa Wygrana - klasa obslugujaca stan wygrana
@param nick nick gracza
@see void kaczki()
@see void set_czas(time_t nowy)
@see bool zapisz(time_t)
@see void wpisz_nick()
@see friend ostream& operator<<(ostream& out, Wygrana & w)
*/
class Wygrana
{
private:
	string nick;
	time_t czas;
public:
	/**Funkcja wypisujaca kaczki
	*/
	void kaczki();

	/**Funkcja wpisujaca czas gry
	@param nowy nowy czas
	*/
	void set_czas(time_t nowy) { czas = nowy; }

	/**Funkcja zapisujaca wynik do pliku
	@param time_t czas rozgrywki
	@return Funkcja zwraca czy dane zostaly prawidlowo zapisane
	*/
	bool zapisz(/*time_t*/);

	/**Funkcja wpisujaca nick
	*/
	void wpisz_nick();

	/**Operator strumieniowy
	@param out wyjscie
	@param w obiekt klasy Wygrana
	@return Funkcja  zwraca wyjscie
	*/
	friend ostream& operator<<(ostream& out, Wygrana & w);
};

#endif