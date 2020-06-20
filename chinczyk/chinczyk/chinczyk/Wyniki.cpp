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
#include "Stan.h"
#include "Wyniki.h"

state Stan::do_wynik()
{
	Wynik wynik1;
	cout<<"Jak posortowac wyniki?"<<endl<<endl<<"1.Od najnowszych."<<endl<<"2.Alfabetycznie."<<endl;
	fseek(stdin, 0, SEEK_END); //usuniecie dodatkowych znakow z bufora

	wynik1.set_wybor((_getch()) - 48);
	if (wynik1.get_wybor() != 1 && wynik1.get_wybor() != 2)
	{
		cout << "Niepoprawna wartosc." << endl;
		return MENU;
	}
	system("cls");
	forward_list <string> wyniki;
	
	if (wynik1.wypisz_z_pliku(wyniki))
	{
		wynik1.wypisz_nick(wyniki);
		wyniki.clear();
	}

	return MENU;
}

bool Wynik::wypisz_z_pliku(forward_list <string> &wyniki)
{
	ifstream plik("wyniki.txt");
	nick_p = " ";

	if (plik)
	{
		while (plik>> this->nick_p)
				{
					wyniki.push_front(this->nick_p);
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
void Wynik::wypisz_nick(forward_list <string>& wyniki)
{
	if(get_wybor()==2)
	wyniki.sort();
	cout << "ZWYCIEZCY" << endl << endl;
	for (string& c : wyniki) //wypisanie wszytskiech elementów listy
		cout << c<<endl;
	system("pause");
}
