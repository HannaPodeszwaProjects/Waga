#pragma once
#ifndef Gra_H
#define Gra_H

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
#include "Gracz.h"
#include "Trasa.h"
#include "Blad.h"
#include "Wygrana.h"

class Gra
{
private:
	Gracz gracz[4];
	Trasa trasa_gra;
	int nr_aktualnego_gracza;
	int kostka;
	int wybrany_znak;

	//obiekty bledow
	pionek_baza pb;
	pionek_koniec pk;
	meta_kolizja mk;
	za_daleko zd;

public:
	Gra(Trasa t);
	void wpisz_graczy(); //ustawienie graczy i ich pionkow
	int graj(); //funkcja obsluguje gre
	void rzuc_kostka();
	void przesun_pionek(int nr, int kostka); 

	//znalezienie na ktorym polu pionek bedzie stal po przesunieciu
	void nastepne_pole(int &polozenie, int kostka, int &gdzie); 
	int pobierz_znak(); //pobiera znak od gracza i sprawdza jego poprawnosc
	void set_wybrany_znak(int z) { wybrany_znak = z; }
	int get_wybrany_znak() { return wybrany_znak; }
	Gracz* get_gracz(int i) { 
		Gracz* g = gracz + i;
		return g; }

	void set_nr_aktualnego_gracza(int n) { nr_aktualnego_gracza = n; }
	int get_nr_aktualnego_gracza() { return nr_aktualnego_gracza; }
	void set_kostka(int k) { kostka = k; }
	int get_kostka() { return kostka; }
	void wypisz_bazy(); //wypisuje bazy graczy na planszy
	void kolizja(int); //obsluga zbijania pionkow
	void zajmij_pole(Pole&, Pionek&, int); //ustawienie pionka na danym polu

	//sprawdzanie zanku i mozliwosci ruchu pionkami
	bool sprawdz();
	int sprawdz_zakres(int);
	void sprawdz_baza(int, Pionek);
	void sprawdz_koniec(int, Pionek);
	void sprawdz_meta_kolizja_zadaleko(int, Pionek);
	
	void wygrana(int); //obsluga wygranej gracza
};
#endif
