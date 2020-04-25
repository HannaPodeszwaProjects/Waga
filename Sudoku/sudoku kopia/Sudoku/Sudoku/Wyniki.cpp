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

	if (wynik1.wypisz_z_pliku(gracz_pierwszy))
	{
		wynik1.wypisz_nick(gracz_pierwszy);
		wynik1.usun(gracz_pierwszy);
	}

	return MENU;
}

bool Wynik::wypisz_z_pliku(Gracz * &gracz_pierwszy)
{
	ifstream plik("wyniki.txt");

	string nick_p;
	int czas_p;
	
	if (plik)
	{
		while (plik >> nick_p)
		{
			plik >> czas_p;
			dodaj(gracz_pierwszy, nick_p, czas_p);
		}
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
	if (pHead == nullptr) 
	{
		Gracz * nowy=new Gracz(nullptr,nick_nowy,czas_nowy);
		pHead = nowy;
	}
	else 
	{
		Gracz* x = new Gracz(pHead, nick_nowy, czas_nowy);
		pHead=x;
	}
}

void Wynik::wypisz_nick(Gracz* pHead) 
{
	cout << "WYNIKI" << endl << endl;
	cout << left <<setw(21)<<"nick: "<<right<< setw(14) <<"czas:" << endl << endl;
	while (pHead != nullptr)
	{
		cout << pHead;
		pHead = pHead->kolejny();
	}
	system("pause");
}

Gracz::~Gracz()
{
	if(this!=nullptr)
	delete pNext;
	pNext = nullptr;
}
void Wynik::usun(Gracz*& pHead) 
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