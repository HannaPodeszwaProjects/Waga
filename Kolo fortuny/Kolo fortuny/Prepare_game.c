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

state do_prepare_game(cur_data *data) //przygotowuje gre
{
	for (int i = 0; i < (*data).number_players; i++)
	{
		(*data).tab_player[i].money = 0;
	}
	(*data).chance = -2;
	char**tab_words;
	int j; //liczba slow z pliku
	tab_words=read_file((*data), &j); //wpisuje slowa z pliku do tablicy

	if (tab_words == NULL)
	{
		return MENU;
	}

	random_word(&(*data), tab_words, j); //losowanie slowa
	release_tab_words(&tab_words,j); //zwalnianie pamieci a tablicy char
	system("cls"); //czyszczenie konsoli
	do_available_letters(&(*data)); //przygotowuje tablice dostepnych liter

	return GAME_MENU;
}

void do_available_letters(cur_data *data) //tablica dostepnych liter
{
	for (int i = 0; i < number_letters_alphabet; i++) 
	{
		(*data).available_letters[i].c = 'A' + i;
		if (i == 16 || i == 21 || i == 23) //bez q,v i x
		{
			(*data).available_letters[i].available_char = false;
		}
		else
		{
			(*data).available_letters[i].available_char = true; //dostepna
			if ((*data).available_letters[i].c == 'A' || (*data).available_letters[i].c == 'E' || (*data).available_letters[i].c == 'I' ||
				(*data).available_letters[i].c == 'O' || (*data).available_letters[i].c == 'U' || (*data).available_letters[i].c == 'Y')
			{
				(*data).available_letters[i].consonant = false; //samogloska
			}
		}
	}
}

void random_word(cur_data *data, char**tab_words, int j) //losuje haslo
{
	srand(time(0));
	int x = rand() % j; //losowe slowo
	int n = count_char(tab_words[x]); //liczba liter 
	(*data).number_letters = n;
	printf("\n\n%d\n", n);
	(*data).tab_word = (letter*)calloc((n + 1), sizeof(letter)); //(znak konca lini)
	
	for (int i = 0; i < n + 1; i++)
	{
		(*data).tab_word[i].c = tab_words[x][i];
		if ((*data).tab_word[i].c == space) //spacje jako odgadniete
		{
			(*data).tab_word[i].guessed = true;
		}
		else
		{
			(*data).tab_word[i].guessed = false;
		}
	}
}

char* read_word(FILE *file)  //wczytuje slowo z pliku
{
	char* word;  // bufor na zwracany string
	char tmp[max_size]; //tablica przechowujaca wyraz

	char a;
	if ((a = fgetc(file)) == EOF) //sprawdzenie, czy plik nie jest pusty
	{
		return NULL;
	}
	fseek(file, -1, SEEK_CUR);
	fgets(tmp, max_size, file); //wpisanie wyrazu do tablicy

	int n=count_char(tmp); //ilosc znakow wyrrazu

	//przepisanie wyrazu do dynamicznej tablicy

	word = (char*)malloc(n + 1);
	for (int i = 0; i < n; i++)
		word[i] = tmp[i];
	word[n] = 0;  //dodanie znacznika koñca 
	return word;
}

char ** read_file(cur_data data, int *j) //wczytuje slowa z pliku do tablicy
{
	char * tmp = -1;
	
	FILE * file = NULL;
	char**tab = NULL;
	fopen_s(&file, data.category, "r");
	*j = 0;
	if (file)
	{
		tab = (char*)malloc(sizeof(char *));
		while (tmp != NULL)
		{
			tmp = read_word(file);
			if (tmp != NULL)
			{
				tab = realloc(tab, ((*j) + 1) * sizeof(char *)); //zalokowanie miejsca na nowe slowo
				tab[(*j)] = tmp;
				(*j)++;
			}
		}
		fclose(file);
	}
	else
	{
		printf("Blad otwracia pliku. Wybierz inny plik.\n");
		hit_enter();
	}
	return tab;
}



state do_init(cur_data *data) //tworzy tablice graczy
{
	(*data).tab_player = ((player*)calloc((*data).number_players, sizeof(player))); //alokacja miejsca na tablice struktur

	for (int i = 0; i < (*data).number_players; i++)
	{
		printf("Podaj nick %d gracza:\n", i + 1);

		(*data).tab_player[i].nick = do_nick(); //wpisuje nick do dynamicznej tablicy
	}
	(*data).cur_player = rand() % (*data).number_players; //losowanie pierwszego gracza

	system("cls"); //czyszczenie konsoli
	return MENU;

}

char *do_nick() //wpisuje nick do dynamicznej tablicy
{
	char* nick;  // bufor na zwracany string
	char tmp[max_size]; //tablica przechowujaca nick
	fgets(tmp, sizeof(tmp), stdin); //wpisanie wyrazu do tablicy
	fseek(stdin, 0, SEEK_END); //usuniecie dodatkowych znakow z bufora
		 
	int n=count_char(tmp); //ilosc znakow wyrazu

	//przepisanie wyrazu do dynamicznej tablicy

	nick = (char*)malloc(sizeof(char) *(n + 1));
	for (int i = 0; i < n; i++)
		nick[i] = tmp[i];
	nick[n] = 0;  //dodanie znacznika koñca 

	return nick;
}