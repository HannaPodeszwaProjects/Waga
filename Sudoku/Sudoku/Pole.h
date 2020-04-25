#pragma once
#ifndef Pole_H
#define Pole_H

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

/**Klasa Pole - pole planszy
@param wartosc wartosc pola
@param z_pliku czy dana litera jest z pliku
@param x polozenie pola w konsoli
@param y polozenie pola w konsoli
@see Pole(int, bool, int, int)
@see Pole() 
@see void wypisz()
@see int get_wartosc()
@see int get_x() 
@see int get_y() 
@see bool get_z_pliku() 
@see void set_z_pliku(bool nowa) 
@see void set_wartosc(int nowa) 
@see Pole& operator=(Pole& nowe)
@see bool operator==(Pole)
*/
class Pole
{
private:
	int wartosc;
	bool z_pliku;
	int x;
	int y;
public:
	/**Konstruktor obiektu Pole
	@param wartosc wartosc pola
	@param z_pliku czy dana litera jest z pliku
	@param x polozenie pola w konsoli
	@param y polozenie pola w konsoli
	*/
	Pole(int, bool, int, int);

	/**Konstruktor obiektu Pole
	*/
	Pole() {}

	/**Funkcja wypisuje pole
	*/
	void wypisz();

	/**Funkcja wypisuje wartosc
	@return Funkcja zwraca wartosc obiektu
	*/
	int get_wartosc() { return wartosc; }

	/**Funkcja wypisujee polozenie pola
	@return Funkcja zwraca polozenie x obiektu
	*/
	int get_x() { return x; }

	/**Funkcja wypisuje polozenie pola
	@return Funkcja zwraca polozenie y obiektu
	*/
	int get_y() { return y; }

	/**Funkcja wypisuje czy wartosc obiektu zostala wpisana z pliku
	@return Funkcja zwraca czy wartosc obiektu zostala wpisana z pliku
	*/
	bool get_z_pliku() { return z_pliku; }

	/**Funkcja wpisuje czy wartosc pola pochodzi z pliku
	@param nowa nowa wartosc
	*/
	void set_z_pliku(bool nowa) { z_pliku = nowa; }

	/**Funkcja odczytuje wartosc pole
	@param nowa nowa wartosc
	*/
	void set_wartosc(int nowa) { wartosc = nowa; }

	/**Operator przypisania
	@param nowe nowe pole
	@return Operator zwraca pole
	*/
	Pole& operator=(Pole& nowe);

	/**Operator porownania
	@param  Pole pole do porownaina
	@return Operator zwraca czy dane pola sa takie same
	*/
	bool operator==(Pole);
};

#endif