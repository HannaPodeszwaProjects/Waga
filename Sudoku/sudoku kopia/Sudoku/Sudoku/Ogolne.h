#pragma once
#ifndef Ogolne_H
#define Ogolne_H

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
#include "Pole.h"
#include "Plansza.h"
#include "vld.h"

using namespace std;

typedef enum //stany
{
	STOP,MENU, GRA,WYNIK, SPRAWDZ, ZAPISZ, WYJDZ, ROZWIAZ,WYGRANA,HELP, NUMBER
} state; 

enum litery {w=119,z=122,s=115,r=114,g=103,spacja=32,zero=48}; //stale 

/**Funckcja wypisujaca menu
*/
void wypisz_menu();
/**Funkcja umozliwiajaca poruszanie strzalkami
@param c polozenie kursora
@return Funkcja zwraca wpisana przez uzytkownika wartosc
*/
int poruszanie_strzalkami(COORD* c);
/**Funkcja znajduje dane pole
@param c polozenie kursora
@param plansza plansza
@return Funkcja zwraca znalezione pole
*/
Pole znajdz_pole(COORD c, Plansza plansza);
/**Funkcja zmienia kolor czcionki
@param numer koloru
*/
void daj_kolor(int kolor);
/**Funkcja ustawia kursor
@param x polozenie kursora
@param y polozenie kursora
*/
void ustaw_kursor(int x, int y);
/**Funkcja wypisuje spacje
@param s ilosc spacji
*/
void wypisz_spacje(int s);

#endif