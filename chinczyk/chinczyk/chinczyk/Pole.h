
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
#include <iomanip>
#include "Pionek.h"

using namespace std;


class Pole
{
private:
	int gracz; //gracz ktorego pionek zajmuje pole
	Pionek* pionek; //pionek
	int kolor_tla; // kolor pola
	bool zajete; //czy dane pole jest zajete
	int x;
	int y;
	int id_pola; //nr pola w trasie

public:
	Pole(){}
	Pole(int id,int g, int k, bool z, int ,int);
	void set_x(int n) { x = n; }
	void set_y(int n) { y = n; }
	int get_x() { return x; }
	int get_y() { return y; }
	int get_pionek() { return ((pionek->get_id()) + 1); }
	void set_id_pola(int i) { id_pola = i; }
	int get_id_pola() { return id_pola; }
	void set_gracz(int g) { gracz = g; }
	int get_gracz() { return gracz; }
	

	Pionek *get_Pionek() { return pionek; }
	void set_Pionek(Pionek *p) { pionek =p; }

	bool get_zajete() { return zajete; }
	void set_zajete(bool b) { zajete=b; }
	void set_kolor_tla(int k) { kolor_tla = k; }
	int get_kolor_tla() { return kolor_tla; }

	Pole& operator=(Pole& nowy);
	friend ostream& operator<<(ostream& out, Pole p);

};



#endif