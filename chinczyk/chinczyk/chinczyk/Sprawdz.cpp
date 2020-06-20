#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <stdlib.h> 
#include <windows.h>
#include <cstdio>
#include <conio.h>
#include <stdio.h>
#include "Stan.h"
#include "Pole.h"
#include "Gracz.h"
#include "Trasa.h"

using namespace std;

bool Gra::sprawdz()
{
	
	int ile_blednych=0;
	for (int i = 0;i < 4;i++)
	{
		Pionek* p = &(gracz[nr_aktualnego_gracza].get_p(i));
		try
		{
			sprawdz_baza(i, *p);
			sprawdz_koniec(i, *p);
			sprawdz_meta_kolizja_zadaleko(i, *p);
			p->set_blad(nullptr);
		}
		catch (pionek_baza type)
		{
			blad* x = &pb;
			p->set_blad(x);
			ile_blednych++;
		}
		catch (pionek_koniec type)
		{
			blad* x = &pk;
			p->set_blad(x);
			ile_blednych++;
		}
		catch (meta_kolizja type)
		{
			blad* x = &mk;
			p->set_blad(x);
			ile_blednych++;
		}
		catch (za_daleko type)
		{
			blad* x = &zd;
			p->set_blad(x);
			ile_blednych++;
		}
	}
	if (ile_blednych == 4)
	{
		return false;
	}
	
	return true;
}

int Gra::sprawdz_zakres(int x)
{
	if (x > -1 && x < 4)
		return x;
	else {
		zly_znak b;
		throw b;
	}
}
void Gra::sprawdz_baza(int x, Pionek p)
{
	if (p.get_baza() && get_kostka() != 6)
	{
		pionek_baza b;
		throw b;
	}
	else {
		return ;
	}
}

void Gra::sprawdz_koniec(int x, Pionek p)
{
	if (p.get_koniec())
	{
	pionek_koniec b;
		throw b;
	}
	else {
		return;
	}
}

void Gra::sprawdz_meta_kolizja_zadaleko(int x, Pionek p) //nie mozna zbic pionka na mecie
{
	int ile_zostanie;
	if (p.get_meta() && (p.get_polozenie() + kostka) >4)
	{
		za_daleko b;
		throw b;
	}
	else if (p.get_meta() && trasa_gra.get_meta(nr_aktualnego_gracza, p.get_polozenie() + kostka).get_zajete()) //jesli pole jest juz zajete
	{
		
		meta_kolizja b;
		throw b;
	}
	else if (!p.get_baza() && gracz[nr_aktualnego_gracza].get_wejscie_meta() >= p.get_polozenie()
		&& kostka > (gracz[nr_aktualnego_gracza].get_wejscie_meta() - p.get_polozenie())) //gdy pionek dopiero wejdzie na mete
	//wtedy moze wejsc na mete
	{
		ile_zostanie = kostka - (gracz[nr_aktualnego_gracza].get_wejscie_meta() - p.get_polozenie()); //kostka - odleglosc do wejscia na mete
		if (ile_zostanie >5)
		{
			za_daleko b;
			throw b;
		}
		else if (trasa_gra.get_meta(nr_aktualnego_gracza, ile_zostanie-1).get_zajete()) //jesli pole jest juz zajete
		{
			meta_kolizja b;
			throw b;
		}
	}
	else
	{
		return;
	}
}
//
void zly_znak::wypisz_blad()
{
	wypisz_komunikat("Wypisz cyfre od 1 do 4.", true);
}
void pionek_koniec::wypisz_blad()
{
	wypisz_komunikat("Pionek jest juz w domu.", true);
}
void pionek_baza::wypisz_blad()
{
	wypisz_komunikat("Ten pionek jest w bazie a ty nie masz 6.", true);
}
void meta_kolizja::wypisz_blad()
{
	wypisz_komunikat("To pole jest juz zajete.", true);
}
void za_daleko::wypisz_blad()
{
	wypisz_komunikat("Za daleko.", true);
}

void Pionek::wyswietl_blad(blad &b) 
{
	b.wypisz_blad();
}

zly_znak::~zly_znak(){}
pionek_koniec::~pionek_koniec(){}
pionek_baza::~pionek_baza() {}
meta_kolizja::~meta_kolizja() {}
za_daleko::~za_daleko() {}