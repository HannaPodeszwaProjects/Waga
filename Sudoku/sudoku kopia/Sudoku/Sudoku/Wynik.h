#pragma once
#ifndef Wynik_H
#define Wynik_H

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
#include <iomanip>
#include "Stan.h"
#include "Ogolne.h"
#include "Pole.h"
#include "Plansza.h"
#include "Rozwiaz.h"
using namespace std;

/**Klasa Gracz - opisuje obiekt kazdego gracza
@param pNext wskaznik na kolejny element listy
@param nick nick gracza
@param czas czas gry gracza
@see Gracz(Gracz* wsk, string nick_nowy, int czas_nowy)
@see string daj_nick() 
@see time_t daj_czas() 
@see void ustaw(Gracz* z)
@see Gracz* kolejny()
@see friend ostream& operator<<(ostream& out, Gracz*&)
@see ~Gracz();
*/
class Gracz
{
private:
	Gracz* pNext;
	string nick;
	int czas;

public:
	/**Konstruktor obiektu Gracz
	@param wsk wskaznik na kolejny element listy 
	@param nick_nowy nick gracza
	@param czas_nowy czas gracza
	*/
	Gracz(Gracz* wsk, string nick_nowy, int czas_nowy) : pNext(wsk), czas(czas_nowy), nick(nick_nowy) {};
	/**Funkcja wypisuje nick
	@return Funkcja zwraca nick
	*/
	string daj_nick() { return nick; }
	/**Funkcja wypisuje czas
	@return Funkcja zwraca czas
	*/
	time_t daj_czas() { return czas; }
	/**Funkcja wpisuje wskaznik na kolejny element listy
	@param z wskaznik na kolejny element listy
	*/
	void ustaw(Gracz* z) { pNext = z; }
	/**Funkcja wypisuje wskaznik na kolejny element listy
	@return Funkcja zwraca wskaznik na kolejny element listy
	*/
	Gracz* kolejny() { return pNext; }
	friend ostream& operator<<(ostream& out, Gracz*&);
	/**Destruktor obiektu Gracz
	*/
	~Gracz();
};

/**Klasa Wynik - poczatek listy wynikow
@param pHead poczatek listy wynikow
@see void set_gracz(Gracz* nowy) 
@see void dodaj(Gracz*& pHead, string nick_nowy, int czas_nowy)
@see void usun(Gracz*& pHead)
@see void wypisz_nick(Gracz* pHead)
@see bool wypisz_z_pliku(Gracz*&)
*/
class Wynik
{
private:
	Gracz* pHead;
public:
	/**Funkcja wpisuje nowy poczatek listy graczy
	@param nowy nowy wskaznik
	*/
	void set_gracz(Gracz* nowy) { pHead = nowy; }
	/**Funkcja dodaje nowy element do listy
	@param pHead wskaznik na poczatek listy
	@param nick_nowy nick gracza
	@param czas_nowy czas gracza
	*/
	void dodaj(Gracz*& pHead, string nick_nowy, int czas_nowy);
	/**Funkcja usuwa liste wynikow
	@param pHead wskaznik na poczatek listy
	*/
	void usun(Gracz*& pHead);
	/**Funkcja wypisuje liste wynikow
	@param pHead wskaznik na poczatek listy
	*/
	void wypisz_nick(Gracz* pHead);
	/**Funkcja wypisuje dane z pliku
	@param  wskaznik na poczatek listy
	@return Funkcja zwraca czy dane zostaly prawidlowo zapisane
	*/
	bool wypisz_z_pliku(Gracz*&);
};

#endif