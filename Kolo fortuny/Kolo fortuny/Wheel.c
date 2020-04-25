#include <stdio.h>
#include <stdlib.h>     
#include <time.h> 
#include <stdbool.h>
#include <windows.h>
#include "Menu.h"
#include "Play.h"
#include "Prepare_game.h"
#include "Results.h"
#include "Settings.h"
#include "Wheel.h"
#include "Release_memory.h"

state do_wheel( cur_data *data) //przygotowanie kola
{
	int tab[25];
	FILE * file = NULL;
	fopen_s(&file, "kolo.txt", "r");
	if (file)
	{
		for (int i = 0; i < 24; i++)
			fscanf_s(file, "%d", &tab[i], sizeof(int));
		tab[24] = -10;
		fclose(file);
	}
	else
	{
		printf("Blad otwracia pliku.\n");
	}
	memcpy((*data).tab_wheel, tab, sizeof(int) * 25);
	return INIT;
}

int power() //moc krecenia kolem
{
	system("cls"); //czyszczenie konsoli
	int  i = 0;
	printf("Nacisnij enter by zatrzymac  \n\n");
	while (!kbhit())
	{
		for (i = 0; i < 6; i++)
		{

			printf("||||");
			if (kbhit())
				break;
			Sleep(200);
		}
		system("cls"); //czyszczenie konsoli
		printf("Nacisnij enter by zatrzymac  \n\n");
	}
	return i+1;
}

state do_spin( cur_data *data) //krecenie kolem
{
	int i = power();
	int x = rand();
	(*data).chance = (*data).tab_wheel[(i*x) % 24];
	switch ((*data).chance)
	{
	case -1: //bankrut
		(*data).tab_player[(*data).cur_player].money = 0;
		(*data).cur_player = ((*data).cur_player + 1) % (*data).number_players;
		return GAME_MENU;
	case 0: //pass
		(*data).cur_player = ((*data).cur_player + 1) % (*data).number_players;
		return GAME_MENU;
	default:
		return GUESS_LETTER;
	}
}