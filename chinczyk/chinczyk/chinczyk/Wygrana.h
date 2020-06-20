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
#include <iomanip>

void wypisz_spacje(int s);

class Wygrana
{
private:
	string nick; 
public:
	/**Funkcja wypisujaca kaczki
	*/
	void kaczki();

	/**Funkcja zapisujaca wynik do pliku
	@return Funkcja zwraca czy dane zostaly prawidlowo zapisane
	*/
	bool zapisz();

	/**Funkcja wpisujaca nick
	*/
	void wpisz_nick();

	/**Operator strumieniowy
	@param out wyjscie
	@param w obiekt klasy Wygrana
	@return Funkcja  zwraca wyjscie
	*/
	friend ostream& operator<<(ostream& out, Wygrana& w);
};

#endif