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

using namespace std;


void Stan::kolejny_stan()
{
	set_aktualny_stan((this->*tab[aktualny_stan])());
}

int main()
{
	Stan stan1(MENU);
		while (stan1.get_aktualny_stan() != STOP)
		{
			stan1.kolejny_stan();
		}
		return 0;
}
