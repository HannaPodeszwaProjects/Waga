#pragma once
#ifndef Pionek_H
#define Pionek_H
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
#include "Blad.h"

using namespace std;

class Pionek
{
private:
	int id; //id pionka
	int kolor_cz;
	bool baza; //czy w bazie
	bool meta; //czy na mecie
	bool koniec; //czy na koncu
	int polozenie; //polozenie na trasie
	blad* blad1; //rodzaj bledu, ktory wystapil

public:
	Pionek() {};
	Pionek(int i, int kolor, bool baza, bool meta, bool koniec, int polozenie/*,blad **/);
	Pionek& operator=(Pionek& nowy);
	int get_id() { return id; }
	int get_kolor_cz() { return kolor_cz; }
	void set_meta(bool b) { meta = b; }
	void set_baza(bool b) { baza = b; }
	bool get_meta() { return meta; }
	bool get_baza() { return baza; }
	void set_koniec(bool b) { koniec = b; }
	bool get_koniec() { return koniec; }

	void set_blad(blad* b){blad1 = b;}
	blad* get_blad() { return blad1; }
	void wyswietl_blad(blad& b); //wyswietlanie komunikatu do bledu


	void set_polozenie(int i) { polozenie = i; }
	int get_polozenie() { return polozenie; }
};

#endif