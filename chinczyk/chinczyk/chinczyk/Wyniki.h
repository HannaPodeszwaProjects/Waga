#pragma once
#ifndef Wyniki_H
#define Wyniki_H

#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <stdlib.h> 
#include <windows.h>
#include <cstdio>
#include <conio.h>
#include <stdio.h>
#include <forward_list>
using namespace std;

class Wynik
{
private:
	string nick_p;
	int wybor;
public:
	//Wynik(forward_list <string> w) { wyniki = w; }
	/**Funkcja wypisuje liste wynikow
	@param pHead wskaznik na poczatek listy
	*/
	void wypisz_nick(forward_list <string>&);

	/**Funkcja wypisuje dane z pliku
	@param  wskaznik na poczatek listy
	@return Funkcja zwraca czy dane zostaly prawidlowo zapisane
	*/
	bool wypisz_z_pliku(forward_list <string>&);

	string get_nick() { return nick_p; }
	void set_wybor(int i) { wybor = i; }
	int get_wybor() { return wybor; }
};
#endif