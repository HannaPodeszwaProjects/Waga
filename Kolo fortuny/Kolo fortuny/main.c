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

//stany
//TURN_OFF, PREPARE_GAME, SETTINGS, RESULTS, GUESS_LETTER, GAME_MENU, SPIN, GUESS_WORD, BUY, WIN, SAVE,WHEEL, INIT, MENU, STOP, NUMBER

//tablica wskaznikow na funkcje
state(*tab[NUMBER])(cur_data *data) = {release_players, do_prepare_game, do_settings, do_results, do_guess_letter,
do_game_menu, do_spin,do_guess_word, do_buy, do_win, do_save, do_wheel, do_init, do_menu, NULL};

//funkcja obslugujaca stany
state run_state(state state_cur,cur_data *data)
{
	return tab[state_cur](data);
};

int main(int argc, char ** argv)
{
	if (argc != 2)
	{
		printf("Za malo argumentow.\n");
		return 0;
	}
	else if (strcmp(argv[1],"h")==0)
	{
		write_help();
	}
	else
	{
		cur_data data;
		int i;
		sscanf_s(argv[1], "%d", &i); //przeksztalcenie char* na int
		data.number_players = i;
		if (data.number_players >= 1 && data.number_players <= 4)
		{
			change_category(&(data.category)); //wybor kategorii
			data.state_cur = WHEEL; //pierwszy stan
			while (data.state_cur != STOP)
			{
				data.state_cur = run_state(data.state_cur, &data);
			}
			printf("Wylaczenie gry.");
			printf("%d %d  ", data.tab_player, data.tab_word);
		}
		else
		{
			printf("Niepoprawna liczba graczy");
		}
	}

	return 0;
	
}