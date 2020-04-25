#include <iostream>
#include <windows.h>
#include <cstdio>
#include <stdio.h>
#include <time.h>
#include "Stan.h"
#include "Ogolne.h"
#include "Pole.h"
#include "Plansza.h"
#include "Rozwiaz.h"
#include "Wynik.h"
#include "Wygrana.h"


using namespace std;

void Stan::kolejny_stan()
{
	set_aktualny_stan((this->*tab[aktualny_stan])());
}


int main()
{
	Stan stan1(MENU); 
	stan1.set_poprzedni_stan(MENU);
	while(stan1.get_aktualny_stan()!=STOP)
	{
		stan1.kolejny_stan();
	}
		return 0;
}