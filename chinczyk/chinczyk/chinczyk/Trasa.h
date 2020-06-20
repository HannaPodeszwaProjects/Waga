#pragma once
#ifndef Trasa_H
#define Trasa_H
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
#include "Pole.h"



class Trasa
{
private:
	Pole trasa[40]; //staly rozmiar planszy

	Pole meta[4][6]; //meta + pole za_daleko
	Pole baza[4][4];

public:
	Trasa();
	void wpisz_wspolrzedne();
	void wpisz_wspolrzedne_baza();
	void wpisz_wspolrzedne_meta();
	Pole& get_pole(int i) { return trasa[i]; }
	Pole &get_meta(int g, int p) { return meta[g][p]; }
	Pole &get_baza(int g, int p) { return baza[g][p]; }

	

	Pole &znajdz_pole(int gracz, int pionek);
	Pole &znajdz_baza(int gracz, int pionek);
	Pole& znajdz_meta(int gracz, int pionek);
	void wpisz_kolory(); //ustawienie kolorow pol
	~Trasa(){}
};


#endif