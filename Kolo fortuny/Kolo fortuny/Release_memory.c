#include <stdio.h>
#include <stdlib.h>     
#include <time.h> 
#include <stdbool.h>

#include "menu.h"
#include "play.h"
#include "prepare_game.h"
#include "results.h"
#include "settings.h"
#include "wheel.h"
#include "Release_memory.h"


void release_results(result ** tab,int j) //zwalnia pamiec zaalokowana na wyniki
{
	for (int i = 0; i < j; i++)
	{
		free((*tab)[i].nick);
	}
	free(*tab);
	*tab = NULL;
}

state release_players(cur_data * data) //zwalnia pamiec zaalokowana na graczy
{
	for (int i = 0; i < (*data).number_players; i++)
	{
		free((*data).tab_player[i].nick);
	}
	free((*data).tab_player);
	(*data).tab_player = NULL;
	return STOP;
}

void release_tab_words(char***tab,int j) //zwalnia pamiec zaalokowana na slowa z pliku
{
	for (int i = 0; i < j; i++)
	{
		free((*tab)[i]);
	}
	free(*tab);
	*tab = NULL;
}

void release_random_word(cur_data * data) //zwalnia pamiec zaalokowana na haslo
{
	free((*data).tab_word);
}