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
#include "Wynik.h"
#include "Wygrana.h"

using namespace std;

state Stan::do_wynik()
{
	system("cls");
	Gracz* gracz_pierwszy=nullptr;
	Wynik wynik1;
	wynik1.set_gracz(gracz_pierwszy);

	if (wynik1.wypisz_z_pliku())
	{
		wynik1.wypisz_nick();
		wynik1.usun();
	}

	return MENU;
}

bool Wynik::wypisz_z_pliku()
{
	ifstream plik("wyniki.txt");
	czas_p = 0;
	nick_p = " ";
	
	if (plik)
	{
		plik >> *this;
		plik.close();
	}
	else
	{
		cout << "Blad otwarcia pliku." << endl;
		system("pause");
		return false;
	}
	return true;
}



void Wynik::dodaj(Gracz*& pHead, string nick_nowy, int czas_nowy)
{
	
		Gracz* x = new Gracz(pHead, nick_nowy, czas_nowy);
		pHead=x;
	
}

void Wynik::wypisz_nick() 
{
	cout << "WYNIKI" << endl << endl;
	cout << left <<setw(21)<<"nick: "<<right<< setw(14) <<"czas:" << endl << endl;
	Gracz* tmp=pHead;
	while (tmp != nullptr)
	{
		cout << tmp;
		tmp = tmp->kolejny();
	}
	system("pause");
}

Gracz::~Gracz()
{
	if(this!=nullptr)
	delete pNext;
	pNext = nullptr;
}
void Wynik::usun()
{
	delete pHead;
	pHead = nullptr;
}

ostream& operator<<(ostream& out,Gracz*& pHead)
{
	int c = (pHead->daj_czas());
	out << left<<setw(21)<<pHead->daj_nick() <<right<< setw(10) << c/60<<" min. "
		<<right << setw(3) <<c%60<<left<<" s." << endl;
	return out;
}
Gracz* Wynik::operator+=( Gracz* x)
{
	x = new Gracz(pHead, nick_p, czas_p);
		pHead = x;
		return x;
}

istream& operator>>(istream& input, Wynik & w)
{
	while (input >> w.nick_p)
	{
		input >> w.czas_p;
		Gracz* nowy = nullptr;
		w += nowy;
	}
	return input;
}