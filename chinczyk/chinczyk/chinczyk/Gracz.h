#pragma once
#ifndef Gracz_H
#define Gracz_H

#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <stdlib.h> 
#include <windows.h>
#include <cstdio>
#include <conio.h>
#include <stdio.h>
#include "Pole.h"

class Gracz
{
private:
	int nr; //nr gracza
	int kolor_cz; //kolor czcionki
	int kolor_t; //kolor tla
	Pionek p[4]; //pionki gracza
	int pionki_baza; //liczba pionkow w bazie
	int pionki_meta; //liczba pionkow na mecie
	int pionki_koniec; //liczba pionkow w domu
	int wejscie_meta; //pole przed wejsciem do mety

public:
	Gracz() {};
	Gracz(int nr,int b, int m,int k);
	Pionek& get_p(int i) { return p[i]; } //zwraca wybrany pionek

	Gracz &operator =(Gracz &nowy);
	void set_kolor(int cz, int t) { kolor_cz = cz; kolor_t = t; }
	void set_p(int i, Pionek pi) { p[i] = pi; }
	int get_pionki_baza() { return pionki_baza; }
	int get_pionki_meta() { return pionki_meta; }
	void set_pionki_baza(int i) { pionki_baza=i; }
	void set_pionki_meta(int i) { pionki_meta=i; }
	int get_pionki_koniec() { return pionki_koniec; }
	void set_pionki_koniec(int i) { pionki_koniec = i; }

	void set_wejscie_meta(int i) { wejscie_meta = i; }
	int get_wejscie_meta() { return wejscie_meta; }
};

#endif